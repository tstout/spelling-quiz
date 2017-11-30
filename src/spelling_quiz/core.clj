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

;(defmulti speak (fn [os _] os))
;
;(defmethod speak :linux [word]
;  (sh "espeak" "-v" "english-us" word))
;
;(defmethod speak :osx [word]
;  (sh "say" word))

(defn speak [word]
  (sh "say" (str "The next word is " word)))

(defn verify-word [w answer]
  (if (= w answer)
    (do
      (println "Correct!")
      (sh "say" "correct"))
    (println (str "Sorry, correct answer is " w))))

(defn -main
  "Start the quiz"
  [& args]
  (println "For Spelling Bee Glory!")
  (let [{:keys [os words]} (cfg/mk-cfg)]
    (doseq [w words]
      (speak w)
      (verify-word w (input-word))))
  (println "You have gone through this list. Take a break!"))
