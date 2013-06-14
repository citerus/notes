(ns notes.paas
  (:require
   [clojure.java.io :as io]
   [clojure.data.json :as json]))

(def db-name "notes")

(defn localhost-uri [uid pwd]
  (str "mongodb://" uid ":" pwd "@localhost:27017/" db-name))

;-- dotCloud

(defn is-dotcloud? []
  (-> (io/file "/home/dotcloud/environment.json") .exists))

(defn dotcloud-uri [uid pwd]
  "Get dotCloud MongoDB URI. User 'uid' with password 'pwd' must exist
  for database 'notes'"
  (let [env-file "/home/dotcloud/environment.json"
        env (with-open [f (io/reader env-file)] (json/read f))
        mongo-host (get env "DOTCLOUD_MONGO_MONGODB_HOST")
        mongo-port (get env "DOTCLOUD_MONGO_MONGODB_PORT")]
    (str "mongodb://" uid ":" pwd "@" mongo-host ":" mongo-port "/" db-name)))

;-- OpenShift

(defn is-open-shift? []
  (System/getenv "OPENSHIFT_APP_UUID"))

(defn open-shift-uri [uid pwd]
  "Get OpenShift MongoDB URI. 'uid' and 'pwd' are ignored, instead uses
  OpenShift provided admin credentials for zero conf deployment."
  (str (System/getenv "OPENSHIFT_MONGODB_DB_URL") db-name))

(defn mongo-connection-uri [uid pwd]
  "Try to figure out runtime environment and resolve associated MongoDB
  connection URI. Defaults to localhost and standard port"
  (cond (is-dotcloud?) (dotcloud-uri uid pwd)
        (is-open-shift?) (open-shift-uri uid pwd)
        :else (localhost-uri uid pwd)))

;;Roadmap:
;localhost
;dotCloud
;OpenShift
;Heroku
;Jelastic
;AWS Beanstalk
;CloudFoundry
;Azure
