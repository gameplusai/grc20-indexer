package com.gameplus.indexer.utils;

import com.gameplus.indexer.constant.Constant;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TokenIdUtil {

    private static final ConcurrentHashMap<String, Set<String>> nonceMap = new ConcurrentHashMap<>();

    public static String get(String symbol) {
        Set<String> tokens = nonceMap.get(symbol);
        if (Objects.isNull(tokens)) {
            tokens = new HashSet<>();
            nonceMap.put(symbol, tokens);
        }
        String tokenId = Utils.getRandomString(Constant.TOKEN_LENGTH);
        while (tokens.contains(tokenId)) tokenId = Utils.getRandomString(Constant.TOKEN_LENGTH);
        tokens.add(tokenId);
        return tokenId;
    }


}
