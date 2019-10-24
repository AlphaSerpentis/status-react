(ns status-im.i18n-resources
  (:require [status-im.react-native.js-dependencies :as rn-dependencies]))

(def default-device-language
  (keyword (.-language rn-dependencies/react-native-languages)))

(def languages [:af :ar :bel :cs :da :de :de_ch :el :en :es :es_419 :es_ar :es_mx :fa :fi :fr :fr_ch :fy :he :hi :hu :id :it_ch :ja :ko :la :lt :lv :ms :nb :ne :nl :pl :pt_br :pt_pt :ro :ru :sl :sr_rs_cyrl :sr_rs:latn :sv :sw :th :tr :uk :ur :vi :zh_Hans_CN :zh_hans :zh_hant :zh_hant_hk :zh_hang_sg :zh_hant_tw :zh_wuu :zh_yue])

(defonce loaded-languages
  (atom
   (conj #{:en} default-device-language)))

(def prod-translations
  {:af         (js/require "translations/af.json")
   :ar         (js/require "translations/ar.json")
   :bel        (js/require "translations/bel.json")
   :cs         (js/require "translations/cs.json")
   :da         (js/require "translations/da.json")
   :de         (js/require "translations/de.json")
   :de_ch      (js/require "translations/de_ch.json")
   :el         (js/require "translations/el.json")
   :en         (js/require "translations/en.json")
   :es         (js/require "translations/es.json")
   :es_419     (js/require "translations/es_419.json")
   :es_ar      (js/require "translations/es_ar.json")
   :es_mx      (js/require "translations/es_mx.json")
   :fa         (js/require "translations/fa.json")
   :fi         (js/require "translations/fi.json")
   :fr         (js/require "translations/fr.json")
   :fr_ch      (js/require "translations/fr_ch.json")
   :fy         (js/require "translations/fy.json")
   :he         (js/require "translations/he.json")
   :hi         (js/require "translations/hi.json")
   :hu         (js/require "translations/hu.json")
   :id         (js/require "translations/id.json")
   :it         (js/require "translations/it.json")
   :it_ch      (js/require "translations/it_ch.json")
   :ja         (js/require "translations/ja.json")
   :ko         (js/require "translations/ko.json")
   :la         (js/require "translations/la.json")
   :lt         (js/require "translations/lt.json")
   :lv         (js/require "translations/lv.json")
   :ms         (js/require "translations/ms.json")
   :nb         (js/require "translations/nb.json")
   :ne         (js/require "translations/ne.json")
   :nl         (js/require "translations/nl.json")
   :pl         (js/require "translations/pl.json")
   :pt_br      (js/require "translations/pt_br.json")
   :pt_pt      (js/require "translations/pt_pt.json")
   :ro         (js/require "translations/ro.json")
   :ru         (js/require "translations/ru.json")
   :sl         (js/require "translations/sl.json")
   :sr_rs_cyrl (js/require "translations/sr_rs_cyrl.json")
   :sr_rs_latn (js/require "translations/sr_rs_latn.json")
   :sv         (js/require "translations/sv.json")
   :sw         (js/require "translations/sw.json")
   :th         (js/require "translations/th.json")
   :tr         (js/require "translations/tr.json")
   :uk         (js/require "translations/uk.json")
   :ur         (js/require "translations/ur.json")
   :vi         (js/require "translations/vi.json")
   :zh_Hans_CN (js/require "translations/zh_Hans_CN.json")
   :zh_hans    (js/require "translations/zh_hans.json")
   :zh_hant    (js/require "translations/zh_hant.json")
   :zh_hant_hk (js/require "translations/zh_hant_hk.json")
   :zh_hant_sg (js/require "translations/zh_hant_sg.json")
   :zh_hant_tw (js/require "translations/zh_hant_tw.json")
   :zh_wuu     (js/require "translations/zh_wuu.json")
   :zh_yue     (js/require "translations/zh_yue.json")})

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
