(ns zencoding.routes
  (:require [compojure.core :refer :all]
            [noir.response :as resp]
            [zencoding.home.pages :as page]))

;;Still taken from zencode

(defroutes home
           (GET "/" req
                (homepage/home ""))
           (GET "/login" req
                (homepage/userform :login))
           (POST "/login-act" req
                 (let [{:keys [params]} req
                       {:keys [username password]} params]
                   (if (homectrl/valid-user? {:username username
                                              :password password})
                     (do (session/put! :username username)
                         (resp/redirect "/"))
                     (resp/redirect "/login"))))
           (GET "/logout" req
                (do (session/clear!)
                    (resp/redirect "/")))
           (GET "/signup" req
                (homepage/userform :signup))
           (POST "/signup-act" req
                 (let [{:keys [params]} req
                       {:keys [username password]} params]
                   (if (homectrl/signup {:username username
                                         :password password})
                     (do (session/put! :username username)
                         (resp/redirect "/"))
                     (resp/redirect "/signup")))))

(def backoffice
  (context "/backoffice" req
           (GET "/" [request]
                (backoffice/login ""))
           (GET "/login-try/:message" [message]
                (backoffice/login message))
           (PUT "/login-act" req
                (let [{:keys [params]} req
                      {:keys [username password]} params]
                  (if (valid-password? username password)
                    (do (session/put! :username username)
                        (resp/redirect "/backoffice/home"))
                    (resp/redirect "/backoffice/login-try/Salah Pasword Bro!"))))
           (GET "/home" req
                (if-let [username (session/get :username)]
                  (backoffice/home (str "Welcome bro " username))
                  (resp/redirect "/backoffice/login-try/Login dulu dong bro!")))
           (GET "/logout" req
                (do (session/clear!)
                    (resp/redirect "/backoffice")))))