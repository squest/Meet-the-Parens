(ns zencoding.dbase.config
  (:require [cemerick.url :as curl]
            [com.ashafa.clutch :as cl]
            [zencoding.handler :as handler]
            [com.stuartsierra.component :as component]))

;; ctypes : "_test" "category"  "course" "tutorial"
;; "user" "admin"
;; "problem" "answer" "_id"

(def dbconf (component/start handler/zencoding-app))










