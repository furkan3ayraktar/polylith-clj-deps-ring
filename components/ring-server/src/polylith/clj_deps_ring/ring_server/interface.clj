(ns polylith.clj-deps-ring.ring-server.interface
  (:require [polylith.clj-deps-ring.ring-server.core :as core]))

(defn start!
  ([proj-name]
   (core/start! proj-name))
  ([proj-name join?]
   (core/start! proj-name join?)))

(defn stop! [proj-name]
  (core/stop! proj-name))
