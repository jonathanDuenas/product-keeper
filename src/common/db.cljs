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

(defmethod read :test/id
  [{:keys [state]} _ {:keys [id]}]
  {:value (get-in (into [] (->> (d/q '[:find ?e ?doc
                                  :where [?e :support/transaction ?doc]]
                                (d/db state)) (sort-by first)))  [id 1])}
  )

(defmethod read :app/state
  [{:keys [state]} _ {:keys [query]}]
  {:value (d/q '[:find [(pull ?e ?selector) ...]
                 :in $ ?selector
                 :where [?e :app/modal]]
               (d/db state) query)}
  )

(defmulti mutate om/dispatch)

(defmethod mutate 'product/add
  [{:keys [state]} _ param]
  {:value {:keys [_]}
   :action (fn [] (d/transact! state [param]))})

(defmethod mutate 'test/add
  [{:keys [state]} _ param]
  {:value {:keys [_]}
   :action (fn [] (d/transact! state [param]))})

(defmethod mutate 'test/remove
  [{:keys [state]} _ {:keys [id]}]
  {:value {:keys [_]}
   :action (fn [] (d/transact! state [[:db.fn/retractEntity id]]))})

