(ns zencoding.routes
  (:require [compojure.core :refer :all]
            [noir.response :as resp]))

(def home
  (context "/" req
           (GET "/" req
                "Happy hacking brothers!!")))

(def courses
  (context "/course" req
           (GET "/" req
                nil)))
