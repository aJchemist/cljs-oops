(ns oops.arena.exercise-ocall
  (:require-macros [oops.arena.macros :refer [macro-identity]])
  (:require [oops.core :refer [ocall ocall+]]
            [oops.config :refer [without-diagnostics with-debug]]
            [oops.tools :refer [init-arena-test! testing]]))

(init-arena-test!)

; we want to test generated code shape expansion under dev mode

(testing "static ocall expansion"
  (ocall js/window "method" "p1" "p2"))

(testing "dynamic ocall expansion"
  (ocall+ js/window (identity "method") "p1" "p2"))

(testing "ocall expansion with macro-generated method and params should be static"
  (ocall js/window (macro-identity "method") (macro-identity "p1") "p2"))

(testing "ocall expansion with disabled diagnostics"
  (without-diagnostics
    (ocall js/window "method" "p1" "p2")
    (ocall+ js/window (identity "method") "p1" "p2")))

(testing "ocall expansion with enabled debugging"
  (with-debug
    (ocall js/window "method" "p1" "p2")
    (ocall+ js/window (identity "method") "p1" "p2")))
