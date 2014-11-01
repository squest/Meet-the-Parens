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
                       [:a {:href "#"} "Example Pages"]
                       [:ul {:class "flyout"}
                        [:li {:class "has-flyout"}
                         [:a {:href "blog.html"} "blog"]]
                        [:li {:class "has-flyout"}
                         [:a {:href "blog.html"} "blog"]]
                        [:li {:class "has-flyout"}
                         [:a {:href "blog.html"} "blog"]]
                        [:li {:class "has-flyout"}
                         [:a {:href "blog.html"} "blog"]]]]
                      [:li {:class ""}
                       [:a {:href "galleries.html"} "Boxed Gallery"]]
                      [:li {:class ""}
                       [:a {:href "galleries.html"} "Boxed Gallery"]]
                      [:li {:class ""}
                       [:a {:href "galleries.html"} "Boxed Gallery"]]]]])

(defelem footer []
         [:div {:class "row"}
          [:div {:class "twelve colums footer"}
           [:a {:href ""} "PT Zenius Education"]]])

(defelem home []
         [:div {:id "header"}])