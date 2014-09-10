(ns zencoding.handler
  (:require [compojure.core :refer [defroutes]]
            [zencoding.routes :as routes]
            [zencoding.middleware :refer [load-middleware]]
            [zencoding.session-manager :as session-manager]
            [noir.response :refer [redirect]]
            [noir.util.middleware :refer [app-handler]]
            [compojure.route :as route]
            [taoensso.timbre :as timbre]
            [taoensso.timbre.appenders.rotor :as rotor]
            [selmer.parser :as parser]
            [environ.core :refer [env]]
            [cronj.core :as cronj]
						[cemerick.url :as curl]
						[questdb.core :as qdb]
						[com.stuartsierra.component :as com]))

(defrecord ZenCoding [fname whichcouch whichquest]
	com/Lifecycle

	(start [this]
		(let [conf (read-string (slurp "config.edn"))
					couch-config (get conf whichcouch)
					couch (assoc (curl/url (:url couch-config) (:dbname couch-config))
									:username (:username couch-config)
									:password (:password couch-config))
					quest (get conf whichquest)]
			(do (if (qdb/db-exists? quest)
						nil
						(qdb/create! quest))
					(assoc this :cdb couch
											:qdb quest
											:git (:git conf)))))

	(stop [this]))

(defn make-app [fname whichcouch whichquest]
	(->ZenCoding fname whichcouch whichquest))

(def zencoding-app (make-app "config.edn"
																:local-couch
																:qdb-dev))

(defroutes base-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(defn init
  "init will be called once when
   app is deployed as a servlet on
   an app server such as Tomcat
   put any initialization code here"
  []
  (timbre/set-config!
    [:appenders :rotor]
    {:min-level :info
     :enabled? true
     :async? false ; should be always false for rotor
     :max-message-per-msecs nil
     :fn rotor/appender-fn})

	(com/start zencoding-app)

  (timbre/set-config!
    [:shared-appender-config :rotor]
    {:path "zencoding.log" :max-size (* 512 1024) :backlog 10})

  (if (env :dev) (parser/cache-off!))
  ;;start the expired session cleanup job
  (cronj/start! session-manager/cleanup-job)
  (timbre/info "zencoding started successfully"))

(defn destroy
  "destroy will be called when your application
   shuts down, put any clean up code here"
  []
  (timbre/info "zencoding is shutting down...")
  (cronj/shutdown! session-manager/cleanup-job)
  (timbre/info "shutdown complete!"))

(def app (app-handler
           ;; add your application routes here
           [routes/home base-routes]
           ;; add custom middleware here
           :middleware (load-middleware)
           ;; timeout sessions after 30 minutes
           :session-options {:timeout (* 60 30)
                             :timeout-response (redirect "/")}
           ;; add access rules here
           :access-rules []
           ;; serialize/deserialize the following data formats
           ;; available formats:
           ;; :json :json-kw :yaml :yaml-kw :edn :yaml-in-html
           :formats [:json-kw :edn]))
