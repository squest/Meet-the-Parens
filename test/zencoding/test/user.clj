(ns zencoding.test.user
  (:require [expectations :refer :all]
            [zencoding.admin.user :refer :all]
            [zencoding.dbase.config :as db]
            [com.ashafa.clutch :as cl]
            [questdb.core :as qc]
            [zencoding.util :refer [now]]))

(def usernames ["jojon" "jolita" "pickaboo" "packetan" "plesetan" "material" "sampah"])
(def passwords ["Gradle" "Maven" "Leiningen"])
(def nicknames ["jeremiah" "parjordan"])
(def nama-test "Ringgo")
(def new-users (for [u usernames p passwords n nicknames]
                 {:username u :nickname n :nama nama-test :passwords p}))

(def db zencoding.dbase.config/dbconf)

(defn cl-get-users
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

;; Testing the behaviour of add-user
(expect-let [this (doseq [user new-users]
                    (add-user! user))]
            (inc (count usernames))
            (do (nil? this)
                (count (cl-get-users db))))

;; Testing the user-exists? behaviour
(expect-let [new-user {:nama nama-test :password "koni"
                       :username "pasukan" :nickname "nikc"}]
            true
            (do (add-user! new-user)
                (user-exists? (:username new-user))))

;; Testing user-valid?
(expect true
        (user-valid? {:username "pasukan" :password "koni"}))

(expect-let [data (qc/find-doc (:qdb db)
                               {:username "pasukan"
                                :type "user-login-log"})]
            (now)
            (:logDate data))

;; Bulk-testing the user-valid? behaviour
(expect-let [users-in-db (take 7 new-users)]
            true
            (apply = (conj (map user-valid?
                                users-in-db)
                           true)))

;; Bulk-testing user-valid? behaviour for some random data, expect all
;; to be false
(expect true
        (apply = (->> (for [a usernames b nicknames]
                        {:username a :password b})
                      (map user-valid?)
                      (cons false))))

;; Testing the behaviour of timestamp
(expect-let [regdate (map :regDate (cl-get-users db))]
            true
            (apply = (cons (zencoding.util/now)
                           regdate)))

;; finalising the test and ensure the database back to its original state
(expect-let [data (cl-get-users db)]
            nil
            (doseq [datum (filter #(= (:nama %)
                                      nama-test) data)]
              (cl/delete-document (:cdb db) datum)))

;; after deletion expect user-exists? to return false to all
;; previously added usernames
(expect true
        (apply = (cons false
                       (map #(user-exists? %) usernames))))

;; special user testing whether the deletion happened only to selected users
(expect-let [special {:nama "NotRinggo" :username "veryspecial"
                      :password "special" :nickname "nick"}
             this (add-user! special)]
            1
            (do (nil? this)
                (count (cl-get-users db))))

(expect-let [specials (map :regDate (cl-get-users db))]
            true
            (apply = (cons (zencoding.util/now)
                           specials)))


