(ns notes.handler
  (:use [compojure.core]
        [hiccup.core]
        [hiccup.form]
        [hiccup.page]
        [hiccup.element]
        [ring.util.response])
  
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [monger.core :as mg]
            [monger.collection :as mc]
            [monger.joda-time])
  (:import [org.joda.time DateTime DateTimeZone]
           [org.bson.types ObjectId]))

(def db-uri "mongodb://drutten:gena@localhost:27017/notes")

(DateTimeZone/setDefault DateTimeZone/UTC)

(mg/connect-via-uri! db-uri)

(defn save-note! [note]
  (mc/insert "notes" note))

(defn find-notes []
    (mc/find-maps "notes"))

(defn delete-note! [id] 
  (mc/remove-by-id "notes" (ObjectId. id)))

(defn front-page [notes added?] 
  (html 
   (html5
      [:head [:title "Notes"]
       (include-css "/css/bootstrap.css")
       (include-css "/css/notes.css")
       (include-js "/js/bootstrap.js")
       [:style
        "body {
          padding-top: 60px;
        }"]]
    
      [:body
       [:div.navbar.navbar-fixed-top
        [:div.navbar-inner
         [:div.container
         (link-to {:class "brand"} "#" "Notes!")]]]
       
       [:header
        [:div.container
          [:h1 "Notes!"]
         [:p.lead [:small " Create and read awesome note, right here!"]]]]
       
       [:div.container
        [:div.row-fluid	
         [:div.span4
          
        
      (form-to [:post "/"]
                 "Heading"
                 [:br]
                 (text-field :heading)
                 [:br]
                 "Note"
                 [:br]
                 (text-area :body)
                 [:br]
                 [:button.btn {:type "submit"} "Create Note!"] " " (if added? (html [:i.icon-ok])))]
         
         [:div.span8 
          (for [note notes]
            [:div
            [:div.row-fluid 
              [:div.span10 [:h2 [:small (:heading note)]]]
              [:div.span2 (form-to [:delete "/"] (hidden-field :id (:_id note)) [:button.btn.btn-mini.btn-link {:type "submit"} [:i.icon-remove]])]]
            [:div (:body note)]
            [:p.text-info [:small (:ts note)]]])]]]]
    
    [:footer 
     [:div.container 
      [:p.muted.credit "By " (link-to "http://www.citerus.se/" "Citerus") " " [:i.icon-star-empty]
       " Styling support by " (link-to "http://twitter.github.com/bootstrap/index.html" "Bootstrap. ") [:i.icon-star-empty]
       " Icons by " (link-to "http://glyphicons.com/" "Glyphicons")]]])))
    

(defroutes app-routes
  (GET "/" [] (front-page (find-notes) false))
  
  (POST "/" [heading body] 
        (do
          (save-note! {:heading heading :body body :ts (DateTime.)})
          (front-page (find-notes) true)))
  
  (DELETE "/" [id]
         (do
          (delete-note! id)
          (front-page (find-notes) false)))

  (route/resources "/")
  
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))