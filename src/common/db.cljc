(ns common.db
  (:require [om.next :as om]
            [datascript.core :as d]
            ))


(def conn (d/create-conn {}))

(defmulti read om/dispatch)

(defmethod read :product/id
  [{:keys [state]} _ {:keys [query name]}]
  (if (= name nil)
    {:value (d/q '[:find [(pull ?e ?selector) ...]
                   :in $ ?selector
                   :where [?e :pro/name]]
                 (d/db state) query)}
    {:value (d/q '[:find [(pull ?e ?selector) ...]
                   :in $ ?selector ?name 
                   :where [?e :pro/name ?name]]
                   (d/db state) query name)}
    )
  )

  
(defmulti mutate om/dispatch)

(defmethod mutate 'product/add
  [{:keys [state]} _ param]
  {:value {:keys [_]}
   :action (fn [] (d/transact! state [param]))})
