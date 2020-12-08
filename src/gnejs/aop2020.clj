(ns gnejs.aop2020
  "Utility functions!"
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn read-lines
  "Read content from `resource/` and return lines."
  [name]
  (-> (io/resource name)
      (slurp)
      (str/split-lines)))

(defn read-and-parse
  "Feed each line to `line-fn` and return a seq from the result.
   If `line-fn` returns `nil` the line is excluded."
  [resource-name line-fn]
  (mapcat (fn [l]
            (if-let [parsed (line-fn l)]
              (list parsed)
              (list)))
          (read-lines resource-name)))
