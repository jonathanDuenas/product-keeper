(ns react-native.components)

(def ReactNative (js/require "react-native"))
(set! js/window.React (js/require "react"))

(defn create-element [rn-comp opts & children]
  (apply js/React.createElement rn-comp (clj->js opts) children))

(def app-registry (.-AppRegistry ReactNative))
(def view (partial create-element (.-View ReactNative)))
(def scroll-view (partial create-element (.-ScrollView ReactNative)))
(def text-input (partial create-element (.-TextInput ReactNative)))
(def text (partial create-element (.-Text ReactNative)))
(def touchable-highlight (partial create-element (.-TouchableHighlight ReactNative)))
(def modal (partial create-element (.-Modal ReactNative)))


  
  
