(ns ctx.react-js
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [ctx.functions :as fn]
            ))

;; --

(defn controller
  [inst]
  ;; -- Contextual debugging
  (if (not= nil (:test/id (om/props inst)))
    (let [cxt  (cljs.reader/read-string (:test/id (om/props inst)))
          id (:root/id (om.next/get-params inst))]
      (let [parms (mapcat (fn [x] (clojure.string/split x #"\=")) (clojure.string/split (get (clojure.string/split (.-location js/document) #"\?") 1) #"\&"))
            [k1 v1 k2 v2aux] parms v2 (get (clojure.string/split v2aux "#") 0)]
        (print "se hizo el " id " con " cxt " int " v2 )
        (if (and (= v1 "true") (not= cxt nil) (<= id (int v2)))
          (cljs.core.async.macros/go
            (<! (cljs.core.async/timeout 300))
            ((fn/foward inst cxt id)))
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
  
  )
