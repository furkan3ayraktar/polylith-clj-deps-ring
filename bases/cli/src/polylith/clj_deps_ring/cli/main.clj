(ns polylith.clj-deps-ring.cli.main
  (:require [polylith.clj-deps-ring.ring-server.interface :as ring-server])
  (:gen-class))

(defn -main [cmd & _args]
  (try
    (case cmd
      "start" (ring-server/start! true)
      (println "Allowed options: start"))
    (catch Exception e
      (println (.getMessage e))
      (println (.printStackTrace e))
      (System/exit 1))
    (finally
      (System/exit 0))))
