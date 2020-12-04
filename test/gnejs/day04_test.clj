(ns gnejs.day04-test
  (:require [gnejs.day04 :as sut]
            [clojure.test :as t]))

(t/deftest verify

  (t/testing "Valid passports - Part 1"
    (t/is (= 2 (sut/solve-1 (sut/read-batch "day04_test.txt"))))
    (t/is (= 239 (sut/solve-1 (sut/read-batch "day04.txt")))))
  (t/testing "Valid passports - Part 2"
    (t/is (= 188 (sut/solve-2 (sut/read-batch "day04.txt"))))))
