package df.common.utils;

public interface KVPair<K, V> {

    K key();

    V value();

    KVPair<K, V> key(K key);

    KVPair<K, V> value(V value);

    static <K, V> KVPair<K, V> create(K key, V value) {
        return new KVPair<K, V>() {
            private K key;
            private V value;

            @Override
            public K key() {
                return key;
            }

            @Override
            public V value() {
                return value;
            }

            @Override
            public KVPair<K, V> key(K key) {
                this.key = key;
                return this;
            }

            @Override
            public KVPair<K, V> value(V value) {
                this.value = value;
                return this;
            }
        }.key(key).value(value);
    }

}
