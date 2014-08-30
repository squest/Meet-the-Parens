(ns zencoding.home.pages
  (:require [net.cgrand.enlive-html :as el]
            [net.cgrand.reload :refer [auto-reload]]))

(def pages "home/pages/")
(def snips "home/snippets/")

(auto-reload 'zencoding.home.pages)

(el/deftemplate main-template (str pages "base.html")
  []
  [:#subtitle] (el/content "This comes from enlive!!"))

(defn homepage
  []
  (apply str (main-template)))
