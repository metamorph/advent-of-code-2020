(ns gnejs.day09
  (:require
   [clojure.math.combinatorics :refer [combinations]]
   [gnejs.aop2020 :refer :all]))

(defn input [name]
  (read-and-parse name #(Integer/parseInt %)))

(defn solve-1 [preamble-size ns]
  (loop [idx (inc preamble-size)
         ns  ns]
    (let [preamble (take preamble-size ns)
          n        (first (drop preamble-size ns))
          combs    (remove (fn [[a b]] (= a b)) (combinations preamble 2))
          check?   (some (fn [[a b]] (= (+ a b) n)) combs)]
      (if-not check?
        (do
          (println "Found bad value at index" idx)
          n)
        (recur (inc idx) (next ns))))))

(defn solve-2 [ns target]
  (loop [partition-size 2]
    (when (= partition-size (count ns))
      (println "Not found!"))

    (let [partitions (partition partition-size 1 ns)
          match (first (filter (fn [n] (= target (apply + n))) partitions))]
      (if match
        [(apply min match)
         (apply max match)]
        (recur (inc partition-size))))))
