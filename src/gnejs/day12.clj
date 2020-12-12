(ns gnejs.day12
  (:require [clojure.string :refer [join]]
            [gnejs.aop2020 :refer [read-and-parse]]))

(defn input [name]
  (read-and-parse name
                  (fn [line]
                    (vector (-> (str (first line))
                                (keyword))
                            (-> (join (rest line))
                                (Integer/parseInt))))))
(defn turn [dir [lr amount]]
  (let [turn-count (quot amount 90)
        turns      (case lr
                     :R (cycle [:N :E :S :W])
                     :L (cycle [:N :W :S :E]))]
    (first
     (drop turn-count
           (drop-while #(not= % dir) turns)))))

(defn rotate [[x y] n]
  (let [rads (* n (/ Math/PI 180))]
    (vector
     (Math/round
      (+ (* x (Math/cos rads))
         (* y (Math/sin rads))))
     (Math/round
      (+ (* (- x) (Math/sin rads))
         (* y (Math/cos rads)))))))

(defn move [{:keys [dir pos] :as state}
            [action value :as op]]
  (case action
    :N (update state :pos #(map + % (map * [0 -1] [1 value])))
    :S (update state :pos #(map + % (map * [0 1] [1 value])))
    :E (update state :pos #(map + % (map * [1 0] [value 1])))
    :W (update state :pos #(map + % (map * [-1 0] [value 1])))
    :F (move state [dir value])
    :R (update state :dir #(turn % op))
    :L (update state :dir #(turn % op))))

(defn move-2 [{:keys [waypoint pos] :as state}
              [action value :as op]]
  (let [new-state (case action
                    :N (update state :waypoint #(map + % (map * [0 -1] [1 value])))
                    :S (update state :waypoint #(map + % (map * [0 1] [1 value])))
                    :E (update state :waypoint #(map + % (map * [1 0] [value 1])))
                    :W (update state :waypoint #(map + % (map * [-1 0] [value 1])))
                    :F (update state :pos #(map + % (map * [value value] waypoint)))
                    :R (update state :waypoint rotate (- value))
                    :L (update state :waypoint rotate value))]
    (println "IN:" state " + " op " = " new-state)
    new-state))

(defn solve-1 [ops]
  (let [state (reduce move
                      {:dir :E
                       :pos [0 0]}
                      ops)]
    (println "State:" state)
    (let [[x y] (:pos state)]
      (println "Manhattan distance:" (+ (Math/abs x) (Math/abs y))))))

(defn solve-2 [ops]
  (let [state (reduce move-2
                      {:waypoint [10 -1]
                       :pos [0 0]}
                      ops)]
    (println "State:" state)
    (let [[x y] (:pos state)]
      (println "Manhattan distance:" (+ (Math/abs x) (Math/abs y))))))
