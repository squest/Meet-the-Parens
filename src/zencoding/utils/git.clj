(ns zencoding.utils.git
  (:require [tentacles.core :as core]
            [tentacles.users :as user]
            [tentacles.data :as data]
            [org.httpkit.client :as http]
            [zencoding.dbase.config :refer :all]
            [com.ashafa.clutch :as cl]
            [cheshire.core :as cs]
            [clojure.string :as cstr]
            [clojure.pprint :as pp]
            [me.raynes.fs :as fs]))
(comment 
  (defn git-config
    "Returns the git access data slurped from an edn file,
  this is a temporarily publiced function for test only"
    []
    (:git (read-string (slurp "config.edn"))))

  (defn master-sha
    "Returns the master-sha for the repo that is used to stored notes"
    []
    (let [{gituser :username gitrepo :repo} (git-config)]
      (->> (data/references gituser gitrepo)
           first
           :object
           :sha)))

  (defn in-path?
    "Returns true if a certain something in git-tree pathname"
    [tree something]
    (let [paths (cstr/split (:path tree) #"/")]
      (if (some #(= something %) paths)
        true
        false)))

  (defn this-type?
    "Returns true if the-type is the type for a particular git tree"
    [tree the-type]
    (if (= the-type (:type tree))
      true
      false))

  (defn repo
    "Use as a helper function to be used in attach function,
  it should be a function to guarantee that the sha is coming
  from the latest commit on github"
    []
    (let [master-sha (master-sha)
          gituser (:username (git-config))
          gitrepo (:repo (git-config))]
      (->> (data/tree gituser gitrepo master-sha {:recursive true})
           :tree
           (filter #(in-path? % "repo")))))

  (defn get-files
    "Get files associated with a zenid in the zenrepo"
    [zenid repo]
    (let [files (filter #(and (in-path? % (str zenid))
                              (this-type? % "blob"))
                        repo)]
      files))


  (def ^:private curdir fs/*cwd*)
  (def ^:private dirname "jojontemp")

  (defn- create-dir
    "Create a temporary directory in this application"
    [dirname zenid]
    (do (fs/mkdir dirname)
        (fs/mkdir (str dirname "/" zenid))
        (str dirname "/" zenid "/")))

  (defn- remove-dir
    "Remove the temporary directory"
    [dirname]
    (fs/delete-dir (str dirname)))

  (defn- filename
    "Returns the name only of a file from git blob"
    [blob]
    (last (cstr/split (:path blob) #"/")))

  (defn cache-files
    "Cache the files associated with the zenid found in the zenrepo"
    [repo zenid]
    (let [files (get-files zenid repo)
          notes-dir (create-dir dirname zenid)]
      (loop [blobs files res []]
        (if (empty? blobs)
          {:dir notes-dir :files res}
          (let [url (:url (first blobs))
                name (filename (first blobs))
                git-option {:headers {"Accept" "application/vnd.github.v3.raw"}}
                file-content (:body @(http/get url git-option))]
            (recur (do (spit (str notes-dir name) file-content)
                       (rest blobs))
                   (conj res name)))))))

  (defn- get-notes-id
    "Get notes id from couchdb"
    [cdb zenid]
    (-> (cl/get-view cdb
                     "Notes"
                     "byZenid"
                     {:key zenid})
        first
        :value
        :_id))

  (defn- attach-file
    "Attach the file into couchdb"
    [cdb docid filepath]
    (cl/put-attachment cdb
                       (cl/get-document cdb docid)
                       filepath))

  (defn attach
    "This is the only useful public function in this namespace,
  the role of this function is to attach all files in a notes
  repo on github into the corresponding database content of
  that notes in couchdb (cloudant in production). To call this
  function, you must use the repo function (repo) to fill the
  first argument, cdb should be obtain from
  thepretelan.control.dbconfig mainspace, and zenid is a valid
  notes-id (integer, not string)"
    [repo cdb zenid]
    (let [filemap (cache-files repo zenid)
          filepaths (map #(str (:dir filemap)
                               %)
                         (:files filemap))]
      (loop [filepath filepaths]
        (if (empty? filepath)
          (remove-dir (:dir filemap))
          (recur (do (attach-file cdb
                                  (get-notes-id cdb zenid)
                                  (first filepath))
                     (rest filepath))))))))
