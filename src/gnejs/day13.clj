(ns gnejs.day13
  (:require [gnejs.aop2020 :refer [read-lines]]
            [clojure.string :refer [split]]))

(defn input [name]
  (let [[target busses] (read-lines name)]
    {:target (Integer/parseInt target)
     :busses (map #(Integer/parseInt %)
                  (remove #(= "x" %) (split busses #",")))}))

(defn next-dep [target id]
  (+ id (* (quot target id) id)))

(defn solve-1 [name]
  (let [{:keys [target busses]} (input name)
        next-deps               (map (fn [bus]
                                       (let [departs (next-dep target bus)]
                                         {:bus       bus
                                          :departs   departs
                                          :wait-time (- departs target)}))
                                     busses)
        dep                     (first (sort-by :wait-time next-deps))]
    (* (:bus dep)
       (:wait-time dep))))

(defn input-2
  "Returns a list of:
  `[buss-nr offset]`"
  [name]
  (let [[_ busses] (read-lines name)
        busses (map (fn [v] (when (not= "x" v) (BigInteger/valueOf (Long/parseLong v))))
                    (split busses #","))]
    (remove #(nil? (first %))
     (map vector busses (iterate inc 0)))))

(defn solve-2
  "What a mess...
   For each bus, find the next T, and step using the product of the previous busses."
  [bao]
  (let [[step _] (first bao)]
    (loop [step-size step
           time step
           [[b o] & tail] (drop 1 bao)]
      (if b
        (let [t (first
                 (filter (fn [t] (zero? (mod (+ t o) b)))
                         (iterate #(+ step-size %) time)))]
          (recur (* step-size b) t tail))
        time))))
