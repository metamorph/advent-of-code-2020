(ns gnejs.day05-test
  (:require [gnejs.day05 :as sut]
            [clojure.test :as t]))

(t/deftest verify
  (t/testing "SeatID"
    (t/is (= 567 (sut/seat-id "BFFFBBFRRR")))
    (t/is (= 119 (sut/seat-id "FFFBBBFRRR")))
    (t/is (= 820 (sut/seat-id "BBFFBBFRLL"))))
  (t/testing "Max Seat ID"
    (t/is (= 822 (sut/solve-1)))))
