(ns gnejs.day05
  (:require [gnejs.aop2020 :refer [read-lines]]))

(def ROW-RANGE [0 127])
(def COL-RANGE [0 7])
(def char->dir
  {\F :dn
   \B :up
   \L :dn
   \R :up})

(defn partition-range [[low high] dir]
  (let [size (quot (- high low) 2)]
    (case dir
      :up [(- high size) high]
      :dn [low (+ low size)])))

(defn pass->pos
  "Parses a boardingpass code, returns `[row column]`"
  [code]
  (let [[row-seq col-seq] (split-at 7 code)
        row-dirs          (map char->dir row-seq)
        col-dirs          (map char->dir col-seq)]
    [(first (reduce partition-range ROW-RANGE row-dirs))
     (first (reduce partition-range COL-RANGE col-dirs))]))

(defn seat-id [code]
  (let [[row col] (pass->pos code)]
    (+ (* row 8) col)))

(defn solve-1 []
  (let [codes (read-lines "day05.txt")]
    (apply max (map seat-id codes))))

(defn solve-2
  "Maybe it's my input data, but:
  'the seats with IDs +1 and -1 from yours will be in your list'"
  []
  (let [codes (read-lines "day05.txt")
        gaps (map (fn [[a _]] (inc a))
                  (filter
                   (fn [[a b]] (= 2 (- b a)))
                   (partition 2 1 (sort (map seat-id codes)))))]
    (first gaps)))
