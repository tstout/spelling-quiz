(ns user)


(defn load-vars []
  (prn "loading repl customizations...")
  (require '[spelling-quiz.cfg :as cfg]
           '[spelling-quiz.core :as core]))

(load-vars)