(ns spelling-quiz.cfg
  (:require [clojure.java.io :as io]))

(defn get-lines [fname]
  (with-open [r (io/reader fname)]
    (doall (line-seq r))))

(defn load-words []
  (->
    "spelling-words.txt"
    io/resource
    get-lines))

