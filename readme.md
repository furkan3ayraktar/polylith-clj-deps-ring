# Polylith - Clojure Deps Ring Plugin

This plugin lets you run a ring server application developed in a [Polylith](https://github.com/tengstrand/polylith.git) workspace.

## Usage

Add the following alias to your deps.edn file in the root of your Polylith workspace:

```clojure
:ring {:extra-deps {furkan3ayraktar/polylith-clj-deps-ring
                    {:git/url   "https://github.com/furkan3ayraktar/polylith-clj-deps-ring.git"
                     :sha       "LATEST_COMMIT_SHA_FROM_THIS_REPOSITORY"
                     :deps/root "environments/core"}}

       :main-opts  ["-m" "polylith.clj-deps-ring.cli.main"]}
```

In the configuration above, update the `:sha` with the latest sha from this repository. Also do not forget to replace `ENV_NAME` with your environments name that is a ring server application. Once you have the alias run:

```bash
clj -A:ring start ENV_NAME
```

## During development

The plugin allows you to run a ring server from your REPL as well. In order to run the server from your REPL, include the dependency and run:

```clojure
(require '[polylith.clj-deps-ring.ring-server.interface :as ring-server])

(ring-server/start! "ENV_NAME")
``` 

To stop a running server:

```clojure
(require '[polylith.clj-deps-ring.ring-server.interface :as ring-server])

(ring-server/stop! "ENV_NAME")
``` 
