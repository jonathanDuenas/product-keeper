(ns react-js.views
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [common.functions :as fn]
            [react-js.styles]
            ))

;;
;; -- @c parent component
;; -- @p props
;; -- @e target component
;;


(defn change-text [c p e]
  "onChange input method"
  (om/update-state! c assoc
                    p (.. e -target -value)))

(defn get-box
  [elem]
  (let [{:keys [c name p]} elem]  
    (apply dom/div #js {:className "box1"}
           [(dom/label nil name)
          (dom/input #js {:value (om/get-state c p)
                          :onChange  #(change-text c p %)
                          })
          ])))


(defn search-input 
  [elem]
  (let [{:keys [c p]} elem]
    (if (= (om/get-state c p) nil) (om/update-state! c assoc p "")) 
    (dom/input #js {:id "searchIn" :placeholder "Search (by full name)"
                    :value (om/get-state c p)
                    :onChange  #(change-text c p %)
                    :onKeyDown #(fn/key-down c %)
                    } nil)))

(defn search-button
  [elem]
  (let [{:keys [c]} elem]
    (dom/button #js {:onClick (om/get-state c :SS)
                     :className "btn" :id "searchBtn"} (om/get-state c :SText))))


(defn modal-window
  [elem]
  (let [{:keys [c]} elem {:keys [db/id app/modal] :as states} (get (:app/state (om/props c)) 0)]
  (apply dom/div #js {:id "openModal" :className "modalDialog"}
         [(apply dom/div #js {:id "divBorder"}
                 [(apply dom/div #js {:id "modalHead"}
                         [(dom/label #js {:id "modalTittle"} "Product Keeper")
                          (dom/a #js {:onClick (fn []
                                                 (om/transact!
                                                  c `[(product/add ~(conj states {:app/modal false}))]))
                                      :id "closeModal"} "x")])
                  (dom/div #js {:id "modalHeadF"} nil)
                  (apply dom/div #js {:id "grid"}
                         [
                          (fn/init-form c)
                          (apply dom/div #js {:className "row1"}
                                 [(get-box {:c c :name "Product Name" :p :name :mode :web})
                                  (get-box {:c c :name "Category" :p :category :mode :web})])
                          
                          (apply dom/div #js {:className "row1"}
                                 [(get-box {:c c :name "Brand" :p :brand :mode :web})
                                  (get-box {:c c :name "Height" :p :height :mode :web})])
                       
                          (apply dom/div #js {:className "row1"}
                                 [(get-box {:c c :name "Width" :p :width :mode :web})
                                  (dom/div #js {:className "box1"} nil)])
                          ;; --

                          (apply dom/div #js {:className "row2"}
                                 [(apply dom/div #js {:className "box2"}
                                         [(dom/label nil "Notes")
                                          (dom/textarea #js {:rows "5"
                                                             :value (om/get-state c :notes)
                                                             :onChange  #(change-text c :notes %)
                                                             })])])
                                                                              
                          (apply dom/div #js {:className "row1"}
                                 [(apply dom/div #js {:className "box1"}
                                         [])
                                  (apply dom/div #js {:className "box1"}
                                         [(dom/a #js {:href "#close"
                                                      :className "btn"
                                                      :id "saveBtn"
                                                      :onClick (fn [e]
                                                                 (if (= (om/get-state c :name) "")
                                                                   (do
                                                                     (js/alert "Cannot add a Product without a name")
                                                                     (fn/clean-form c)
                                                                     )
                                                                   (fn/add c (fn/get-obj c)))
                                                                 )} "Save")])])
                          ])
                  ])
          ])))

(defn product
  [product]
  (let [{:keys [pro/name pro/category pro/brand pro/height pro/width pro/notes]} (:product product)]
    (apply
     dom/tr nil [
                 (dom/td #js {:className "Name"} name)
                 (dom/td nil category)
                 (dom/td nil brand)
                 (dom/td nil height)
                 (dom/td nil width)
                 (dom/td nil notes)
                 ]
     )))


(defn listview
  [elem]
  (apply
   dom/table nil 
   [(apply
     dom/thead nil
     [(apply
       dom/tr nil
       [(dom/th nil "Product Name") (dom/th nil "Category")
        (dom/th nil "Brand") (dom/th nil "Height")
        (dom/th nil "Width") (dom/th #js {:id "notesTh"} "Notes")]  )])
    (apply
     dom/tbody nil
     [(:tbody elem)])]))


(defn root
  [elem]
  (let [inst (:this elem) {:keys [db/id app/modal] :as states} (get (:app/state (om/props inst)) 0)]
    (apply
     dom/div #js {:id "container"}
   [
    (modal-window {:c inst :mode :web})

    (if modal
      ((fn [] (set! (.-href (.-location js/window)) "#openModal") nil))
      ((fn [] (set! (.-href (.-location js/window)) "#") nil))
    )

    ;(print "props in root " (om/props inst))
    (if (and (= (count (:product/id (om/props inst))) 7) (not= (om/get-state inst :ref) "#"))
      (do
        (om/update-state!
         inst assoc :click (fn [e]
                             (om/update-state! inst assoc :search-text "")
                             (fn/searchF inst)
                             (js/alert "The table is full")))
        (om/update-state! inst assoc :ref "#")
        )
      nil
      )
    (dom/div #js {:id "header"} (dom/label #js {:className "sidesMargin"} "Product Keeper"))
    (dom/div #js {:id "headerF"} nil)
    (apply
     dom/div #js {:className "sidesMargin"}
     [
      (search-input {:c inst :p :search-text :mode :web})
      (search-button {:c inst :mode :web})
      (dom/a
       #js {
            :onClick (if (= (om/get-state inst :click) "")
                       (fn [e] (om/update-state! inst assoc :search-text "")
                         (fn/searchF inst)
                         (if (= modal nil)
                           (om/transact! inst `[(product/add ~{:db/id -1 :app/modal true})])
                           (om/transact! inst `[(product/add ~(conj states {:app/modal true}))])
                           )
                         )
                       (om/get-state inst :click))
            :className "btn" :id "addBtn"}
       "+ Product Keeper")
      (:body elem)

      

      ;; -- Contextual debugging 
      (if (not= nil (:test/id (om/props inst)))
        (let [cxt  (cljs.reader/read-string (:test/id (om/props inst)))
              id (:root/id (om.next/get-params inst))]
          (let [parms (mapcat (fn [x] (clojure.string/split x #"\=")) (clojure.string/split (get (clojure.string/split (.-location js/document) #"\?") 1) #"\&"))
                [k1 v1 k2 v2aux] parms v2 (get (clojure.string/split v2aux "#") 0)]
            (print "se hizo el " id " con " cxt " int " v2 )
            (if (and (= v1 "true") (not= cxt nil) (<= id (int v2)))
              ((fn/foward inst cxt id))
              nil
              ))
          (dom/div
           #js {:id "contextual"} [(dom/a
                 #js {:onClick (fn/foward inst cxt id) :className "btn" :id "addBtn" :title (str (first cxt) " " id)}
                 ">")
                (dom/a
                 #js {:onClick (fn/backward inst cxt id) :className "btn" :id "addBtn" :title (om/get-state inst :prev)}
                 "<")]))
        nil)
      
      ;; -- End contextual
      
      ]
     )]))
  )
