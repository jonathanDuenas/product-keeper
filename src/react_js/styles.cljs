(ns react-js.styles
  (:require [goog.style]
            [garden.core :as g]
            [garden.units :as u]
            [garden.selectors :as s]
            [garden.stylesheet :as stylesheet]
            ))
;; --

(def style
  [[:body {:font-family "'Lucida Sans Unicode', 'Lucida Grande', sans-serif"
           :font-size "0.8em"
           :background "white"
           :margin "0"}]
   [:#container {:height "100vh"}]
   [:#header {:background "#3a8dab"
              :color "white"
              :font-size "1.5em"
              :height "10%"
              :align-items "center"
              :display "flex"}]
   [:#headerF {:background "#34829c"
               :height "1%"}]
   [:.btn {:text-decoration "none"
           :background "#f36f27"
           :color "white"
           :border "1px solid #f36f27"
           :font-size "1em" }]
   [:.btn :input :textarea {:padding "8px 15px"}]
   [:input :textarea {:background "white"
                      :color "black"
                      :box-sizing "border-box"}]
   [:#addBtn {:float "right"}]
   [:table {:width "100%"}]
   [:th {:background "#3866b0"
         :border "1px solid #3866b0"
         :color "white"
         :text-align "left"}]
   [:th :tr {:height "2em"}]
   [:th :td {:padding "5px"}]
   [:td :input :textarea {:border "1px solid #dddee0"}]
   [:table {:border-collapse "collapse"
            :width "100%"}]
   [:.sidesMargin {:margin-left "5%"
                   :margin-right "5%"
                   }]
   [:th {:width "14%"}]
   [:#notesTh {:width "28%"}]
   [:#searchBtn :#addBtn :#searchIn{:margin-top "2%"
                           :margin-bottom "2%"
                           }]
   ])

(goog.style/installStyles (g/css style))


;; -- Modal Window
;; -- CSS Modal window based on the fiddle: http://jsfiddle.net/kumarmuthaliar/GG9Sa/1/

(def modalStyle
  [
   [:.modalDialog {:position "fixed"
                   :font-family "Arial, Helvetica, sans-serif"
                   :top "0"
                   :right "0"
                   :bottom "0"
                   :left "0"
                   :background "rgba(0, 0, 0, 0.6)"
                   :z-index "99999"
                   :opacity "0"
                   :-webkit-transition "opacity 300ms ease-in"
                   :-moz-transition "opacity 300ms ease-in"
                   :transition "opacity 300ms ease-in"
                   :pointer-events "none"}
    [:&:target {
                :opacity "1"
                :pointer-events "auto"
                }]]
   [:#divBorder {:display "block"
                 :width "55%"
                 :height "70%"
                 :position "relative"
                 :margin "10% auto"
                 :background "#fff"
                 }
    [:div {:vertical-align "top"}]
    ] 
   [:#divBorder {:-moz-box-shadow "2px 1px 15px 1px rgba(0,0,0,0.41)"
                 :-webkit-box-shadow "2px 1px 15px 1px rgba(0,0,0,0.41)"
                 :box-shadow "2px 1px 15px 1px rgba(0,0,0,0.41)"
                 }]
   [:#modalHead {:width "100%"
                 :height "42px"
                 :background "#f36f27"
                 :color "white"
                 :margin-bottom "0"
                 :align-items "center"
                 :display "flex"
                 }]
   [:#modalHeadF {:width "100%"
                  :height "4px"
                  :background "#dc6523"
                  :margin "0"
                  }]
   [:#modalTittle {:margin-left "5%"
                   :font-size "1.3em"}]
   [:#closeModal {:color "white"
                  :line-height "25px"
                  :margin-left "auto"
                  :margin-right "5%"
                  :text-align "center"
                  :width "24px"
                  :text-decoration "none"
                  :font-weight "bold"
                  :-webkit-border-radius "12px"
                  :-moz-border-radius "12px"
                  :border-radius "12px"
                  :border "2px solid #dc6523"
                  }]
   [:#grid {:box-sizing "border-box"
            :height "calc(100% - (46px))"
            :margin "0"
            :padding-right "5%"
            :padding-bottom "5%"
            :color "#787676"
            }
    
    [:input :label {:display "inline-block"
                    :width "100%"
                    :vertical-align "middle"
                    }]
    [:input {:flex-grow "1"}]
    ]
   [:.row1 :.row2 {:height "16.5%"
                   :display "flex"
                   :box-sizing "border-box"
                   :padding-top "2%"
                   :margin "0"}
    ]
   [:.row2 {:height "33%"}]
   [:.box1 :.box2 {:width "50%"
                   :height "100%"
                   :margin-left "5%"
                   :display "flex"
                   :flex-direction "column"
                   }]
   [:.box2 {:width "100%"
            }]
   [:#saveBtn {:width "60%"
               :text-align "center"
               :margin-right "0"
               :margin-left "auto"}]
   ])

(goog.style/installStyles (g/css modalStyle))
