(ns gnejs.day01-test
  (:require [gnejs.day01 :as sut]
            [clojure.test :as t]))


(t/deftest verify
  (t/is (= 157059 (sut/solve-1 (sut/input))))
  (t/is (= 165080960 (sut/solve-2 (sut/input)))))
