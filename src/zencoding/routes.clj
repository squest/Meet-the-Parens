(ns zencoding.routes
  (:require [compojure.core :refer :all]
            [noir.response :as resp]
            [zencoding.home.pages :as page]))

<<<<<<< HEAD
(defroutes home
  (context "/home" req
           (GET "/" req "Do nothing here!")))

=======
(defroutes login
           (GET "/login" [] (util/render-request page/index))
           )

(defroutes course
           (GET "/course" [] (util/render-request page/index))
           )
>>>>>>> FETCH_HEAD
