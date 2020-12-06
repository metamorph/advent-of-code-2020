(ns gnejs.day06
  (:require [clojure.set :refer [union]]
            [clojure.string :as str]
            [gnejs.aop2020 :refer [read-lines]]))

(defn groups [input]
  (->> (read-lines input)
       (partition-by str/blank?)
       (remove (partial = (list "")))))

(defn solve-1 [groups]
  (let [sets (map (fn [group]
                    (reduce (fn [s answer] (union s (set answer)))
                            #{} group))
                  groups)]
    (reduce + 0 (map count sets))))
