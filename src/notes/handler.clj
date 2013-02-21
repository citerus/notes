(ns notes.handler
  (:use [compojure.core]
        [hiccup.core]
        [hiccup.form]
        [hiccup.page]
        [hiccup.element]
        [ring.util.response]
        [clojure.data],
        [clojure.java.io])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [monger.core :as mg]
            [monger.collection :as mc]
            [monger.query :as mq]
            [monger.joda-time]
            [clojure.data.json :as json])
  (:import [org.joda.time DateTime DateTimeZone]
           [org.bson.types ObjectId]))

(DateTimeZone/setDefault DateTimeZone/UTC)

(def mongo-host "localhost")
(def mongo-port "27017")
(def mongo-username "test")
(def mongo-password "test")
(def environment-file "/home/dotcloud/environment.json")

(if (.exists (as-file environment-file))
(
  (def environment (json/read (reader (file environment-file))))
  (def mongo-host (val (find environment "DOTCLOUD_MONGO_MONGODB_HOST")))
  (def mongo-port (val (find environment "DOTCLOUD_MONGO_MONGODB_PORT")))))

(mg/connect-via-uri! (str "mongodb://" mongo-username ":" mongo-password "@" mongo-host ":" mongo-port "/notes"))

(defn save-note! [note]
  (mc/insert "notes" note))

(defn find-notes []
  (mq/with-collection "notes"
    (mq/find {})
    (mq/sort {:ts -1})))

(defn delete-note! [id]
  (mc/remove-by-id "notes" (ObjectId. id)))

(defn front-page [notes]
  (html
    (html5
      [:head [:title "Notes"]
       (include-css "css/bootstrap.min.css")
       (include-css "css/notes.css")
       (include-css "css/jquery-ui-1.9.2.custom.min.css")]

      [:body [:div.navbar.navbar-fixed-top [:div.navbar-inner [:div.container (link-to {:class "brand"} "#" "Notes!")]]]

       [:header
        [:div.container (image {:id "note"} "img/note.png")
         [:div#header-txt [:h1 "Notes!"]
         [:p.lead [:small " Create and read awesome notes, right here!"]]]]]

       [:div.container
        [:div.row-fluid
         [:div.span4 [:div#add-note.affix-top {:data-spy "affix", :data-offset-top "215"}
                                                    (form-to [:post "./"]
                                                      [:fieldset [:label "Heading"]
                                                       (text-field :heading )
                                                       [:label "Note"]
                                                       (text-area {:rows 5} :body )
                                                       [:button.btn {:type "submit"} "Create Note!"]])]]

         [:div.span8 (for [{:keys [_id, heading, body, added? ts]} notes]
           [(if added? :div#added.note :div.note) [:div.row-fluid [:div.span11 [:legend heading]]
                                      [:div.span1 [:div.del (form-to [:delete "./"] (hidden-field :id _id) [:button.btn.btn-mini.btn-link {:type "submit"} [:i.icon-remove ]])]]]
                      [:div body]
                      [:p.text-info [:small ts]]])]]]

      [:footer
       [:div.container
        [:p.muted.credit "By " (link-to "http://www.citerus.se/" "Citerus") " " [:i.icon-star-empty ]
                                " Styling support by " (link-to "http://twitter.github.com/bootstrap/index.html" "Bootstrap. ") [:i.icon-star-empty ]
                                " Icons by " (link-to "http://glyphicons.com/" "Glyphicons")]]]
       (include-js "js/jquery-1.8.3.min.js")
       (include-js "js/jquery-ui-1.9.2.custom.min.js")
       (include-js "js/bootstrap.min.js")
       (include-js "js/notes.js")])))


(defroutes app-routes
  (GET "/" [] (front-page (find-notes)))

  (POST "/" [heading body]
    (do
      (save-note! {:heading heading :body body :ts (DateTime.)})
      (let [notes (find-notes)]
        (front-page (cons (assoc (first notes) :added? true) (rest notes))))))

  (DELETE "/" [id]
    (do
      (delete-note! id)
      (front-page (find-notes))))

  (route/resources "/")

  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
