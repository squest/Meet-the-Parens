(ns zencoding.core
	(:require
		[zencoding.handler :refer [app]]
		[ring.middleware.reload :as reload]
		[org.httpkit.server :as http-kit]
		[taoensso.timbre :as timbre]
		[com.ashafa.clutch :as cl]
		[questdb.core :as qdb]
		[com.stuartsierra.component :as com]
		[cemerick.url :as curl])
	(:gen-class))

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

(def zencoding-system (make-app "config.edn"
																:local-couch
																:qdb-dev))

(def server
	"contains function that can be used to stop http-kit server"
	(atom nil))

(defn dev? [args] (some #{"-dev"} args))

(defn parse-port [args]
	(if-let [port (->> args (remove #{"-dev"}) first)]
		(Integer/parseInt port)
		3000))

(defn- start-server [port args]
	(do (com/start zencoding-system)
			(reset! server
							(http-kit/run-server
								(if (dev? args) (reload/wrap-reload app) app)
								{:port port}))))

(defn- stop-server []
	(@server))

(defn -main [& args]
	(let [port (parse-port args)]
		(start-server port args)
		(timbre/info "server started on port:" port)))
