(ns notes.paas
  (:require
   [clojure.java.io :as io]
   [clojure.data.json :as json]))

(defn localhost-uri [uid pwd]
   (str "mongodb://" uid ":" pwd "@localhost:27017/notes"))

(defn is-dotcloud? []
  (-> (io/file "/home/dotcloud/environment.json") .exists))

(defn dotcloud-uri [uid pwd]
  (let [env-file "/home/dotcloud/environment.json"
        env (with-open [f (io/reader env-file)] (json/read f))
        mongo-host (get env "DOTCLOUD_MONGO_MONGODB_HOST")
        mongo-port (get env "DOTCLOUD_MONGO_MONGODB_PORT")]
    (str "mongodb://" uid ":" pwd "@" mongo-host ":" mongo-port "/notes")))

(defn is-open-shift? []
  (System/getenv "OPENSHIFT_APP_UUID"))

(defn mongo-connection-uri [uid pwd]
  (cond :else (localhost-uri uid pwd)))

;;Roadmap:
;localhost
;dotCloud
;OpenShift
;Heroku
;Jelastic
;AWS Beanstalk
;CloudFoundry
;Azure
