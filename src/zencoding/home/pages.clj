(ns zencoding.home.pages
  (:require [net.cgrand.enlive-html :as el]
            [net.cgrand.reload :refer [auto-reload]]
            [zencoding.util :refer [md->html]]
            [zencoding.util :as util])
  (:use
        [zencoding.util :only [run-server render-to-response]]))


(el/deftemplate base "../resources/pages/index.html"
  ;; path to html file
  [{:keys [title nav content footer]}]
  [:#title]  (util/maybe-content title)
  [:#nav] (util/maybe-substitute nav)
  [:#content] (util/maybe-substitute content)
  [:#footer] (util/maybe-substitute footer))

(el/defsnippet login "../resources/pages/login.html"
  [:div#content]
  [{:keys [header form ]}]
  [:div#header] (util/maybe-substitute header)
  [:div#form] (util/maybe-substitute form))

(el/defsnippet course "../resources/pages/courses.html"
  [:div#content]
  [{:keys [header contents ]}]
  [:div#header] (util/maybe-substitute header)
  [:div#contents] (util/maybe-substitute contents))

(defn index
  ([] (base {}))
  ([ctxt] (base ctxt)))

(defn viewlogin []
  (base {:title "login"
         :content (login {})}))

(defn viewcourse []
  (base {:title "Playground"
         :content (course {})}))

