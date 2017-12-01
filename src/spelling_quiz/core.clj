(ns spelling-quiz.core
  (:require [spelling-quiz.cfg :as cfg]
            [clojure.java.shell :refer [sh]])
  (:gen-class))

(defn input-word []
  (println "Your answer:")
  (read-line))

(defn next-word [words]
  (->>
    (rand-int (dec (count words)))
    (nth words)))

(defn speak [tts word]
  (apply sh (concat tts [(format "\"The next word is, %s\"" word)])))

(defn verify-word [w answer tts]
  (if (= w answer)
    (do
      (println "Correct!")
      (apply sh (concat tts ["correct"])))
    (println (str "Sorry, correct answer is " w))))

(defn quiz [cfg]
  (let [{:keys [tts words]} (cfg/mk-cfg)]
    (doseq [w words]
      (speak tts w)
      (verify-word w (input-word) tts))))

(defn -main
  "Start the quiz"
  [& args]
  (println "For Spelling Bee Glory!")
  (quiz (cfg/mk-cfg))
  (println "You have gone through this list. Take a break!"))
