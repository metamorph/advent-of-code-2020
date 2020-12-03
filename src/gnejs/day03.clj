(ns gnejs.day03
  (:require [gnejs.aop2020 :refer [read-lines]]))

(defn slope-map [name]
  ;; Create a matrix of chars
  (mapv vec (read-lines name)))

(defn tree-count [matrix [dx dy :as slope]]
  (let [row-len     (count (first matrix))
        row-count   (count matrix)
        ;; Find the coordinates we will pass through
        path-coords (take row-count (iterate #(mapv + % [dx dy]) [0 0]))
        ;; Collect the terrain on the coordinates
        path        (map (fn [[x y]]
                    (-> (get matrix y)
                        (get (mod x row-len))))
                  path-coords)]
    ;; Count how many trees are in the path
    (count (filter #(= % \#) path))))

(defn solve-1 [matrix]
  (tree-count matrix [3 1]))

(defn solve-2 [matrix]
  (* (tree-count matrix [1 1])
     (tree-count matrix [3 1])
     (tree-count matrix [5 1])
     (tree-count matrix [7 1])
     (tree-count matrix [1 2])))
