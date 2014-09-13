(ns zencoding.backoffice.authentication
  (:require [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds])))
(def users {"admin" {:username "administrator"
                    :password (creds/hash-bcrypt "administrator")
                    :roles #{::admin}}
            })


