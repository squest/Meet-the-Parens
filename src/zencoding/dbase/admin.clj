(ns zencoding.dbase.admin
  (:require [cemerick.url :as curl]
            [com.ashafa.clutch :as cl]
            [zencoding.dbase.config :as db]))

(def cdb
  "The couch database used throughout the application"
  db/cdb)

(def ^:private id-ref "46df84459b939ef42691290e7f68dc19")

;; available ctypes:
;; "track" "course" "content"
;; "user" "admin"
;; "problem" "answer"
;; db management -> "_id"

;; A typical data in couchdb must at least contains:
;; {:zenid (get-zenid! cdb "user") :ctype "user"

(defn get-zenid!
  "Returns the new logical zenid for a specific ctype, it increments
  the existing value in database to ensure uniqueness"
  [cdb ctype]
  (let [old-data (if-let [sometest (->> (cl/get-view cdb "zenid" "id")
                                        first
                                        :value)]
                   sometest
                   {:ctype "_id"})
        last-id ((keyword ctype) old-data)
        new-id (if-let [id last-id]
                 (inc id)
                 1)]
    (do (cl/put-document cdb
                         (merge old-data
                                {(keyword ctype) new-id}))
        new-id)))



