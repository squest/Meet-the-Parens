(ns zencoding.routes
  (:require [compojure.core :refer :all]
            [noir.response :as resp]
            [zencoding.util :as util]
            [zencoding.home.pages :as page]))
;;TODO Namespace dan routing ke 3 page yg udah di define
(defroutes home
  (GET "/" [] (util/render-request page/index))
 )

(defroutes login
           (GET "/" [] (util/render-request page/index))
           )

(defroutes course
           (GET "/" [] (util/render-request page/index))
           )