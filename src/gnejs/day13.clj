(ns gnejs.day13
  (:require [gnejs.aop2020 :refer [read-lines]]))

(defn input [name]
  (let [[target busses] (read-lines name)]
    {:target (Integer/parseInt target)
     :busses (map #(Integer/parseInt %)
                  (remove #(= "x" %)
                          (clojure.string/split busses #",")))}))

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
