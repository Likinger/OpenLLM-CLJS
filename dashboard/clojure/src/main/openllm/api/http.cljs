(ns openllm.api.http
  (:require [ajax.core :as ajax]
            [re-frame.core :refer [reg-event-fx]]))

(def api-base-url "http://localhost:3000")


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;              Functions             ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn- get-uri
  "Returns the URI for the given endpoint."
  [endpoint]
  (str api-base-url endpoint))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;               Events               ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(reg-event-fx
 ::v1-generate
 []
 (fn [_ [_ prompt llm-config & {:keys [on-success on-failure]}]]
   {:http-xhrio {:method :post
                 :uri (get-uri "/v1/generate")
                 :params {:prompt prompt
                          :llm_config llm-config}
                 :format          (ajax/json-request-format)
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success      on-success
                 :on-failure      on-failure}}))

(reg-event-fx
 ::v1-metadata
 []
 (fn [_ [_ & {:keys [on-success on-failure]}]]
   {:http-xhrio {:method :post
                 :uri (get-uri "/v1/metadata")
                 :format          (ajax/json-request-format)
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success      on-success
                 :on-failure      on-failure}}))
