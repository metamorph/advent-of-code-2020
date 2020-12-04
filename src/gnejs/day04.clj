(ns gnejs.day04
  (:require [clojure.spec.alpha :as s]
            [clojure.string :as str]
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
      ;; break or merge
      (if (str/blank? line)
        (recur tail {} (conj passports current))
        (recur tail (merge current (line->map line)) passports)))))

(defn valid-passport-fields? [p]
  (every? (partial contains? p)
          [:byr :iyr :eyr :hgt :hcl :ecl :pid]))

(defn solve-1 [passports] (count (filter valid-passport-fields? passports)))

(defn byr? [s] (and (re-matches #"\d{4}" s) (<= 1920 (Integer/parseInt s) 2002)))
(defn iyr? [s] (and (re-matches #"\d{4}" s) (<= 2010 (Integer/parseInt s) 2020)))
(defn eyr? [s] (and (re-matches #"\d{4}" s) (<= 2020 (Integer/parseInt s) 2030)))
(defn hgt? [s] (when-let [[_ n unit] (re-find #"(\d+)(cm|in)" s)]
                 (case unit
                   "cm" (<= 150 (Integer/parseInt n) 193)
                   "in" (<= 59 (Integer/parseInt n) 76))))
(defn hcl? [s] (some? (re-matches #"#[0-9a-f]{6}" s)))
(def ecl? #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"})
(defn pid? [s] (some? (re-matches #"[0-9]{9}" s)))

(defn valid-passport? [p]
  (and (valid-passport-fields? p)
       (byr? (:byr p))
       (iyr? (:iyr p))
       (eyr? (:eyr p))
       (hgt? (:hgt p))
       (hcl? (:hcl p))
       (ecl? (:ecl p))
       (pid? (:pid p))))

(defn solve-2 [passports] (count (filter valid-passport? passports)))

;; Alternate implementation using Clojure Spec
(s/def ::byr byr?)
(s/def ::iyr iyr?)
(s/def ::eyr eyr?)
(s/def ::hgt hgt?)
(s/def ::hcl hcl?)
(s/def ::ecl ecl?)
(s/def ::pid pid?)
(s/def ::passport (s/keys :req-un [::byr ::iyr ::eyr ::hgt ::hcl ::ecl ::pid]))
(defn solve-2-spec [passports] (count (filter #(s/valid? ::passport %) passports)))
