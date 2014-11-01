(ns pages.index
  (:require [hiccup.core :refer :all]
            [hiccup.page :refer [html5 include-js include-css]]
            [hiccup.def :refer :all]
            [hiccup.form :refer :all]
            [hiccup.element :refer :all]))

(defelem head []
         [:head
            [:title "Front page" ]
            (include-css "stylesheets/foundation.min.css")

           ]
         )

(defelem nav []
         [:div {:class "twelve columns header_nav"}
                {:id "nav"}
                [:div {:class "row"}
                 [:ul {:id "menu-header"}
                      {:class "nav-bar horizontal"}
                      [:li {:class "has-flyout"}
                       [:a {:href "index.html"} "Home"]]
                      [:li {:class "has-flyout"}
                       [:a {:href ""} "Example Pages"]]]]])