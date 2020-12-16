(ns gnejs.day16
  (:require [clojure.string :as str]
            [gnejs.aop2020 :refer [read-lines]]))

(defn parse-rule [line]
  (when (not (str/blank? line))
    (let [[id ranges] (str/split line #":\s+" 2)
          predicates  (map (fn [s]
                             (let [[_ start end] (re-find #"(\d+)-(\d+)" s)
                                   start         (Integer/parseInt start)
                                   end           (Integer/parseInt end)]
                               (fn [n] (<= start n end))))
                           (str/split ranges #"\s+or\s+"))]
      [id (apply some-fn predicates)])))

(defn parse-ticket [line]
  (when (not (str/blank? line))
    (map #(Integer/parseInt %) (str/split line #","))))

(defn input [name]
  (let [lines             (read-lines name)
        [rule-lines tail] (split-with (fn [l] (not= l "your ticket:")) lines)
        rules             (into {} (keep parse-rule rule-lines))
        my-ticket         (parse-ticket (second tail))
        nearby-tickets    (keep parse-ticket (next (drop-while (partial not= "nearby tickets:") tail)))]
    {:rules rules
     :my-ticket my-ticket
     :nearby-tickets nearby-tickets}))

(defn solve-1 [name]
  (let [{:keys [rules nearby-tickets]} (input name)
        pred                           (complement (apply some-fn (vals rules)))]
    (->> (mapcat identity nearby-tickets)
         (filter pred)
         (reduce + 0))))

(defn rules-matching [rules ns]
  (set (keep
        (fn [[id p]] (when (every? p ns) id))
        rules)))

(defn solve-2 [name]
  (let [{:keys [rules
                my-ticket
                nearby-tickets]} (input name)
        valid-tickets            (filter (partial every? (apply some-fn (vals rules))) nearby-tickets)
        positions                (apply map list valid-tickets)
        seed                     (map (partial rules-matching rules) positions)
        found-fn                 (fn [sets]
                                   (reduce (fn [s v] (if (= (count v) 1) (conj s (first v)) s))
                                           #{} sets))]
    (loop [result seed]
      (let [found (found-fn result)]
        (if (= (count found) (count positions))
          ;; Done
          (map (fn [n id] [id n]) my-ticket (map first result))

          (recur
           (map (fn [s]
                  (if (= (count s) 1) s (clojure.set/difference s found))) result)))))))
