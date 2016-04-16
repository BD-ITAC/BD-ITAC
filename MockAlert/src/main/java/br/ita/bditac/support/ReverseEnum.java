package br.ita.bditac.support;

import java.util.HashMap;
import java.util.Map;

public class ReverseEnum <V extends Enum<V>> {
    private Map<Integer, V> map = new HashMap<Integer, V>();
    public ReverseEnum(Class<V> valueType) {
        for (V v : valueType.getEnumConstants()) {
            map.put(v.ordinal(), v);
        }
    }

    public V get(int num) {
        return map.get(num);
    }
}