(ns purchases-clojure.core
  (:require [clojure.string :as str]
            [compojure.core :as c]
            [ring.adapter.jetty :as j]
            [hiccup.core :as h])
  (:gen-class))

(defn read-purchases []
  (let [purchases (slurp "purchases.csv")
        purchases (str/split-lines purchases)
        purchases (map (fn [line] (str/split line #",")) ;; alt: (map #(str/split % #",") purchases)
                    purchases)
        header (first purchases)
        purchases (rest purchases)
        purchases (map (fn [line] (zipmap header line)) ;; alt: (map #(zipmap header %) purchases)
                    purchases)]
    purchases))
 
(defn purchases-html [purchases]
  [:html
   [:body
    [:a {:href "/"} "All "]
    [:a {:href "/Furniture"} "Furniture "]
    [:a {:href "/Alcohol"} "Alcohol "]
    [:a {:href "/Toiletries"} "Toiletries "]
    [:a {:href "/Shoes"} "Shoes "]
    [:a {:href "/Food"} "Food "]
    [:a {:href "/Jewelry"} "Jewelry "]
    [:br]
    [:br]
    [:ul
     (map (fn [line]
            [:li (str (get line "customer_id") " " (get line "date") " " (get line "credit_card")
                   " " (get line "cvv") " " (get line "category"))])
       purchases)]]])
     
(defn filter-by-category [purchases category]
  (filter (fn [line]
            (= (get line "category") category))
    purchases))

(c/defroutes app
  (c/GET "/:category{.*}" [category]
    (let [purchases (read-purchases)
          purchases (if (= 0 (count category))
                      purchases
                      (filter-by-category purchases category))]
      (h/html (purchases-html purchases)))))
          
(defonce server (atom nil))

(defn -main []
  (if @server
    (.stop @server))
  (reset! server (j/run-jetty app {:port 3000 :join? false})))
 

        
    
        
        
        
