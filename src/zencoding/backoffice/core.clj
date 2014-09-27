(ns zencoding.backoffice.core
  (:require [net.cgrand.enlive-html :as html])
  (:use [net.cgrand.moustache :only [app]]
        [zencoding.backoffice.utils :only [run-server render-to-response]]))



(html/deftemplate index "Meet-the-Parens/resources/pages/index.html" ;;path to html file
                  [{:keys [title nav content footer]}]
                  [:#title]  (maybe-content title)
                  [:#nav] (maybe-substitute nav)
                  [:#content] (maybe-substitute content)
                  [:#footer] (maybe-substitute footer))

(html/defsnippet login "Meet-the-Parens/resources/pages/login.html" [:div#content]
                 [{:keys [header form ]}]
                 [:div#header]   (maybe-substitute left)
                 [:div#form] (maybe-substitute middle)
                 )

(html/defsnippet course "Meet-the-Parens/resources/pages/course.html" [:div#content]
                 [{:keys [header form ]}]
                 [:div#header]   (maybe-substitute left)
                 [:div#content] (maybe-substitute middle)
                 )

(defn viewlogin []
  (index {:title "login"
         :content (login {})}))

(defn viewcourse []
  (index {:title "Playground"
          :content (course {})}))
;; ========================================
;; The App
;; ========================================

