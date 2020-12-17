(ns gnejs.day17
  (:require [gnejs.aop2020 :refer [read-lines]]))

(defn input
  "Read and return a map `[x y z] -> active?`"
  [name]
  (let [rows (read-lines name)]
    (->> rows
         (map vector (range (count rows)))
         (mapcat (fn [[y row]]
                   (map-indexed
                    (fn [x c]
                      (vector [x y 0] (= c \#)))
                    row)))
         (into {}))))

(defn print-slice
  "Print the status at a plane"
  [status z]
  (let [pts   (filter (fn [[_ _ z']] (= z' z)) (keys status))
        xs    (map first pts)
        ys    (map second pts)
        min-x (apply min xs)
        min-y (apply min ys)]
    (when (seq pts)
      (printf "Reference: [%d, %d]\n" min-x min-x)
      (doseq [y (range min-y (inc (apply max ys)))]
        (doseq [x (range min-x (inc (apply max xs)))]
          (print (if (get status [x y z] false) "#" ".")))
        (println "")))))

(defn surroundings
  "Find the surrounding coords for a point"
  [[x y z]]
  (for [x' (range (dec x) (+ x 2))
        y' (range (dec y) (+ y 2))
        z' (range (dec z) (+ z 2))
        :when (not= [x y z] [x' y' z'])] (vector x' y' z')))

(defn active? [state [x y z :as pt]]
  (let [pt-active? (get state pt false)
        active-neighbours (->> (surroundings pt)
                               (map #(get state % false))
                               (filter true?)
                               (count))]
    (if pt-active?
      (some? (#{2 3} active-neighbours))
      (= active-neighbours 3))))

(defn drop-inactive [state]
  (->> state
       (filter second)
       (into {})))

(defn bounds [state]
  (let [pts (keys state)]
    (vector (vector (dec (apply min (map first pts)))
                    (inc (apply max (map first pts))))
            (vector (dec (apply min (map second pts)))
                    (inc (apply max (map second pts))))
            (vector (dec (apply min (map last pts)))
                    (inc (apply max (map last pts)))))))

(defn turn [state]
  (let [state                                       (drop-inactive state)
        [[min-x max-x] [min-y max-y] [min-z max-z]] (bounds state)]
    (reduce (fn [m [x y z :as pt]] (assoc m pt (active? state pt)))
            {} (for [x (range min-x (inc max-x))
                     y (range min-y (inc max-y))
                     z (range min-z (inc max-z))] [x y z]))))

(defn solve-1 [name turns]
  (let [state (first (drop turns (iterate turn (input name))))]
    (print-slice state 0)
    (count (drop-inactive state))))
