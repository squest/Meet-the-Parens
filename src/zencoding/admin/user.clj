(ns zencoding.admin.user
  (:require [zencoding.dbase.config :as dbconfig]
            [com.ashafa.clutch :as cl]
            [questdb.core :as qc]
            [zencoding.util :refer [now]]
            [noir.util.crypt :as crip]))

(def ^:private db dbconfig/dbconf)

(defn- cl-get-users
  "Simplify the calling behaviour for cl/get-view user byUsername"
  ([db] (->> (cl/get-view (:cdb db)
                          "user"
                          "byUsername")
             (map :value)))
  ([db username] (->> (cl/get-view (:cdb db)
                                   "user"
                                   "byUsername"
                                   {:key username})
                      first
                      :value)))

(defn- log-user-login
  [username]
  (qc/put-doc! (:qdb db)
               {:username username
                :logDate (now)
                :type "user-login-log"}))

(defn user-exists?
  "Returns true if user exists in database, and false otherwise"
  [username]
  (let [cdb (:cdb db)
        existence (cl-get-users db username)]
    (if existence true false)))

(defn add-user!
  "Create a new user into database, accepts db config map and user map"
  [{:keys [username password nickname nama]}]
  (let [cdb (:cdb db)]
    (if (user-exists? username)
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
  [{:keys [username password]}]
  (if (user-exists? username)
    (let [cdb (:cdb db)
          user-data (cl-get-users db username)]
      (do (log-user-login username)
          (and (= username (:username user-data))
               (crip/compare password (:password user-data)))))))











