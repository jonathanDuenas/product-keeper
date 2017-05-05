(ns app.ws
  (:require [taoensso.sente :as sente]
            [datascript.core :as d]
            [dat.sync.client])
  )

;; Definitions

(def conn (d/create-conn dat.sync.client/base-schema))
(def mySente (atom nil))

;; Local handlers

(defmulti event-msg-handler first)

(defmethod event-msg-handler :dat.sync.client/recv-remote-tx
  [[_ tx-data]]
  (println "Receive :dat.sync.client/recv-remote-tx")
  (dat.sync.client/apply-remote-tx! conn tx-data))

(defmethod event-msg-handler :dat.sync.client/bootstrap
  [[_ tx-data]]
  (println "Receive :dat.sync.client/bootstrap")
  (dat.sync.client/apply-remote-tx! conn tx-data))

(defmethod event-msg-handler :default [{:as event-msg :keys [event id ?data ring-req ?reply-fn send-fn]}] ;; Fallback
  (println "Unhandled event:" id))

;; Sente Handlers

(defmulti -event-handler :id)

(defmethod -event-handler :chsk/handshake
  [{:as ev-msg :keys [?data]}]
  (let [[?uid ?csrf-token ?handshake-data] ?data]
    (.log js/console "Handshake: " ?uid)
    (let [{:keys [send-fn]} @mySente]
      (send-fn [:dat.sync.client/request-bootstrap true])
      )
    ))

(defmethod -event-handler :chsk/recv
  [{:as ev-msg :keys [event]}]
  (event-msg-handler (second event)))

(defmethod -event-handler :default [req] ;; Fallback
  (.log js/console "[-event-handler] Default: " req))

;; Public Method

(defn startSente [path host]
  (reset! mySente (sente/make-channel-socket! path {:host host} ))
  (sente/start-chsk-router! (:ch-recv @mySente) -event-handler)
  )

(defn send! [msg]
  (let [{:keys [send-fn]} @mySente]
    (send-fn msg)
    )
  )

(defn send-tx! [tx]
  (send! [:dat.sync.client/tx (dat.sync.client/datomic-tx @conn tx)])
  )

