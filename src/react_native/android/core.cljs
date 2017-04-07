(ns react-native.android.core
  (:require [om.next :as om :refer-macros [defui]]
            [react-native.re-natal.support :as sup]
            [common.db :as db]
            [common.components :as cmp]
            [react-native.components :refer [app-registry]]
            ))

(def ReactNative (js/require "react-native"))
(set! js/window.React (js/require "react"))

(defonce RootNode (sup/root-node! 1))
(defonce app-root (om/factory RootNode))

(def reconciler
  (om/reconciler
   {:state db/conn
    :shared {:mode :app}
    :parser (om/parser {:read db/read :mutate db/mutate})
    :root-render  sup/root-render
    :root-unmount sup/root-unmount
    }))


(defn init []
  (om/add-root! reconciler cmp/RootView 1)
  (.registerComponent app-registry "ProductKeeper" (fn [] app-root)))
