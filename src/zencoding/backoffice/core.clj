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

