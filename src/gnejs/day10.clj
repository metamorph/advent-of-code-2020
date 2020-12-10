(ns gnejs.day10
  (:require [gnejs.aop2020 :refer [read-and-parse]]))

(defn input [name] (read-and-parse name #(Integer/parseInt %)))

(defn full-list
  "Pad the adapters with the socket and the device, then sort."
  [adapters]
  (sort (concat
         (cons 0 (seq adapters))
         (list (+ 3 (apply max adapters))))))

(defn routes
  "For each adapter in the chain, calculate how many adapters
   it can be combined with."
  [ns]
  (loop [[n & tail] ns
         acc        '()]
    (if (seq tail)
      (recur tail
             (conj acc
                   (count (take-while #(<= (- % n) 3) tail))))
      (reverse acc))))

(defn solve-1
  "Count the distance between each adapter."
  [adapters]
  (let [ns     (full-list adapters)
        diffs  (map #(Math/abs (apply - %))
                    (partition 2 1 ns))
        diff-1 (count (filter (partial = 1) diffs))
        diff-3 (count (filter (partial = 3) diffs))]
    [diff-1
     diff-3
     ;; Result
     (* diff-1 diff-3)]))

(defn solve-2
  "Using the `routes`, start from the end and aggregate
   the number of branches toward the first adapter."
  [ns]
  (let [branches (reverse (routes (sort ns)))]
    (loop [sum        (list 1)
           [n & tail] branches]
      (if n
        (recur (conj sum (apply + (take n sum))) tail)
        ;; Result
        (first sum)))))
