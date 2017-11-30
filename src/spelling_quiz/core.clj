(ns spelling-quiz.core
  (:require [spelling-quiz.cfg :as cfg]
            [clojure.java.shell :refer [sh]])
  (:gen-class))

;; Try this option
;; espeak -ven+f3 -k5 -s150

(def tts ["espeak" "-ven+f3" "-k5" "-s150"])
;;(def tts ["say" "-vFred"])

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
  (apply sh (concat tts [(format "\"The next word is, %s\"" word)])))

(defn verify-word [w answer]
  (if (= w answer)
    (do
      (println "Correct!")
      (apply sh (concat tts ["correct"])))
    (println (str "Sorry, correct answer is " w))))

(defn quiz [cfg]
  (let [{:keys [os words]} (cfg/mk-cfg)]
    (doseq [w words]
      (speak w)
      (verify-word w (input-word)))))


(defn -main
  "Start the quiz"
  [& args]
  (println "For Spelling Bee Glory!")
  (quiz (cfg/mk-cfg))
  (println "You have gone through this list. Take a break!"))
