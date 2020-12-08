(ns gnejs.day08
  (:require [gnejs.aop2020 :refer [read-lines]]))

(def OP-PATTERN #"(\w+)\s+([+-])(\d+)")
(defn line->op [line]
  (let [[_ op s arg] (re-find OP-PATTERN line)
        arg          (Integer/parseInt arg)]
    [(keyword op)
     (case s
       "+" arg
       "-" (- arg))]))

(defn lines->prg [lines]
  (let [ops (mapv line->op lines)]
    {:ops ops}))

(defn run-prg
  "Try running `prg` to a successful state. Returns either
  [:success state] or
  [:error state]"
  [prg]
  (loop [{:keys [ops ptr acc stack] :as state}
         (-> prg
             (assoc :ptr 0)
             (assoc :acc 0)
             (assoc :stack (list)))]

    (let [[op arg]   (get ops ptr)
          next-state (case op
                       :nop (update state :ptr inc)
                       :jmp (update state :ptr #(+ % arg))
                       :acc (-> state
                                (update :acc #(+ % arg))
                                (update :ptr inc)))]
      (cond
        ;; Detect the infinite loop
        (contains? (set stack) (:ptr next-state))
        [:error (select-keys next-state [:ptr :acc])]

        ;; Are we done?
        (= (:ptr next-state) (count ops))
        [:success (select-keys next-state [:ptr :acc])]

        ;; Next
        :else
        (recur (update next-state :stack conj ptr))))))


(defn solve-1
  "Find the value of `:acc` when we're hitting an infinite loop."
  [prg]
  (let [[f {:keys [acc]}] (run-prg prg)]
    (when (= f :error)
      acc)))

(defn solve-2
  "Create mutations of the prg and find the first one that completes!"
  [prg]
  (let [;; Find all the positions where there's either a :nop or a :jmp
        mutation-ptrs (filter (fn [p] (#{:nop :jmp} (first (get (:ops prg) p))))
                              (range 0 (count (:ops prg))))
        ;; Create mutated programs at the points
        mutations (map (fn [p]
                         (let [[op arg] (get (:ops prg) p)
                               new-op (case op
                                        :jmp :nop
                                        :nop :jmp)]
                           (assoc-in prg [:ops p] [new-op arg])))
                       mutation-ptrs)]

    (loop [mutations mutations]
      (let [[f {:keys [acc]}] (run-prg (first mutations))]
        (case f
          :success acc
          :error (recur (next mutations)))))))
