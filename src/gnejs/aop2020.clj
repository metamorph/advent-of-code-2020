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
