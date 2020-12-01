(ns gnejs.day01
  (:require [clojure.math.combinatorics :refer [combinations]]
            [clojure.java.io :as io]))

(defn input []
  (->> (io/resource "day01.txt")
       (io/reader)
       (line-seq)
       (map #(Integer/parseInt %))))

(defn solve [data count target]
  (let [tuples (combinations data count)]
    (first
     (filter
      (fn [ns] (= target (apply + ns)))
      tuples))))

(defn solve-1 [data]
  (let [[a b]  (solve data 2 2020)
        result (* a b)]
    (printf "%d + %d = %d\n" a b (+ a b))
    (printf "%d * %d = %d\n" a b result)
    result))
(defn solve-2 [data]
  (let [[a b c] (solve data 3 2020)
        result  (* a b c)]
    (printf "%d + %d + %d = %d\n" a b c (+ a b c))
    (printf "%d * %d * %d = %d\n" a b c result)
    result))
