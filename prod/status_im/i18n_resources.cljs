(ns status-im.i18n-resources
  (:require [status-im.react-native.js-dependencies :as rn-dependencies]))

(def default-device-language
  (keyword (.-language rn-dependencies/react-native-languages)))

(def languages [:af :ar :bel :cs :da :de :de_ch :el :en :es :es_419 :es_ar :es_mx :fa :fi :fr :fr_ch :fy :he :hi :hu :id :it_ch :ja :ko :la :lt :lv :ms :nb :ne :nl :pl :pt_br :pt_pt :ro :ru :sl :sr_rs_cyrl :sr_rs:latn :sv :sw :th :tr :uk :ur :vi :zh_Hans_CN :zh_hans :zh_hant :zh_hant_hk :zh_hang_sg :zh_hant_tw :zh_wuu :zh_yue])

(defonce loaded-languages
  (atom
   (conj #{:en} default-device-language)))

(def prod-translations
  {:en         (js/require "status-modules/translations/en.json")
   :es_419     (js/require "status-modules/translations/es_419.json")
   :fa         (js/require "status-modules/translations/fa.json")
   :fr         (js/require "status-modules/translations/fr.json")
   :ko         (js/require "status-modules/translations/ko.json")
   :ms         (js/require "status-modules/translations/ms.json")
   :pl         (js/require "status-modules/translations/pl.json")
   :ru         (js/require "status-modules/translations/ru.json")
   :zh_Hans_CN (js/require "status-modules/translations/zh_Hans_CN.json")})

(defn valid-language [lang]
  (if (contains? prod-translations lang)
    lang
    (let [short-lang (keyword (subs (name lang) 0 2))]
      (when (contains? prod-translations short-lang)
        short-lang))))

(defn require-translation [lang-key]
  (when-let [lang (valid-language lang-key)]
    (get prod-translations lang)))

;; translations
(def translations-by-locale
  (cond->
    {:en (require-translation :en)}
    (not= :en default-device-language)
    (assoc default-device-language
           (require-translation default-device-language))))

(defn load-language [lang]
  (let [lang-key (valid-language (keyword lang))]
    (when-not (contains? @loaded-languages lang-key)
      (aset (.-translations rn-dependencies/i18n)
            lang
            (require-translation lang-key))
      (swap! loaded-languages conj lang-key))))
