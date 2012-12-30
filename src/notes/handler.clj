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
            [monger.query :as mq]
            [monger.joda-time])
  (:import [org.joda.time DateTime DateTimeZone]
           [org.bson.types ObjectId]))

(def db-uri "mongodb://drutten:gena@localhost:27017/notes")

(DateTimeZone/setDefault DateTimeZone/UTC)

(mg/connect-via-uri! db-uri)

(defn save-note! [note]
  (mc/insert "notes" note))

(defn find-notes []
  (mq/with-collection "notes"
    (mq/find {})
    (mq/sort {:ts -1})))

(defn delete-note! [id]
  (mc/remove-by-id "notes" (ObjectId. id)))

(defn front-page [notes added?]
  (html
    (html5
      [:head [:title "Notes"]
       (include-css "/css/bootstrap.css")
       (include-css "/css/notes.css")]

      [:body [:div.navbar.navbar-fixed-top [:div.navbar-inner [:div.container (link-to {:class "brand"} "#" "Notes!")]]]

       [:header
        [:div.container (image {:id "note"} "/img/note.png")
         [:div#header-txt [:h1 "Notes!"]
         [:p.lead [:small " Create and read awesome notes, right here!"]]]]]

       [:div.container
        [:div.row-fluid
         [:div.span4 [:div#add-note.affix-top {:data-spy "affix", :data-offset-top "215"}
                                                    (form-to [:post "/"]
                                                      [:fieldset [:label "Heading"]
                                                       (text-field :heading )
                                                       [:label "Note"]
                                                       (text-area {:rows 5} :body )
                                                       [:button.btn {:type "submit"} "Create Note!"] " " (if added? [:i.icon-ok ])])]]

         [:div.span8 (for [note notes]
           [:div.note [:div.row-fluid [:div.span11 [:legend (:heading note)]]
                                      [:div.span1 [:div.del (form-to [:delete "/"] (hidden-field :id (:_id note)) [:button.btn.btn-mini.btn-link {:type "submit"} [:i.icon-remove ]])]]]
                      [:div (:body note)]
                      [:p.text-info [:small (:ts note)]]])]]]

      [:footer
       [:div.container
        [:p.muted.credit "By " (link-to "http://www.citerus.se/" "Citerus") " " [:i.icon-star-empty ]
                                " Styling support by " (link-to "http://twitter.github.com/bootstrap/index.html" "Bootstrap. ") [:i.icon-star-empty ]
                                " Icons by " (link-to "http://glyphicons.com/" "Glyphicons")]]]
       (include-js "/js/jquery-1.8.3.min.js")
       (include-js "/js/bootstrap.js")])))


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