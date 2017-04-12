(ns common.functions
  (:require [om.next :as om]
            ))



(defn clean-form [c]
  "Restart the form's input states"
  (doseq [x [:name :category :brand :height :width :notes]]
    (om/update-state! c assoc x "")
  ))

(defn searchF [c]
  "Changes the parameters of the RootView's Query to filter the datoms"
  (let [text (om/get-state c :search-text)]
    (if (not= text "")
      (do
        (om/update-state! c assoc :SS (fn [e] (om/update-state! c assoc :search-text "") (searchF c) ))
        (om/update-state! c assoc :SText "X")
        (om/set-query! c {:params {:root/name (om/get-state c :search-text)
                                   :root/id (:root/id (om/get-params c))}}))
      (do
        (om/update-state! c assoc :SS (fn [e] (searchF c)))
        (om/update-state! c assoc :SText "SS")
        (om/set-query! c {:params {:root/name nil
                                   :root/id (:root/id (om/get-params c))}}))
      ))
  )

(defn init-form [c]
  "Initiate the form's states"
  (if (= (om/get-state c :name) nil)
    (do
      (clean-form c)
      (om/update-state! c assoc :modal false)
      (om/update-state! c assoc :SS (fn [e] (searchF c)))
      (om/update-state! c assoc :SText "SS")
      (om/update-state! c assoc :click "")
      (om/update-state! c assoc :ref "#openModal")
      )
    nil
    ))


(defn get-obj [c]
  "Returns the object to be added in the databse"
  {:db/id (.getTime (js/Date.)) :pro/name (om/get-state c :name) :pro/category (om/get-state c :category)
   :pro/brand (om/get-state c :brand) :pro/height (om/get-state c :height)
   :pro/width (om/get-state c :width) :pro/notes (om/get-state c :notes)})


(defn add [c ob]
  "Add @ob to the database"
  (om/transact! c
                `[(product/add ~ob)])
  (clean-form c)
  (if (= (om/get-state c :mode) "web")
    (clean-form c)
    (om/update-state! c assoc :modal false))
  )

(defn change-text [c p e]
  "onChange input method"
  (if (= (om/get-state c :mode) "web")
    (om/update-state! c assoc p (.. e -target -value))
    (om/update-state! c assoc p e)))



(defn key-down [c e]
  "onKeyDown input method to call the search function on ENTER"
  (if (= (.-keyCode e) 13)
    (searchF c)
    nil
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
