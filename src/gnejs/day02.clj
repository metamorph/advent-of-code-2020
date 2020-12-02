(ns gnejs.day02
  (:require [clojure.java.io :as io]))

(defn input->lines "Read test-data"
  []
  (-> (io/resource "day02.txt")
      (io/reader)
      (line-seq)))

(def PATTERN "Pattern to parse a line"
  #"(\d+)-(\d+)\s+([a-z])\:\s+(.+)$")

(defn line->rule
  "Turn a rule into data."
  [line]
  (if-let [[_ min max char password] (re-find PATTERN line)]
    {:min      (Integer/parseInt min)
     :max      (Integer/parseInt max)
     :char     (first char)
     :password password}

    ;; If not matching pattern!
    (throw (ex-info "Pattern no match!" {:pattern line}))))

(defn input->rules
  "Parse day02.txt as seq of:
   `{:min <int>
    :max <int>
    :char <char>
    :password <password>}`"
  [lines] (map line->rule lines))

(defn matches?
  "Check if rule matches according to policy 1"
  [{:keys [min max char password]}]
  (let [char-count (count (filter #(= char %) password))]
    (<= min char-count max)))

(defn matches-pos?
  "Check if rule matches according to policy 2"
  [{:keys [min max char password]}]
  (let [chars (vec password)
        p1 (get chars (dec min))
        p2 (get chars (dec max))]
    (and (or (= p1 char)
             (= p2 char))
         (not= p1 p2))))

;; --- Solve ----------

(defn solve-1 [rules] (count (filter matches? rules)))

(defn solve-2 [rules] (count (filter matches-pos? rules)))
