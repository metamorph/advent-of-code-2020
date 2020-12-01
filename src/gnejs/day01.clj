(ns gnejs.day01
  (:require [clojure.math.combinatorics :refer [combinations]]
            [clojure.java.io :as io]))

(defn input []
  (->> (io/resource "day01.txt")
       (io/reader)
       (line-seq)
       (map #(Integer/parseInt %))))

(defn solve [data count target]
  (->> (combinations data count)
       (filter (fn [ns] (= target (apply + ns))))
       (first)))

(defn solve-1 [data] (apply * (solve data 2 2020)))

(defn solve-2 [data] (apply * (solve data 3 2020)))
