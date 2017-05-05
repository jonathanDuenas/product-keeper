(ns ctx.functions
  (:require [om.next :as om]))

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
