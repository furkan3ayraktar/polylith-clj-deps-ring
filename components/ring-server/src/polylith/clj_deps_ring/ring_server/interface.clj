(ns polylith.clj-deps-ring.ring-server.interface
  (:require [polylith.clj-deps-ring.ring-server.core :as core]))

(defn start!
  ([]
   (core/start!))
  ([join?]
   (core/start! join?)))

(defn stop! []
  (core/stop!))
