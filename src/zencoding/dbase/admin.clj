(ns zencoding.dbase.admin
  (:require [cemerick.url :as curl]
            [com.ashafa.clutch :as cl]
            [zencoding.dbase.config :as db]))

(def cdb db/cdb)

(def id-ref "46df84459b939ef42691290e7f68dc19")

(defn get-zenid!
  "Returns the new logical zenid for a specific ctype, it increments
  the existing value in database to ensure uniqueness"
  [cdb ctype]
  (let [old-data (cl/get-document cdb id-ref)
        last-id ((keyword ctype) old-data)
        new-id (if-let [id last-id]
                 (inc last-id)
                 1)]
    (do (cl/put-document (merge old-data
                                {(keyword ctype) new-id}))
        last-id)))



