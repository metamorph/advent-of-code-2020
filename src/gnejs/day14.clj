(ns gnejs.day14
  (:require [gnejs.aop2020 :refer [read-lines]]
            [clojure.string :refer [starts-with?]]))

(defn dec->binary [n] (Integer/toBinaryString n))
(defn binary->dec [s] (Integer/parseInt s 2))
(defn mask-fn [mask]
  (let [ops (reverse mask)]
    (fn [n]
      (reduce
       (fn [v [idx m]]
         (case m
           \1 (bit-set v idx)
           \0 (bit-clear v idx)
           v))
       n (map vector (iterate inc 0) ops)))))

(defn input [name]
  (map (fn [l]
         (if (starts-with? l "mask")
           (let [[_ m] (re-find #"mask\s+=\s+(.*)" l)]
             [:mask m])
           (let [[_ k v] (re-find #"mem\[(\d+)\]\s+=\s+(\d+)" l)]
             [:set (Integer/parseInt k) (Integer/parseInt v)])))
       (read-lines name)))

(defn run-prg [ops]
  (loop [ops   ops
         mask  nil
         state {}]
    (if-let [[o & args] (first ops)]
       (case o
         :mask (recur (next ops) (mask-fn (first args)) state)
         :set  (recur (next ops) mask (assoc state (first args) (mask (second args)))))
       state)))

(defn solve-1 [name]
  (apply + (vals (run-prg (input name)))))
