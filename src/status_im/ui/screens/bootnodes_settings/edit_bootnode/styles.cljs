(ns status-im.ui.screens.bootnodes-settings.edit-bootnode.styles
  (:require [status-im.ui.components.colors :as colors]
            [status-im.ui.components.styles :as components.styles]
            [status-im.utils.styles :as styles]))

(def edit-bootnode-view
  {:flex              1
   :margin-horizontal 16
   :margin-vertical   15})

(def bottom-container
  {:flex-direction    :row
   :margin-horizontal 12
   :margin-vertical   15})

(def button-container
  {:margin-top        8
   :margin-bottom     16
   :margin-horizontal 16})

(styles/def button
  {:height           52
   :align-items      :center
   :justify-content  :center
   :border-radius    8
   :ios              {:opacity 0.9}})

(def button-label
  {:color     colors/white-persist
   :font-size 17})

(def delete-button
  (assoc button
         :background-color colors/red))

(def container
  components.styles/flex)
