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

(defelem home-header []
         [:div {:id "header"}
          [:h1 {:class "heading_supersize"} "Zenius Coding"]
          [:h2 {:class "welcome_text"} "Some text"]])

(defelem home-section-light []
         [:section {:class "section_light"}
          [:div {:class "row"}
           [:div {:class "four columns"}
            [:h3
             [:span {:class "dropcap_red lsf-icon-dropcap"}
                    {:title "camera"} "Some Text"]]
            [:p "lorem ipsum "]]
           [:div {:class "four columns"}
            [:h3
             [:span {:class "dropcap_red lsf-icon-dropcap"}
              {:title "camera"} "Some Text"]]
            [:p "lorem ipsum "]]
           [:div {:class "four columns"}
            [:h3
             [:span {:class "dropcap_red lsf-icon-dropcap"}
              {:title "camera"} "Some Text"]]
            [:p "lorem ipsum "]]]])

(defelem home-section-main []
         [:section {:class "section_main"}
          [:h2 "Some text"]
          [:div {:class "row"}
           [:article {:class "six columns"}
            [:div {:class "panel"}
             [:h3 "lorem ipsum"]
             [:p "Lorem ipsum"]]]
           [:article {:class "six columns"}
            [:div {:class "panel"}
             [:h3 "lorem ipsum"]
             [:p "Lorem ipsum"]]]
           ]])

(defhtml home-complete []
         (html5 (head)
                [:body
                 (nav)
                 (home-header)
                 (home-section-light)
                 (home-section-main)
                 (footer)]))