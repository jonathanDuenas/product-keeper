(ns react-js.core
  (:require [goog.dom :as gdom]
            [om.next :as om]
            [common.components :as cmp]
            [common.db :as db]
            [cljsjs.raven]
            ))

(def raven js/Raven)

(.install (.config raven "https://0128947984484f8ebdcf6c7e913cfa0c@sentry.io/153323"))
(enable-console-print!)

(def reconciler
  (om/reconciler
   {:state db/conn
    :shared {:mode :web}
    :parser (om/parser {:read db/read :mutate db/mutate})}))

(om/add-root! reconciler cmp/RootView (gdom/getElement "app"))
