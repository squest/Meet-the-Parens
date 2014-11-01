(ns zencoding.home.pages
  (:require [net.cgrand.enlive-html :refer :all]
            [net.cgrand.reload :refer [auto-reload]]
            [zencoding.util :refer [md->html]]
            [zencoding.util :as util]))

;;TODO using expectation in every namespace, using lein autoexpect in terminal

;;just rendering, no database access
;;functionalities provided by core

(def dir "../resources/pages/")



(declare page-list ct-home ct-courses ct-login)

(deftemplate tmp-home (str dir "index.html")
             [whichpage]
             [:#title]  (content "Zenius Coding")
             [:#content] (content (get page-list whichpage)))

(def page-list
  {:home (ct-home)
   :courses (ct-courses)
   :login (ct-login)})



;;Detail implementation
;;TODO masukin :keys dalam snippet
(defn- ct-courses
  []
  (defsnippet course (str dir "courses.html") ))


(defn- ct-home
  []
  (defsnippet home (str dir "home.html") ))


(defn- ct-login
  []
  ((defsnippet login (str dir "login.html") )))





