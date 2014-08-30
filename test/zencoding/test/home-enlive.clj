(ns zencoding.test.home-enlive
  (:require [expectations :refer :all]
            [zencoding.home.pages :refer :all]))

(expect String
        (homepage))


