(ns ctx.react-native
  (:require [om.next :as om :refer-macros [defui]]
            [react-native.android.style :refer [style]]
            [react-native.components :refer [touchable-highlight
                                             text view]]
            [ctx.functions :as fn]
            ))

;; --

(defn controller
  [inst]
  ;; -- Contextual debugging
  (if (not= nil (:test/id (om/props inst)))
    (let [cxt  (cljs.reader/read-string (:test/id (om/props inst)))
          id (:root/id (om.next/get-params inst))]
      (if (and (not= cxt nil) (<= id 10))
          (cljs.core.async.macros/go
            (<! (cljs.core.async/timeout 300))
            ((fn/foward inst cxt id)))
          nil
          )
      (touchable-highlight
       {:style (:button style)
        :onPress #(fn/foward inst cxt id)}
       (text {:style (:btext style)} ">")                   
       )
      (touchable-highlight
       {:style (:button style)
        :onPress #(fn/backward inst cxt id)}
       (text {:style (:btext style)} "<")                   
       ))
    nil)
  ;; -- End contextual
  
  )
