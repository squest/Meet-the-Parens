(ns zencoding.routes
  (:require [compojure.core :refer :all]
            [noir.response :as resp]
            [zencoding.home.pages :as hp]))

(def home
  (context "/" req
           (GET "/" req
                (hp/homepage))))

(def courses
  (context "/course" req
           (GET "/" req
                nil)))
