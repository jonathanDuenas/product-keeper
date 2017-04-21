(ns ^:figwheel-no-load env.ios.main
  (:require [om.next :as om]
            [common.components :as cmp]
            [react-native.ios.core :as core]
            [figwheel.client :as figwheel :include-macros true]))

(enable-console-print!)

(figwheel/watch-and-reload
  :websocket-url "ws://localhost:3449/figwheel-ws"
  :heads-up-display false
  :jsload-callback #(om/add-root! core/reconciler cmp/RootView 1))

(core/init)

(def root-el (core/app-root))
