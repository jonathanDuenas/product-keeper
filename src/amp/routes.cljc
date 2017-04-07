(ns amp.routes
  (:use     [hiccup.middleware :only (wrap-base-url)])
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]
            [om.next :as om]
            [om.dom :as dom :include-macros true]
            [common.components :as cmp]
            [common.db :as db]
            ))

(defn template [{:keys [body style]}]
  (str
      "<!doctype html>
 <html ⚡>
   <head>
     <meta charset=\"utf-8\">
     <link rel=\"canonical\" href=\".html\" >
     <meta name=\"viewport\" content=\"width=device-width, minimum-scale=1 ,initial-scale=1\">
     <style amp-boilerplate>body{-webkit-animation:-amp-start 8s steps(1,end) 0s 1 normal both;-moz-animation:-amp-start 8s steps(1,end) 0s 1 normal both;-ms-animation:-amp-start 8s steps(1,end) 0s 1 normal both;animation:-amp-start 8s steps(1,end) 0s 1 normal both}@-webkit-keyframes -amp-start{from{visibility:hidden}to{visibility:visible}}@-moz-keyframes -amp-start{from{visibility:hidden}to{visibility:visible}}@-ms-keyframes -amp-start{from{visibility:hidden}to{visibility:visible}}@-o-keyframes -amp-start{from{visibility:hidden}to{visibility:visible}}@keyframes -amp-start{from{visibility:hidden}to{visibility:visible}}</style><noscript><style amp-boilerplate>body{-webkit-animation:none;-moz-animation:none;-ms-animation:none;animation:none}</style></noscript>
     <script async src=\"https://cdn.ampproject.org/v0.js\"></script>"
      style
      "</head>"
      body
      "</html>"))

(def style
  (str
   "<style amp-custom>
     body {
            background-color: white;
     }
     #header { background-color: #3a8dab;
               color: white;
               font-size: 1.5em;
               height: 60px;
               align-items: center;
               display: flex;
     }
     #headerF { background-color: #34829c;
                height: 10px;
                margin-bottom: 10px;
     }
     .btn {text-decoration: none;
           background-color: #f36f27;
           color: white;
           border: 1px solid #f36f27;
           font-size: 1em;
     }
     #addBtn {float: right}
     table { width: 100%;
             margin-top: 10px;
             border-collapse: collapse;     
     }
     th {background: #3866b0;
         border: 1px solid #3866b0;
         color: white;
         text-align: left
     }
     .btn, input {padding: 8px 15px}
     th, tr {height: 2em;}
     th, td {padding: 5px;}
     td, input {border: 1px solid #dddee0;}
     td {width: 20px}
     .sidesMargin { margin-left 10px;
                    margin-right 10px;
     }
    </style>"
   )
  )

(def reconciler
  (om/reconciler
   {:state db/conn
    :normalize false
    :shared {:mode :amp}
    :parser (om/parser {:read db/read :mutate db/mutate})}))

(defn render-om [root reconciler]
  (template {:style style :body (dom/render-to-str (om/add-root! reconciler root nil))}))

(defroutes main-routes
  (GET "/" []
       {:status 200
        :headers {"Content-Type" "text/html"}
        :body (render-om cmp/RootView reconciler )})
  (route/resources "/")
  (route/not-found "Page not found")
  (route/files "/" {:root "resources/public"}))

(def app
  (-> (handler/site main-routes)
      (wrap-base-url)))
