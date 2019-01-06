(ns table.core
  (:gen-class)
  (:require [seesaw.core :as s]
            [seesaw.chooser :as c]
            [clojure-csv.core :as csv]))

(defn verify
  "TODO: add some validation code"
  [data]
  true)

(defn ui
  "start up ui as to not clutter -main"
  [data]
  (if (verify data)
    (s/invoke-later 
      (-> (s/frame :title "Table"

                   :content
                    (s/table
                      :model
                      [ :columns
                          (into [] (repeat (count (first data)) ""))
                        :rows
                          data ]
                      :selection-mode :single
                      :font { :size 18 }
                      :border 15)
                   :on-close :exit
                   :size [720 :by 480])
      s/pack!
      s/show!))))

(defn -main
  "i do nothing"
  [& args]
  (do
    (s/native!)
    (if (> (count args) 0)
      (ui
        (csv/parse-csv
          (slurp
            (first args))))
      ;; else open file dialog
      (ui
        (csv/parse-csv
          (slurp
            (c/choose-file :success-fn (fn [fc file] (.getAbsolutePath file)))))))))
