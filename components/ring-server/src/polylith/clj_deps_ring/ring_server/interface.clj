(ns polylith.clj-deps-ring.ring-server.interface
  (:require [polylith.clj-deps-ring.ring-server.core :as core]))

(defn start!
  ([env-name]
   (core/start! env-name))
  ([env-name join?]
   (core/start! env-name join?)))

(defn stop! [env-name]
  (core/stop! env-name))
