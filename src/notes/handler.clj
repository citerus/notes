(ns notes.handler
  (:use [compojure.core]
        [hiccup.core]
        [hiccup.form])
  
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [monger.core :as mg]
            [monger.collection :as mc])
  (:import [org.joda.time DateTime]))

(def db-uri "mongodb://drutten:gena@localhost:27017/notes")

;add delete
;add timestamp
;add flash on submit

(mg/connect-via-uri! db-uri)

(defn save-note! [note]
  (println "note" note)
  (mc/insert "notes" note))

(defn find-notes []
  (do 
    (println (mc/count "notes"))
    (mc/find-maps "notes")))

(defn front-page [notes] 
  (html [:h1 "Notes!"]
        (println "Notes from db: " notes)
        [:table
         [:tr [:th "Subject"] [:th "Note"]]
        (for [note notes]
          [:tr [:td (:heading note)] [:td (:body note)]])]
        (form-to [:post "/"]
                 (text-field :heading)
                 [:br]
                 (text-area :body)
                 [:br]
                 (submit-button "submit"))))

(defroutes app-routes
  (GET "/" [] (front-page (find-notes)))
  (POST "/" [heading body] 
        (do
          (save-note! {:heading heading :body body})
          (front-page (find-notes))))
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))