(ns zencoding.routes
  (:require [compojure.core :refer :all]
            [noir.response :as resp]
            [zencoding.home.pages :as page]))



(defroutes home
  (context "/home" req
           (GET "/" (page/tmp-home :home))
           (GET "/login" (page/tmp-home :login)))
  (context "/course" req
           (GET "/" (page/tmp-home :courses))))

