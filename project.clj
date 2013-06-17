(defproject notes "0.1.0-SNAPSHOT"
  :description "Simple Notes app to demo PaaS deployment"
  :url "https://github.com/citerus/notes"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [com.novemberain/monger "1.5.0"]
                 [joda-time/joda-time "2.2"]
                 [hiccup "1.0.3"]
                 [cheshire "5.1.1"]
                 [org.clojure/data.json "0.2.2"]
                 [ring/ring-jetty-adapter "1.1.8"]]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler notes.handler/app
         :init notes.handler/init}
  :min-lein-version "2.0.0"
  :profiles
  {:dev {:resource-paths ["resources"]
         :dependencies [[ring-mock "0.1.3"]]}})
