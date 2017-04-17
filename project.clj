(defproject product-keeper "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
                      :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha10"]
                 [org.clojure/clojurescript "1.9.229"
                  :exclusions [org.clojure/clojure]]
                 [ring "1.5.0"]
                 [compojure "1.5.1"]
                 [cljsjs/react-dom "15.3.1-0"]
                 [cljsjs/react-dom-server "15.3.1-0"]
                 [datascript "0.13.1"]
                 [cljsjs/raven "3.9.1-0"]
                 [org.omcljs/om "1.0.0-alpha48"]

                 ;; DatSync
                 [datsync "0.0.1-alpha2-SNAPSHOT" :exclusions [com.datomic/datomic-free
                                                               com.taoensso/sente
                                                               com.taoensso/encore]]
                 ;; Sente WS
                 [com.taoensso/sente "1.11.0" :exclusions [org.slf4j/slf4j-nop]]
                 [com.taoensso/encore "2.90.1"] ; Stable

                 ;; Mount
                 [mount "0.1.11"]

                 ]
  :plugins [[lein-cljsbuild "1.1.5" :exclusions [[org.clojure/clojure]]]
            [lein-figwheel "0.5.9"]
            [lein-shell "0.5.0"]
            [lein-ring "0.7.1"]
            ]

  :ring {:handler amp.routes/app}
  :clean-targets ^{:protect false}["target/" "resources/public/js"]
  :aliases {"prod-build" ^{:doc "Recompile code with prod profile."}
            ["do" "clean"
             ["with-profile" "prod" "cljsbuild" "once"]]
            "amp-re" ["shell" "sed" "-i" "-e" "s/.*amp/;&/" "src/common/views.cljs"]
            "web-re" ["shell" "sed" "-i" "-e" "s/.*rejs/;&/" "src/common/views.cljs"]
            "app-re" ["shell" "sed" "-i" "-e" "s/.*ren/;&/" "src/common/views.cljs"]
            "res" ["shell" "sed" "-i" "-e" "s/^;*//" "src/common/views.cljs"]
            "web" ["do" "res"
                   ["do" "amp-re" ["do" "app-re"]]]
            "app" ["do" "res"
                   ["do" "amp-re" ["do" "web-re"]]]
            "amp" ["do" "res"
                   ["do" "web-re" ["do" "app-re"]]]
            }
  :profiles {:dev {:dependencies [[figwheel-sidecar "0.5.8"]
                                  [com.cemerick/piggieback "0.2.1"]
                                  [binaryage/devtools "0.9.0"]
                                  [garden "1.3.2"]]
                   :source-paths ["src" "env/dev" "dev"]
                   :cljsbuild    {:builds [{:id "web"
                                            :source-paths ["src/react_js" "src/common" "dev"]
                                            :figwheel {:on-jsload "react_js.core/on-js-reload"
                                                       :open-urls ["http://localhost:3449/index.html"]
                                                       }
                                            :compiler {:main "react_js.core"
                                                       :asset-path "js"
                                                       :output-to "resources/public/js/main.js"
                                                       :output-dir "resources/public/js"
                                                       :source-map-timestamp true
                                                       :preloads [devtools.preload]
                                                       }}
                                            {:id "ios"
                                            :source-paths ["src/react_native" "src/common" "env/dev"]
                                            :figwheel true
                                            :compiler {:output-to "target/ios/not-used.js"
                                                       :main "env.ios.main"
                                                       :output-dir "target/ios"
                                                       :optimizations :none}}
                                           {:id "android"
                                            :source-paths ["src/react_native" "src/common" "env/dev"]
                                            :figwheel true
                                            :compiler {:output-to "target/android/not-used.js"
                                                       :main "env.android.main"
                                                       :output-dir "target/android"
                                                       :optimizations :none}}]}
                   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}
             :prod {:cljsbuild {:builds [{:id "ios"
                                          :source-paths ["src/react_native" "src/common" "env/prod"]
                                          :compiler {:output-to "index.ios.js"
                                                     :main "env.ios.main"
                                                     :output-dir "target/ios"
                                                     :static-fns true
                                                     :optimize-constants true
                                                     :optimizations :simple
                                                     :closure-defines {"goog.DEBUG" false}}}
                                         {:id "android"
                                          :source-paths ["src/react_native" "src/common" "env/prod"]
                                          :compiler {:output-to "index.android.js"
                                                     :main "env.android.main"
                                                     :output-dir "target/android"
                                                     :static-fns true
                                                     :optimize-constants true
                                                     :optimizations :simple
                                                     :closure-defines {"goog.DEBUG" false}}}]}}})
 
