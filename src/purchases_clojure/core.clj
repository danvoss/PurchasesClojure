(ns purchases-clojure.core
  (:require [clojure.string :as str])
  (:gen-class))

(defn -main []
  (println "Type a category (Furniture, Alcohol, Toiletries, Shoes, Food, Jewelry):")
  (let [purchases (slurp "purchases.csv")
        purchases (str/split-lines purchases)
        purchases (map (fn [line] (str/split line #",")) ;; alt: (map #(str/split % #",") purchases)
                    purchases)
        header (first purchases)
        purchases (rest purchases)
        purchases (map (fn [line] (zipmap header line)) ;; alt: (map #(zipmap header %) purchases)
                    purchases)
        category (read-line)
        purchases (filter (fn [line] (= (get line "category") category)) ;; (filter #(= category (get % "category")) purchases)
                    purchases)
        file-text (pr-str purchases)] ;; to read later: read-str
    (spit "filtered_purchases.edn" file-text)))
    
        
        
        
