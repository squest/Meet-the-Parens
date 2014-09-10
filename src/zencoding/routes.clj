(ns zencoding.routes
  (:require [compojure.core :refer :all]
            [noir.response :as resp]))

(defroutes home
					 (GET "/" [] "Nothing here to see for now!!"))