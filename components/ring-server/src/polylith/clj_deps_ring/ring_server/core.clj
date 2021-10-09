(ns polylith.clj-deps-ring.ring-server.core
  (:require [clojure.java.io :as io]
            [clojure.tools.deps.alpha :as tools-deps]
            [polylith.clj.core.common.interface :as p-common]))

(def running-server (atom nil))

(defn stop! []
  (when (and @running-server (.isStarted @running-server))
    (.stop @running-server)
    (reset! running-server nil)))

(defn serve [deps join?]
  (let [_ (stop!)
        resolved-deps (tools-deps/resolve-deps deps {:extra-deps {'ring-server {:mvn/version "0.5.0"}}})
        lib-paths (into #{} (mapcat #(-> % second :paths) resolved-deps))
        src-paths (map #(-> %
                            (subs 6)
                            (io/file)
                            (.getAbsolutePath)
                            (str "/"))
                       (:paths deps))
        paths (concat lib-paths src-paths)
        class-loader (p-common/create-class-loader paths "none")
        server (p-common/eval-in class-loader `(do
                                                 (use 'ring.server.standalone)

                                                 (let [deps# (quote ~deps)
                                                       load-var# (fn [sym#]
                                                                   (when sym#
                                                                     (require (-> sym# namespace symbol))
                                                                     (find-var sym#)))
                                                       handler# (load-var# (-> deps# :ring :handler))]
                                                   (when-not handler#
                                                     (throw (ex-info "You need to provide a handler in your ring configuration." {:err :invalid-ring-config})))
                                                   (ring.server.standalone/serve
                                                     handler#
                                                     (merge
                                                       {:join?         ~join?
                                                        :auto-reload?  true
                                                        :open-browser? false}
                                                       (dissoc (:ring deps#) :join?)
                                                       {:init                  (load-var# (-> deps# :ring :init))
                                                        :destroy               (load-var# (-> deps# :ring :destroy))
                                                        :stacktrace-middleware (load-var# (-> deps# :ring :stacktrace-middleware))
                                                        :reload-paths          (into (or (-> deps# :ring :reload-paths) [])
                                                                                     (:paths deps#))})))))]
    (when-not join?
      (reset! running-server server))
    nil))

(defn start!
  ([]
   (start! false))
  ([join?]
   (let [deps (-> "deps.edn" slurp read-string)]
     (serve deps join?))))
