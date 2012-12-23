(ns notes.handler
  (:use [compojure.core]
        [hiccup.core]
        [hiccup.form]
        [ring.util.response])
  
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [monger.core :as mg]
            [monger.collection :as mc])
  (:import [org.joda.time DateTime]
           [org.bson.types ObjectId]))

(def db-uri "mongodb://drutten:gena@localhost:27017/notes")

;add timestamp

(mg/connect-via-uri! db-uri)

(defn save-note! [note]
  (mc/insert "notes" note))

(defn find-notes []
    (mc/find-maps "notes"))

(defn delete-note! [id] 
  (mc/remove-by-id "notes" (ObjectId. id)))

(defn front-page [notes added?] 
  (html [:h1 "Notes!"]
        (if added? (html "Note added!" [:br]))
        [:table
         [:tr [:th "Subject"] [:th "Note"] [:th]]
        (for [note notes]
          [:tr [:td (:heading note)] [:td (:body note)] [:td (form-to [:delete "/"] (hidden-field :id (:_id note)) (submit-button "Delete"))]])
         ]
        (form-to [:post "/"]
                 "Heading"
                 [:br]
                 (text-field :heading)
                 [:br]
                 "Note"
                 [:br]
                 (text-area :body)
                 [:br]
                 (submit-button "submit"))))

(defroutes app-routes
  (GET "/" [] (front-page (find-notes) false))
  (POST "/" [heading body] 
        (do
          (save-note! {:heading heading :body body})
          (front-page (find-notes) true)))
  (DELETE "/" [id]
         (do
          (delete-note! id)
          (redirect "/")))
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))