(ns common.views ;; DO NOT COMMENT AT BEGINNING OF LINE IN THIS FILE
  (:require
   [react-js.views :as rejs]
;   [amp.views :as amp]
;   [react-native.views :as ren] 
   ))

(defmulti product :mode)
(defmulti listview :mode)
(defmulti root :mode)

(defmethod product :web [product] (rejs/product product))
;(defmethod product :amp [product] (amp/product product))
;(defmethod product :app [product] (ren/product product))

(defmethod listview :web [elem] (rejs/listview elem))
;(defmethod listview :amp [elem] (amp/listview elem))
;(defmethod listview :app [elem] (ren/listview elem))

(defmethod root :web [elem] (rejs/root elem))
;(defmethod root :amp [elem] (amp/root elem))
;(defmethod root :app [elem] (ren/root elem))
