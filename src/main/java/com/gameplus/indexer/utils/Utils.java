package com.gameplus.indexer.utils;

import org.bitcoinj.core.Sha256Hash;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

public class Utils {

    private static String byte2Hex(byte[] bytes) {
        return org.bitcoinj.core.Utils.HEX.encode(bytes);
    }

    public static String toHex(String json) {
        if (!StringUtils.hasLength(json)) return "";
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        return byte2Hex(bytes);
    }

    public static String toHash256(String json) {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        byte[] hashBytes = Sha256Hash.hash(bytes);
        return byte2Hex(hashBytes);
    }

    public static String getInscriptionId(String txId, int idx) {
        return String.format("%si%d", txId, idx);
    }

    public static String getRandomString(int length) {
        String generateSource = "0123456789abcdefghigklmnopqrstuvwxyz";
        StringBuilder randomStringBuffer = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String nowStr = String.valueOf(generateSource.charAt((int) Math.floor(Math.random() * generateSource.length())));
            randomStringBuffer.append(nowStr);
            generateSource = generateSource.replaceAll(nowStr, "");
        }
        return randomStringBuffer.toString().toUpperCase();
    }


}
