(ns gnejs.day07
  (:require [gnejs.aop2020 :refer [read-lines]]))

(def BAG-PATTERN #"(\w+\s\w+)\sbag[s*\s] contain(.*)")
(def CONTENT-PATTERN #"(\d+) (\w+\s\w+)")

(defn line->rule [line]
  (let [[_ color content] (re-find BAG-PATTERN line)
        contents          (map (fn [[_ n color]] {:color color
                                                  :count (Integer/parseInt n)})
                               (re-seq CONTENT-PATTERN content))]
    {:color color
     :contains contents}))

(defn read-rules [name] (map line->rule (read-lines name)))

(defn parent-bags [lookup start-color]
  (if-let [contained-in (get lookup start-color)]
    (concat contained-in (mapcat (partial parent-bags lookup) contained-in))
    (list)))

(defn solve-1 [rules]
  (let [lookup (reduce (fn [m {:keys [color contains]}]
                         (reduce (fn [m' c]
                                   (update m' c conj color))
                                 m (map :color contains)))
                       {} rules)]
    (count (set (parent-bags lookup "shiny gold")))))

(defn count-child-bags [lookup color]
  (if-let [children (seq (get lookup color))]
    (+ (apply + (map second children))
       (apply + (map (fn [[c n]] (* n (count-child-bags lookup c))) children)))
    0))

(defn solve-2 [rules]
  (let [lookup (reduce (fn [m {:keys [color contains]}]
                         (assoc m color (map (fn [{:keys [color count]}] [color count]) contains)))
                       {} rules)]
    (count-child-bags lookup "shiny gold")))
