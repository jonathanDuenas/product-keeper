(ns ctx.react-js
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            ))

;; -- Contextual debugging

(defn foward [c cxt id]
  "Foward button of contextual widget"
  (if (not= nil cxt)
    (do
      (fn [e]
        (om/update-state! c assoc :prev (:db/id (second cxt)))
        (om/transact! c `[(~(first cxt) ~(second cxt))])
        (om/set-query! c {:params {:root/name (:root/name (om/get-params c))
                                      :root/id (+ id 1)}})))
    nil))

(defn backward [c cxt id]
  "Backward button of contextual widget"
  (do
    (om/update-state! c assoc :prev (:db/id (second cxt)))
    (fn [e]
      (def ob {:id (om/get-state c :prev)})
      (om/transact! c `[(test/remove ~ob)])
      (om/set-query! c {:params {:root/name (:root/name (om/get-params c))
                                    :root/id (if (= id 1) 1 (- id 1))}})
      )
    )
  )

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
            ((foward inst cxt id)))
          nil
          ))
      (dom/div
       #js {:id "contextual"} [(dom/a
                                #js {:onClick (foward inst cxt id) :className "btn" :id "addBtn" :title (str (first cxt) " " id)}
                                ">")
                               (dom/a
                                #js {:onClick (backward inst cxt id) :className "btn" :id "addBtn" :title (om/get-state inst :prev)}
                                "<")]))
    nil)
  ;; -- End contextual
  
  )
