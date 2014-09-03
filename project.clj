(defproject
  zencoding "0.1.0"
  :repl-options  {:init-ns zencoding.repl}
  :dependencies  [[org.clojure/clojure "1.6.0"]
                  [org.clojure/clojurescript "0.0-2311"]
                  [ring-server "0.3.1"]
                  [com.taoensso/timbre "3.2.1"]
                  [com.taoensso/tower "2.0.2"]
                  [http-kit "2.1.18"]
                  [clj-time "0.8.0"]
                  [enfocus "2.1.0"]
                  [reagent "0.4.2"]
                  [ring-cors "0.1.4"]
                  [lib-noir "0.8.4"]
                  [couchbase-clj "0.2.0"]
                  [com.ashafa/clutch "0.4.0-RC1"]
                  [cheshire "5.3.1"]
                  [com.cemerick/url "0.1.1"]
                  [tentacles "0.2.7"]
                  [kioo "0.4.0"]
                  [rm-hull/monet "0.1.12"]
                  [me.raynes/fs "1.4.6"]
                  [enlive "1.1.5"]
                  [noir-exception "0.2.2"]
                  [environ "1.0.0"]
                  [im.chit/cronj "1.0.1"]
                  [cljs-ajax "0.2.6"]
                  [secretary "1.2.0"]
                  [markdown-clj "0.9.47"]
                  [expectations "2.0.9"]]
  
  :cljsbuild  {:builds
               [{:source-paths ["src-cljs"],
                 :compiler
                 {:pretty-print true,
                  :output-dir "resources/public/js/",
                  :source-map true,
                  :output-to "resources/public/js/app.js",
                  :optimizations :none},
                 :id "dev"}
                {:source-paths ["src-cljs"],
                 :compiler
                 {:pretty-print false,
                  :closure-warnings {:non-standard-jsdoc :off},
                  :output-to "resources/public/js/app.js",
                  :output-wrapper false,
                  :optimizations :none},
                 :id "prod"}]}
  :ring {:handler zencoding.handler/app,
         :init zencoding.handler/init,
         :destroy zencoding.handler/destroy}
  :profiles {:uberjar {:aot :all},
             :production
             {:ring
              {:open-browser? false, :stacktraces? false, :auto-reload? false}},
             :dev
             {:dependencies
              [[ring-mock "0.1.5"]
               [ring/ring-devel "1.3.1"]
               [pjstadig/humane-test-output "0.6.0"]],
              :injections
              [(require 'pjstadig.humane-test-output)
               (pjstadig.humane-test-output/activate!)],
              :env {:dev true}}}
  :url
  "http://example.com/FIXME"
  :main
  zencoding.core
  :jvm-opts
  ["-server"]
  :plugins
  [[lein-ring "0.8.11"]
   [lein-environ "1.0.0"]
   [lein-cljsbuild "1.0.3"]
   [lein-expectations "0.0.8"]
   [codox "0.8.10"]
   [lein-autoexpect "1.2.2"]]
  :description
  "FIXME: write description"
  :min-lein-version "2.0.0")
