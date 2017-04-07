(ns react-native.android.style)

(def style
  {
   :container {
               :flex 1
               :backgroundColor "#ecf0f1"
               }
   :header {
            :height 50
            :backgroundColor "#3a8dab"
            :justifyContent "center"
            :padding 7
            }
   :body {
          :flex 1
          :alignItems "center"
          :justifyContent "center"
          }
   :tr {
        :alignSelf "stretch"
        :backgroundColor "white"
        :padding 10
        :marginHorizontal 7
        :marginBottom 5
        }
   :searchIn {
              :width 200
              :height 44
              :padding 8
              :backgroundColor "white"
              :borderWidth 1
              :borderRadius 20
              :margin 5
              :borderColor "blue"
              }
   :addbtn {
            :margin 100
            :padding 100
            }
   :button {
            :backgroundColor "#f36f27"
            :padding 10
            :borderRadius 5
            :margin 10
            }
   :btext{
          :color "white"
          :fontSize 16
          }
   :modal{
          :backgroundColor "grey"
          :alignItems "center"
          :justifyContent "center"
          }
   })
