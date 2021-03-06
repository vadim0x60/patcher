(ns patcher.core)

(defn remove-key [coll key]
  "A generalization of dissoc that works for maps, sets (by value) and seqs (by index)"
  (cond
    (map? coll) (dissoc coll key)
    (set? coll) (disj coll key)
    (seqable? coll) (concat (take key coll) (drop (inc key) coll))))

(defn merge-anything [coll1 coll2]
  "Merges maps and sequences alike"
  (cond
    (every? map? [coll1 coll2]) (merge coll1 coll2)
    (every? seqable? [coll1 coll2]) (concat coll1 coll2)))
  
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
    (cond 
      (nil? name) (f coll)
      (empty? loc) (edit-f coll)
      :else (update-in coll loc edit-f))))

(defn patch-fn [type value]
  (case type
    :put (constantly value)
    :post #(conj % value)
    :merge (partial merge-anything value)
    :delete (constantly nil)))

(defn apply-patch [patch old-value]
  (edit-in old-value (:path patch) (patch-fn (:type patch) (:value patch))))