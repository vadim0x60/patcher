(ns patcher.core)

(defn remove-key [coll key]
  "A generalization of dissoc that works for maps, sets (by value) and seqs (by index)"
  (cond
    (map? coll) (dissoc coll key)
    (set? coll) (disj coll key)
    (seqable? coll) (concat (take key coll) (drop (inc key) coll))))

(defn edit [coll key f]
  "Like update, but it supports seqs and removal (if f returns nil)"
  (if-let [new-val (f (get coll key))]
    (assoc coll key new-val)
    (remove-key coll key)))

(defn edit-in [coll path f]
  "Like update-in, but it supports seqs and removal (if f returns nil)"
  (let [loc (butlast path)
        name (last path)
        edit-f #(edit % name f)]
    (if (not-empty loc)
      (update-in coll loc edit-f)
      (edit-f coll))))

(defn patch-fn [type value]
  (case type
    :put (fn [old-value] value)
    ; Merge will add value to a sequence or merge it into a map
    :post (fn [old-value] (merge old-value value))
    :delete (fn [old-value] nil)))

(defn apply-patch [patch old-value]
  (edit-in old-value (:path patch) (patch-fn (:type patch) (:value patch))))

(apply-patch {:type :post :path [:interests] :value :music} {:age 35 :sex :male :interests [:cooking]})