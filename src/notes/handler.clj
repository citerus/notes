(ns notes.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [monger.core :as mg]))

(def db-uri "mongodb://user:secret@127.0.0.1/notes:27017")
(mg/connect-via-uri! db-uri)

(defroutes app-routes
  (GET "/" [] "Hello World")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
