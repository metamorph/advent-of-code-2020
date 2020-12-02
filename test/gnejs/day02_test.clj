(ns gnejs.day02-test
  (:require [gnejs.day02 :as sut]
            [clojure.test :as t]))

(t/deftest verify
  (t/testing "Password policy 1"
    (t/is (= 2 (sut/solve-1 (sut/input->rules ["1-3 a: abcde"
                                               "1-3 b: cdefg"
                                               "2-9 c: ccccccccc"]))))
    (t/is (= 572 (sut/solve-1 (sut/input->rules (sut/input->lines))))))

  (t/testing "Password policy 2"
    (t/is (= 1 (sut/solve-2 (sut/input->rules ["1-3 a: abcde"
                                               "1-3 b: cdefg"
                                               "2-9 c: ccccccccc"]))))
    (t/is (= 306 (sut/solve-2 (sut/input->rules (sut/input->lines)))))))
