(ns notes.paas
  (:require
   [clojure.java.io :as io]))

(defn is-open-shift? []
  (System/getenv "OPENSHIFT_APP_UUID"))

(defn is-dotcloud? []
  (-> (io/file "/home/dotcloud/environment.json") .exists))

(defn is-heroku? [] false)

(defn is-beanstalk [] false)

;(str "mongodb://test:test@" mongo-host ":" mongo-port "/notes")

(is-dotcloud?)

(defn mongo-connection-uri [uid pwd]
  (cond (is-dotcloud?) "dotcloud url"
        (is-open-shift?) "open-shift url"
        (is-heroku?) "heroku-url"
        (is-beanstalk?) "mongohq-url"))

;  (str "mongodb://test:test@" mongo-host ":" mongo-port "/notes"))
