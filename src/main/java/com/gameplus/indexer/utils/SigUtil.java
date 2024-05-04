package com.gameplus.indexer.utils;


import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Utils;

import java.security.SignatureException;

public class SigUtil {

    //ecdsa sign
    public static String signMessage(String privateKeyHex, String message) {
        ECKey key = ECKey.fromPrivate(Utils.HEX.decode(privateKeyHex));
        return key.signMessage(message);
    }

    //ecdsa verify
    public static boolean verifySig(String pubKeyHex, String message, String sig) {
        ECKey key = ECKey.fromPublicOnly(Utils.HEX.decode(pubKeyHex));
        try {
            key.verifyMessage(message, sig);
        } catch (SignatureException e) {
            return false;
        }
        return true;
    }


}
