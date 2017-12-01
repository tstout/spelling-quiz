(ns spelling-quiz.cfg
  (:require [clojure.java.io :as io]
            [clojure.java.shell :refer [sh]]
            [clojure.string :as str]))

(defn get-lines [fname]
  (with-open [r (io/reader fname)]
    (doall (line-seq r))))

(def tts
  {:linux ["espeak" "-ven+f3" "-k5" "-s150"]
   :osx   ["say" "-vFred"]})

(defn load-words []
  (->
    "spelling-words.txt"
    io/resource
    get-lines))

(defn determine-os [uname-str]
  (cond
    (str/includes? uname-str "Darwin") :osx
    (str/includes? uname-str "Linux") :linux
    :else :unknown))

(defn os []
  (->
    (sh "uname" "-a")
    :out
    determine-os))

(defn mk-cfg []
  {:words (load-words)
   :tts    (tts (os))})
