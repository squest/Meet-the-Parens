(ns zencoding.admin.user
  (:require [zencoding.dbase.config :as dbconfig]
            [com.ashafa.clutch :as cl]
            [questdb.core :as qc]
            [zencoding.util :refer [now]]
            [noir.util.crypt :as crip]))

(def ^:private db dbconfig/dbconf)

(defn user-exists?
  "Returns true if user exists in database, and false otherwise"
  [db username]
  (let [cdb (:cdb db)
        existence (first (cl/get-view cdb "user" "byUsername" {:key username}))]
    (if existence true false)))

(defn add-user!
  "Create a new user into database, accepts db config map and user map"
  [db {:keys [username password nickname nama]}]
  (let [cdb (:cdb db)]
    (if (user-exists? db username)
      {:status false :message "Username already used"}
      (cl/put-document cdb {:username username
                            :password (crip/encrypt password)
                            :nama nama
                            :nickname nickname
                            :ctype "user"
                            :status "free"
                            :regDate (now)}))))

(defn user-valid?
  "Returns true if username and password matches existing data in db"
  [db {:keys [username password]}]
  (if (user-exists? db username)
    (let [cdb (:cdb db)
          user-data (:value (first (cl/get-view cdb
                                                "user"
                                                "byUsername"
                                                {:key username})))]
      (and (= username (:username user-data))
           (crip/compare password (:password user-data))))))











