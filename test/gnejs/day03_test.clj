(ns gnejs.day03-test
  (:require [gnejs.day03 :as sut]
            [clojure.test :as t]))

(t/deftest verify

  (t/testing "Sleigh path - Part 1"
    (t/is (= 7 (sut/solve-1 (sut/slope-map "day03_test.txt"))))
    (t/is (= 203 (sut/solve-1 (sut/slope-map "day03.txt")))))

  (t/testing "Sleigh path - Part 2"
    (t/is (= 336 (sut/solve-2 (sut/slope-map "day03_test.txt"))))
    (t/is (= 3316272960 (sut/solve-2 (sut/slope-map "day03.txt"))))))
