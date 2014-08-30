(ns zencoding.dbase.config
  (:require [cemerick.url :as curl]
            [com.ashafa.clutch :as cl]))

(def db-config
  "The data for db configuration, coming from an edn file"
  (read-string (slurp "resources/config.edn")))

;; ctypes : "_test" "track" "course" "content" "user" "admin"
;; "problem" "answer" "_id"

(defn configure
  "Returns the complete url with username and password to access the
  database"
  [which-db]
  (if (= :local which-db)
    (->> db-config
         :local-couch
         :dbname)
    (-> (curl/url "https://zenius.cloudant.com/zencode")
        (assoc :username (->> db-config
                              :cloudant
                              :key)
               :password (->> db-config
                              :cloudant
                              :password))
        str)))

(def cdb
  "The actual db used throughout the application"
  (configure :online))

(defn test-input
  "Some test to see if the setup is working"
  [db data]
  (cl/put-document cdb
                   (merge {:ctype "_test"}
                          data)))


