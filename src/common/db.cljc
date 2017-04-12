(ns common.db
  (:require [om.next :as om]
            [datascript.core :as d]
            ))

(def conn (d/create-conn {}))

(def testContextual
  [{:db/id 1
    :transaction `(product/add {:db/id 149185147001, :pro/name "firstName", :pro/category "firstCat", :pro/brand "firstBrand", :pro/height "firstHeig", :pro/width "firstWid", :pro/notes "firstNot"})
    }
   {:db/id 2
    :transaction `(product/add {:db/id 1491851474002, :pro/name "secondName", :pro/category "secondCat", :pro/brand "secondBrand", :pro/height "secondHeig", :pro/width "secondWid", :pro/notes "secondNot"})
    }
   {:db/id 3
    :transaction `(product/add {:db/id 1491851475003, :pro/name "thirdName", :pro/category "thirdCat", :pro/brand "thirdBrand", :pro/height "thirdHeig", :pro/width "thirdWid", :pro/notes "thirdNot"})
    }
   {:db/id 4
    :transaction `(product/add {:db/id 1491851475004, :pro/name "fourthName", :pro/category "fourthCat", :pro/brand "fourthBrand", :pro/height "fourthHeig", :pro/width "fourthWid", :pro/notes "fourthNot"})
    }
   {:db/id 5
    :transaction `(product/add {:db/id 1491851475005, :pro/name "fifthName", :pro/category "fifthCat", :pro/brand "fifthBrand", :pro/height "fifthHeig", :pro/width "fifthWid", :pro/notes "fifthNot"})
    }
   {:db/id 6
    :transaction `(product/add {:db/id 1491851475006, :pro/name "sixthName", :pro/category "sixthCat", :pro/brand "sixthBrand", :pro/height "sixthHeig", :pro/width "sixthWid", :pro/notes "sixthNot"})
    }
   ]
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
  [{:keys [state]} _ {:keys [id]}]
  {:value {:keys [_]}
   :action (fn [] (d/transact! state [[:db.fn/retractEntity id]]))})

