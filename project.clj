(defproject notes "0.1.0-SNAPSHOT"
  :description "Simple Notes app to demo PaaS deployment"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [compojure "1.1.3"]
                 [com.novemberain/monger "1.4.1"]
                 [joda-time/joda-time "2.1"]
                 [hiccup "1.0.2"]
                 [cheshire "5.0.1"]]
  :plugins [[lein-ring "0.7.5"]]
  :ring {:handler notes.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.3"]]}})
