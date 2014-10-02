(ns zencoding.routes
  (:require [compojure.core :refer :all]
            [noir.response :as resp]
            [zencoding.home.pages :as page]))

(defroutes home
  (context "/" req
           (GET "/" req "Do nothing here!")
           (GET "/dothis" "blah!!!")))

(defroutes courses
  (context "/courses" req
           (GET "/aopa" req "whatever")
           (GET "/this" req "thdoasda")))


