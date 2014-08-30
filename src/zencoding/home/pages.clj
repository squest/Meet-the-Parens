(ns zencoding.home.pages
  (:require [net.cgrand.enlive-html :as el]
            [net.cgrand.reload :refer [auto-reload]]
            [zencoding.util :refer [md->html]]))

(def pages "home/pages/")
(def snips "home/snippets/")
(def contents "tutorials/clj001/")

(auto-reload 'zencoding.home.pages)

(el/deftemplate main-template (str pages "base.html")
  []
  [:#subtitle] (el/content "This comes from enlive!!")
  [:#main-content] (el/html-content (md->html (str contents "001.md"))))

(defn homepage
  []
  (apply str (main-template)))
