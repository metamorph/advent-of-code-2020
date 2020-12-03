(ns gnejs.day03
  (:require [gnejs.aop2020 :refer [read-lines]]))

(defn slope-map [name]
  ;; Create a matrix of chars
  (mapv vec (read-lines name)))

(defn solve-1 [matrix]
  (let [row-len     (count (first matrix))
        row-count   (count matrix)
        ;; Find the coordinates we will pass through
        path-coords (take row-count (iterate #(mapv + % [3 1]) [0 0]))
        ;; Collect the terrain on the coordinates
        path        (map (fn [[x y]]
                    (-> (get matrix y)
                        (get (mod x row-len))))
                  path-coords)]

    ;; Count how many trees are in the path
    (count (filter #(= % \#) path))))
