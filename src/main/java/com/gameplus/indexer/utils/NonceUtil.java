package com.gameplus.indexer.utils;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class NonceUtil {

    private static final ConcurrentHashMap<String, AtomicInteger> nonceMap = new ConcurrentHashMap<>();

    public static int get(String symbol) {
        AtomicInteger atomicInteger = nonceMap.get(symbol);
        if (Objects.isNull(atomicInteger)) {
            atomicInteger = new AtomicInteger();
            nonceMap.put(symbol, atomicInteger);
        }
        return atomicInteger.getAndIncrement();
    }

    public static void main(String[] args) {
        System.out.println(get("GPB"));
        System.out.println(get("GPB"));
        System.out.println(get("GPB"));
        System.out.println(get("GPB"));
    }

}
