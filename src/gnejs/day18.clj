(ns gnejs.day18
  (:require [clojure.edn :as edn]
            [gnejs.aop2020 :refer [read-lines]])
  (:import [java.io PushbackReader StringReader]))

(defn expr->forms [input]
  (let [reader (PushbackReader. (StringReader. input))]
    (take-while (partial not= :eof)
                (repeatedly #(edn/read {:eof :eof} reader)))))

(defn process [op a b]
  (eval '(op a b)))

(defn calc [forms]
  (loop [[v & tail] forms
         stack      (list)
         ops        (list)]
    (if v
      (cond
        (seq? v)     (recur (conj tail (calc v)) stack ops)
        (#{'+ '*} v) (recur tail stack (conj ops v))
        (number? v)  (if-let [op (first ops)]
                      (recur tail (conj (next stack) ((eval op) (first stack) v)) (next ops))
                      (recur tail (conj stack v) ops)))
      (first stack))))

(defn solve-1 [name]
  (->> (read-lines name)
       (map expr->forms)
       (map calc)
       (reduce + 0)))
