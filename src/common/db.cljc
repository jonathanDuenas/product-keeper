(ns common.db
  (:require [om.next :as om]
            [datascript.core :as d]
            ))

(def conn (d/create-conn {}))

(def testContextual
  [{:db/id 536870913
    :prev 536870911
    :transaction `(product/add {:db/id 1491851474691, :pro/name "firstName", :pro/category "firstCat", :pro/brand "firstBrand", :pro/height "firstHeig", :pro/width "firstWid", :pro/notes "firstNot"})
    }
   {:db/id 536870915
    :prev 536870913
    :transaction `(product/add {:db/id 1491851474891, :pro/name "secondName", :pro/category "secondCat", :pro/brand "secondBrand", :pro/height "secondHeig", :pro/width "secondWid", :pro/notes "secondNot"})
    }]
  )

(d/transact! conn testContextual)

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
  {:value (d/q '[:find ?e
                 :in $ ?id
                 :where [?id :transaction ?e]]
               (d/db state) id)}
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
  [{:keys [state]} _ param]
  {:value {:keys [_]}
   :action (fn [] (d/transact! state [param]))})
