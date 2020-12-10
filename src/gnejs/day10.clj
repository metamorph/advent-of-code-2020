(ns gnejs.day10
  (:require [gnejs.aop2020 :refer [read-and-parse]]))

(defn input [name] (read-and-parse name #(Integer/parseInt %)))

(defn solve [adapters]
  (let [output (+ 3 (apply max adapters))
        ns     (sort
                (concat
                 (cons 0 (seq adapters))
                 (list output)))
        diffs  (map #(Math/abs (apply - %))
                    (partition 2 1 ns))
        diff-1 (count (filter (partial = 1) diffs))
        diff-3 (count (filter (partial = 3) diffs))]
    [diff-1 diff-3 (* diff-1 diff-3)]))
