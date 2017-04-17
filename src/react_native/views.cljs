(ns react-native.views
  (:require [om.next :as om :refer-macros [defui]]
            [app.ws :as ws]
            [common.functions :as fn]
            [react-native.android.style :refer [style]]
            [react-native.components :refer [app-registry
                                             modal
                                             touchable-highlight
                                             text
                                             text-input
                                             scroll-view view]]
            ))

;;
;; -- @c parent component
;; -- @p props
;; -- @e target component
;;

(defn change-text [c p e]
  "onChange input method"
  (om/update-state! c assoc
                    p e))


(defn get-box
  [elem]
  (let [{:keys [c name p]} elem]  
  (view {:style {:alignSelf "stretch"
                 :alignItems "center"
                 :justifyContent "center"
                 }}
        (text nil name)
        (text-input
         {:style (:searchIn style)
          :placeholder name
          :placeholderTextColor "#b3b3ff"
          :underlineColorAndroid "transparent"
          :onChangeText #(change-text c p %)
          :value (om/get-state c p)
          }
         ))))



(defn search-input
  [elem]
  (let [{:keys [c p]} elem]
  (if (= (om/get-state c p) nil) (om/update-state! c assoc p "")) 
  (text-input
   {:style (:searchIn style)
    :underlineColorAndroid "transparent"
    :placeholder "Search by full name"
    :placeholderTextColor "#b3b3ff"
    :onChangeText #(change-text c p %)
    :returnKeyType "search"
    :onSubmitEditing #(fn/searchF c)
    :value (om/get-state c p)
    }
   )
  ))

(defn search-button
  [elem]
  (let [{:keys [c]} elem]
  (touchable-highlight
   {:style (:button style)
    :onPress (om/get-state c :SS)
    }
   (text
    {:style (:btext style)
     }
    (om/get-state c :SText)
    )
   )))


(defn modal-window
  [elem]
  (let [{:keys [c]} elem]
  
  (modal
   {:animationType "slide"
    :transparent false
    :visible (om/get-state c :modal)
    :onRequestClose #(om/update-state! c assoc :modal false)
    :style (:modal style)
    }
   (scroll-view
    {:style (:container style)
     }
    (view
     {:style {:alignItems "center"
              :justifyContent "center"
              :flex 1}}
     
     (fn/init-form c)
     (get-box {:c c :name "Product Name" :p :name :mode :app})
     (get-box {:c c :name "Category" :p :category :mode :app})
     (get-box {:c c :name "Brand" :p :brand :mode :app})
     (get-box {:c c :name "Height" :p :height :mode :app})
     (get-box {:c c :name "Width" :p :width :mode :app})
     (get-box {:c c :name "Notes" :p :notes :mode :app})    
     
     (touchable-highlight
      {:style (:button style)
       :onPress #(fn/add c (fn/get-obj c))
       }
      (text {:style (:btext style)} "Add")                   
      )

     (touchable-highlight
      {:style (:button style)
       :onPress #(om/update-state! c assoc :modal false)
       }
      (text {:style (:btext style)} "Cancel")
      )
     ))
   )

  ))

(defn product
  [product]
  (let [{:keys [pro/name pro/category pro/brand pro/height pro/width pro/notes]} (:product product)]
    (view
     {:style (:tr style)}
     (text {:style {:fontSize 16}} (str "Product: " name))
     (text {:style {:fontSize 16}} (str "Brand: " brand))
     (text {:style {:fontSize 16}} (str "Width: " width))
     (text {:style {:fontSize 16}} (str "Height: " height))
     (text {:style {:fontSize 16}} (str "Notes: " notes))
     )    
    )
  )

(defn listview
  [elem]
  (let [{:keys [tbody]} elem]
    (if (= list nil)
      nil
      (view {:style {:alignSelf "stretch"}} tbody)
      )
    ))


(defn root
  [elem]
  (scroll-view
     {:style (:container style)}
     (modal-window {:c (:this elem) :mode :app})
     (view 
      {:style (:header style)}
      (text {:style {:fontSize 16 :color "white"}} "Product Keeper")
      )
     (view
      {:style (:body style)}
      (view {:flex 1 :flexDirection "row"}
            (search-input {:c (:this elem) :p :search-text :mode :app})
            (search-button {:c (:this elem) :mode :app})
            )
      (touchable-highlight
       {:style (:button style)
        :onPress #(om/update-state! (:this elem) assoc :modal true)}
       (text {:style (:btext style)} "+ Product")                   
       )
      (text {:style {:fontSize 16 :color "black"}} (str (om/props (:this elem))))
      (:body elem)
      )
     )
    )
