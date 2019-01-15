(ns patcher.core-test
  (:require [clojure.test :refer :all]
            [patcher.core :refer :all]))

(deftest post-test
  (testing "POST"
    (is {:age 35 :sex :male :interests [:cooking :music]}
        (apply-patch {:type :post :path [:interests] :value :music} 
                     {:age 35 :sex :male :interests [:cooking]}))))