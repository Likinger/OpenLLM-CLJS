(ns openllm.api.components
  (:require [reagent-mui.icons.file-upload :refer [file-upload]]
            [reagent-mui.material.icon-button :refer [icon-button]]
            [re-frame.core :as rf]
            [reagent.core :as r]))

(defn file-upload-button
  "The file upload reagent custom component."
  [{:keys [callback-event]}]
  (let [file-reader (js/FileReader.)]
    (r/create-class
     {:component-did-mount
      (fn [_]
        (.addEventListener file-reader "load"
                           (fn [evt]
                             (let [content (-> evt .-target .-result)]
                               (rf/dispatch [callback-event content])))))
      :render
      (fn []
        [icon-button {:color "inherit"
                      :on-change (fn [evt]
                                   (let [file (-> evt .-target .-files (aget 0))]
                                     (.readAsText file-reader file)))}
         (r/as-element [file-upload])])})))
