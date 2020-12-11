(ns gnejs.day11
  (:require [gnejs.aop2020 :refer [read-and-parse]]))

(defn coords [{:keys [rows cols]}]
  (for [row (range rows), col (range cols)] [col row]))

(defn input [name]
  (let [rows   (vec (read-and-parse name vec))
        state  {:rows (count rows)
                :cols (count (first rows))}
        coords (coords state)
        seats (reduce (fn [m [x y]]
                        (let [marker (get (get rows y) x)]
                          (case marker
                            \. m
                            (assoc m [x y] marker))))
                      {} coords)]
    (assoc state :seats seats)))

(defn print-state [{:keys [rows cols seats] :as state}]
  (doseq [y (range rows)]
    (doseq [x (range cols)]
      (print (get seats [x y] \.)))
    (println)))
