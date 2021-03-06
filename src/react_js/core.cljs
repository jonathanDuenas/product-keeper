(ns react-js.core
  (:require [goog.dom :as gdom]
            [om.next :as om]
            [common.components :as cmp]
            [common.db :as db]
            [cljsjs.raven]
            [app.ws :as ws]
            [datascript.core :as d]
            ))

(def raven js/Raven)

(.install (.config raven "https://0128947984484f8ebdcf6c7e913cfa0c@sentry.io/153323"))
(enable-console-print!)
(ws/startSente "/chsk" "localhost:8082")

(def reconciler
  (om/reconciler
   {:state ws/conn
    :shared {:mode :web}
    ;; :tx-listen (fn [_ _]
    ;;              (doseq [n (map (fn [n] (+ n 17592186045420)) (into [] (range 0 24 2)))]
    ;;                (ws/send-tx! [[:db.fn/retractEntity n]])
    ;;                )
    ;;              )
    :tx-listen (fn [tx-data tx]
                 (let [key (get (first (:ret tx)) 0)
                       val (get (first (:ret tx)) 1)
                       transaction (get (:tx tx) 0)
                       id nil
                       ]                   
                   (def ob {:db/id -1 :support/transaction (str transaction)})
                   (ws/send-tx!  [ob])
                   )
                )
    :parser (om/parser {:read db/read :mutate db/mutate})}))


(om/add-root! reconciler cmp/RootView (gdom/getElement "app"))


