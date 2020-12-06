(ns gnejs.day06
  (:require [clojure.set :refer [union intersection]]
            [clojure.string :as str]
            [gnejs.aop2020 :refer [read-lines]]))

(defn groups [input]
  (->> (read-lines input)
       (partition-by str/blank?)
       (remove (partial = (list "")))))

(defn sum [ns] (reduce + 0 ns))

(defn group-score [join-fn answers]
  (count (apply join-fn (map set answers))))

(defn solve-1 [groups]
  (sum (map (partial group-score union) groups)))

(defn solve-2 [groups]
  (sum (map (partial group-score intersection) groups)))
