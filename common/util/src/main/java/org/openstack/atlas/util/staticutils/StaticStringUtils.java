package org.openstack.atlas.util.staticutils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class StaticStringUtils {

    public static String lpadLong(long val, String pad, int npad) {
        return lpad(Long.toString(val), pad, npad);
    }

    public static String lpad(String val, String pad, int npad) {
        StringBuilder sb = new StringBuilder();
        int nspaces = npad - val.length();
        for (int i = 0; i < nspaces; i++) {
            sb.append(pad);
        }
        sb.append(val);
        return sb.toString();
    }

    public static <T> String collectionToString(Collection<T> collection, String delimiter) {
        if (collection == null) {
            return "null";
        }
        if (collection.isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        List<T> list = new ArrayList<T>(collection);
        for (int i = 0; i < list.size() - 1; i++) {
            T entry = list.get(i);
            if (entry == null) {
                sb.append("null").append(delimiter);
                continue;
            }
            sb.append(entry.toString()).append(delimiter);
        }
        sb.append(list.get(list.size() - 1)).append("]");
        return sb.toString();
    }

    public static String truncate(String stringIn, int maxLen) {
        if (stringIn == null) {
            return stringIn;
        }
        return stringIn.substring(0, Math.min(maxLen, stringIn.length() - 1));
    }

    public static <K, V> String mapToString(Map<K, V> mapIn) {
        if (mapIn == null) {
            return "null";
        }
        if (mapIn.size() <= 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Entry<K, V> entry : mapIn.entrySet()) {
            K key = entry.getKey();
            V val = entry.getValue();
            sb.append("(");
            if (key == null) {
                sb.append("null:");
            } else {
                sb.append(key.toString()).append(":");
            }

            if (val == null) {
                sb.append("null),");
            } else {
                sb.append(val.toString()).append("),");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
