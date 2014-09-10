(ns zencoding.util
  (:require [noir.io :as io]
            [markdown.core :as md]
            [clj-time.local :as t]))

(defn md->html
  "reads a markdown file from public/md and returns an HTML string"
  [filename]
  (md/md-to-html-string (slurp (str "resources/" filename))))

(defn now
  "Returns the string of current date"
  []
  (subs (str (t/local-now)) 0 10))


