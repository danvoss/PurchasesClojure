(ns purchases-clojure.core
  (:require [clojure.string :as str])
  (:gen-class))

(defn -main []
  (println "Type a category (Furniture, Alcohol, Toiletries, Shoes, Food, Jewelry):")
  (let [purchases (slurp "purchases.csv")
        purchases (str/split-lines purchases)
        purchases (map (fn [line] (str/split line #","))
                    purchases)
        header (first purchases)
        purchases (rest purchases)
        purchases (map (fn [line] (zipmap header line))
                    purchases)
        category (read-line)
        purchases (filter (fn [line] (= (get line "category") category))
                    purchases)
        file-text (pr-str purchases)]
    (spit "filtered_purchases.edn" file-text)))
    
        
        
        
