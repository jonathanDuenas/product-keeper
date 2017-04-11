(ns common.components
  (:require [om.next :as om :refer-macros [defui]]
            [common.views :as view]
            ))

(om/defui Product
  static om/Ident
  (ident [this {:keys [db/id]}]
         [:ob/by-id id])
  static om/IQuery
  (query [this]
         '[:db/id :pro/name :pro/category :pro/brand :pro/height :pro/width :pro/notes])
  Object
  (render [this]
          (view/product {:product (om/props this) :mode (:mode (om/shared this))})
          ))

(def product (om/factory Product {:keyfn :db/id}))

(om/defui ListView
  Object
  (render [this]
          (let [list (om/props this)]
            (def ^:dynamic aux list)
            (def ^:dynamic num 0)
            (binding [aux list num 0]
              (if (not= (:mode (om/shared this)) :app)
                (do

                  (while (< (count aux) 7)
                    (do
                      (set! aux (conj aux {:db/id num :pro/name "" :pro/category "" :pro/brand ""
                                           :pro/height "" :pro/width "" :pro/notes ""}))
                      (set! num (+ num 1))
                      )))
                nil)
              (view/listview {:mode (:mode (om/shared this)) :tbody (map product aux)})
              )
            )))

(def list-view (om/factory ListView ))

;; --

(om/defui RootView
  static om/IQueryParams
  (params [this]
          {:root/name nil
           :root/id 536870913
           })
  static om/IQuery
  (query [this]
         (let [subquery (om/get-query Product)]
           `[(:product/id {:query ~subquery :name ?root/name})
             (:test/id {:id ?root/id})
             ]
           ))
  Object
  (render [this]
          (let [{:keys [product/id]} (om/props this)]
            (view/root {:mode (:mode (om/shared this)) :this this :body (list-view id)})
            )))
