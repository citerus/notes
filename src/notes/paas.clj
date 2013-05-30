(ns notes.paas
  (:require
   [clojure.java.io :as io]))

(defn is-open-shift? []
  (System/getenv "OPEN_SHIFT"))

(defn is-dotcloud? []
  (-> (io/file "/home/dotcloud/environment.json") .exists))

;(str "mongodb://test:test@" mongo-host ":" mongo-port "/notes")

(is-dotcloud?)

(defn mongo-connection-uri []


  (str "mongodb://test:test@" mongo-host ":" mongo-port "/notes"))
