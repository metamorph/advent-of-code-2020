(ns gnejs.day11
  (:require [gnejs.aop2020 :refer [read-and-parse]]))

(defn coords [{:keys [rows cols]}]
  (for [row (range rows), col (range cols)] [col row]))

;; Ugly memoization hack to speed things up.
(def nbcoords (memoize (fn [rows cols [px py :as p]]
                         (->> (for [x     (range -1 2) y (range -1 2)
                                    :when (not= 0 x y)] [x y])
                              (map #(map + p %))
                              (filter (fn [[x y]]
                                        (and (<= 0 x (dec cols))
                                             (<= 0 y (dec rows)))))))))

(defn input [name]
  (let [rows   (vec (read-and-parse name vec))
        state  {:rows (count rows)
                :cols (count (first rows))}
        coords (coords state)
        seats (reduce (fn [m [x y]]
                        (let [marker (get (get rows y) x)]
                          (case marker
                            \. m
                            (assoc m [x y] marker))))
                      {} coords)]
    (assoc state :seats seats)))

(defn print-state [{:keys [rows cols seats] :as state}]
  (doseq [y (range rows)]
    (doseq [x (range cols)]
      (print (get seats [x y] \.)))
    (println)))

(defn neighbours-in-status [{:keys [rows cols seats]} pos status]
  (->> (nbcoords rows cols pos)
       (map #(get seats %))
       (filter (partial = status))
       (count)))

(defn dir-coords [rows cols p dir]
  (let [coords (rest (iterate #(map + % dir) p))]
    (take-while (fn [[x y]]
                  (and (<= 0 x (dec cols))
                       (<= 0 y (dec rows))))
                coords)))
(defn neighbours-in-sight [{:keys [rows cols seats]} pos status]
  (let [view-fn (fn [dir]
                  (let [coord (dir-coords rows cols pos dir)]
                    (first
                     (filter #(#{\L \#} %)
                             (map #(get seats %) coord)))))
        dirs (list [-1 0] [-1 -1] [0 -1] [1 -1] [1 0] [1 1] [0 1] [-1 1])]
    (count
     (filter #(= % status)
             (keep (partial view-fn) dirs)))))


(defn apply-seating-rule [{:keys [seats] :as state}
                          pos]
  (let [status (get seats pos)]
    (case status
      \L (if (zero? (neighbours-in-status state pos \#))
           \# \L)
      \# (if (<= 4 (neighbours-in-status state pos \#))
           \L \#)
      ;; leave as is
      status)))

(defn apply-seating-rule-2 [{:keys [seats] :as state}
                            pos]
  (let [status (get seats pos)]
    (case status
      \L (if (zero? (neighbours-in-sight state pos \#))
           \# \L)
      \# (if (<= 5 (neighbours-in-sight state pos \#))
           \L \#)
      ;; leave as is
      status)))

(defn step [{:keys [seats] :as state} rule-fn]
  (assoc state :seats
         (reduce (fn [m [[x y :as pos] _]]
                   (assoc m pos (rule-fn state pos)))
                 {} seats)))

(defn solve [state seating-rule-fn]
  (let [stable (->> (iterate #(step % seating-rule-fn) state)
                    (partition 2 1)
                    (drop-while #(apply not= %))
                    (first)
                    (first))]
    (print-state stable)
    (println "Taken seats:"
             (count (filter (fn [[_ v]] (= v \#)) (:seats stable))))
    stable))

(defn solve-1 [state] (solve state apply-seating-rule))
(defn solve-2 [state] (solve state apply-seating-rule-2))
