(defproject notes "0.1.0-SNAPSHOT"
  :description "Simple Notes app to demo PaaS deployment"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [compojure "1.1.3"]
                 [com.novemberain/monger "1.4.1"]
                 [joda-time/joda-time "2.1"]
                 [hiccup "1.0.2"]
                 [cheshire "5.0.1"]
                 [org.clojure/data.json "0.2.0"]]
  :plugins [[lein-ring "0.8.2"]]
  :ring {:handler notes.handler/app}
  :profiles
  {:dev {:resource-paths ["resources"]
         :dependencies [[ring-mock "0.1.3"]]}})
