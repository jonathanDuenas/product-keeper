(ns amp.views
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            ))

(defn search-input
  [elem]
  (let [{:keys [c p]} elem]
    (dom/input #js {:id "searchIn" :placeholder "Search (by full name)"
                    } nil)))
(defn search-button
  [elem]
  (let [{:keys [c]} elem]
    (dom/button #js {:className "btn" :id "searchBtn"} "SS")))

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
  (let [inst (:this elem)]
    (apply
     dom/div #js {:id "container"}
     [
      (dom/div #js {:id "header"} (dom/label #js {:className "sidesMargin"} "Product Keeper"))
      (dom/div #js {:id "headerF"} nil)
      (apply
       dom/div #js {:className "sidesMargin"}
       [
        (search-input {:c inst :p :search-text :mode :web})
        (search-button {:c inst :mode :web})
        (dom/a
         #js { :className "btn" :id "addBtn"}
         "+ Product Keeper")
        (:body elem)]
            )])))
