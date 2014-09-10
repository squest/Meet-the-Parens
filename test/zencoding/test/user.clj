(ns zencoding.test.user
  (:require [expectations :refer :all]
            [zencoding.admin.user :refer :all]
            [zencoding.dbase.config :as db]
            [com.ashafa.clutch :as cl]))

(def usernames ["jojon" "jolita" "pickaboo" "packetan" "plesetan" "material" "sampah"])
(def passwords ["Gradle" "Maven" "Leiningen"])
(def nicknames ["jeremiah" "parjordan"])
(def nama-test "Ringgo")
(def new-users (for [u usernames p passwords n nicknames]
                 {:username u :nickname n :nama nama-test :passwords p}))

(def db zencoding.dbase.config/dbconf)

(defn cl-get-users
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

(expect-let [this (doseq [user new-users]
                    (add-user! db user))]
            (inc (count usernames))
            (do (nil? this)
                (count (cl-get-users db))))

(expect-let [new-user {:nama nama-test :password "koni"
                       :username "pasukan" :nickname "nikc"}]
            true
            (do (add-user! db new-user)
                (user-exists? db (:username new-user))))

(expect true
        (user-valid? db {:username "pasukan" :password "koni"}))

(expect-let [users-in-db (take 7 new-users)]
            true
            (apply = (conj (map #(user-valid? db %)
                                users-in-db)
                           true)))

(expect true
        (apply = (->> (for [a usernames b nicknames]
                        {:username a :password b})
                      (map #(user-valid? db %))
                      (cons false))))

(expect-let [data (cl-get-users db)]
            nil
            (doseq [datum (filter #(= (:nama %)
                                      nama-test) data)]
              (cl/delete-document (:cdb db) datum)))

(expect true
        (apply = (map #(user-exists? db %) usernames)))

(expect-let [special {:nama "NotRinggo" :username "special"
                      :password "special" :nickname "nick"}
             this (add-user! db special)]
            1
            (do (nil? this)
                (count (cl-get-users db))))






