(ns ^:figwheel-no-load env.android.main
  (:require [om.next :as om]
            [common.components :as cmp]
            [react-native.android.core :as core]
            [figwheel.client :as figwheel :include-macros true]))

(enable-console-print!)

(figwheel/watch-and-reload
  :websocket-url "ws://10.0.2.2:3449/figwheel-ws"
  :heads-up-display false
  :jsload-callback #(om/add-root! core/reconciler cmp/RootView 1))

(core/init)

(def root-el (core/app-root))