(ns gnejs.day07-test
  (:require [gnejs.day07 :as sut]
            [clojure.test :as t]))

(t/deftest validate
  (t/testing "Day7 - Bags in bags"
    (t/is (= 4 (sut/solve-1 (sut/read-rules "day07_test.txt"))))))
