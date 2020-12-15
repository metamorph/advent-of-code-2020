(ns gnejs.day15)

(defn init-state [ns]
  (reduce
   (fn [m [i n]]
     (update m n conj i))
   {} (map vector (iterate inc 1) ns)))

(defn solve-1 [ns turns]
  (loop [picked      (init-state ns)
         turn        (inc (count ns))
         last-picked (last ns)]
    (if (> turn turns)
      last-picked ;; Done!
      (let [[a b & _] (get picked last-picked)]
        (let [n (if b (- a b) 0)]
          (recur (update picked n conj turn) (inc turn) n))))))
