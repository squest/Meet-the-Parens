(ns zencoding.backoffice.core
  (:require [net.cgrand.enlive-html :as html])
  (:use [net.cgrand.moustache :only [app]]
        [zencoding.backoffice.utils :only [run-server render-to-response]]))



(html/deftemplate index "Meet-the-Parens/resources/public/index.html" ;;path to html file
                  [ctxt]
                  [:p#message] (html/content (:message ctxt)))

;; ========================================
;; The App
;; ========================================

(def routes
  (app
    [""]       (fn [req] (render-to-response
                           (index {})))
    ["change"] (fn [req] (render-to-response
                           (index {:message "We changed the message!"})))
    [&]        {:status 404
                :body "Page Not Found"}))

(defonce ^:dynamic *server* (run-server routes))