(ns zencoding.routes
  (:require [compojure.core :refer :all]
            [noir.response :as resp]
            [zencoding.home.pages :as page]))


(defroutes home
  (context "/home" req
           (GET "/" req "Do nothing here!")))

