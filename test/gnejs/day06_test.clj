(ns gnejs.day06-test
  (:require [gnejs.day06 :as sut]
            [clojure.test :as t]))

(t/deftest validate
  (t/testing "Part 1 - form sum"
    (t/is (= 11 (sut/solve-1 (sut/groups "day06_test.txt"))))
    (t/is (= 6430 (sut/solve-1 (sut/groups "day06.txt"))))))
