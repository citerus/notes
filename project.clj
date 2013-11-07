(defproject notes "0.1.0-SNAPSHOT"
  :description "Simple Notes app to demo PaaS deployment"
  :url "https://github.com/citerus/notes"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.6"]
                 [com.novemberain/monger "1.6.0"]
                 [clj-time "0.6.0"]
                 [hiccup "1.0.4"]
                 [org.clojure/data.json "0.2.3"]
                 [ring/ring-jetty-adapter "1.2.1"]]
  :plugins [[lein-ring "0.8.8"]]
  :ring {:handler notes.handler/app
         :init notes.handler/init}
  :min-lein-version "2.0.0"
  :profiles
  {:dev {:resource-paths ["resources"]
         :dependencies [[ring-mock "0.1.5"]]}})
