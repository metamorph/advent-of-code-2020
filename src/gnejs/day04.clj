(ns gnejs.day04
  (:require [clojure.string :as str]
            [gnejs.aop2020 :refer [read-lines]]))

(def KV-PATTERN #"(.*[^\:])\:(.*)")
(defn line->map [line]
  (reduce (fn [m s]
            (if-let [[_ k v] (re-find KV-PATTERN s)]
              (assoc m (keyword k) v)
              m))
          {} (str/split line #"\s+")))

(defn read-batch [name]
  (loop [[line & tail] (read-lines name)
         current       {}
         passports     (list)]
    (if (nil? line)
      ;; No more lines
      (conj passports current)
      (if (str/blank? line)
        (recur tail {} (conj passports current))
        (recur tail (merge current (line->map line)) passports)))))

(defn valid-passport? [p]
  (every? (partial contains? p)
          [:byr :iyr :eyr :hgt :hcl :ecl :pid]))

(defn solve-1 [passports] (count (filter valid-passport? passports)))
