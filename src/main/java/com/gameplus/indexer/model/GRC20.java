package com.gameplus.indexer.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.gameplus.indexer.constant.Constant;
import com.gameplus.indexer.utils.Utils;
import lombok.Data;

@Data
public class GRC20 {

    @JSONField(ordinal = 1)
    String p;

    @JSONField(ordinal = 2)
    String name;

    @JSONField(ordinal = 3)
    String symbol;

    @JSONField(ordinal = 4)
    String op;

    @JSONField(ordinal = 5)
    String tokenId;

    @JSONField(ordinal = 6)
    int maxSupply;

    @JSONField(ordinal = 7)
    String baseTokenUri;

    @JSONField(ordinal = 8)
    String metaHash;

    @JSONField(ordinal = 9)
    String signer;

    @JSONField(ordinal = 10)
    String sig;

    @JSONField(ordinal = 11)
    long nonce;


    public static GRC20 parse(InscriptionGRC20Data data) {
        return JSON.parseObject(data.getInscriptionData(), GRC20.class);
    }

    public static GRC20 getInstance(String op) {
        GRC20 grc20 = new GRC20();
        grc20.setP(Constant.GRC20);
        grc20.setOp(op);
        return grc20;
    }

    public static GRC20 getFromInscriptions(String json) {
        return JSON.parseObject(json, GRC20.class);
    }

    public static String getMintSigMessage(String metaHash, String tokenId, long nonce) {
        return metaHash + tokenId + nonce;
    }

    public static GRC20 getDeploy(String name, String symbol, String tokenUri, int max, String signerPubkey) {
        GRC20 grc20 = getInstance(Constant.DEPLOY);
        grc20.setName(name);
        grc20.setSymbol(symbol);
        grc20.setBaseTokenUri(tokenUri);
        grc20.setMaxSupply(max);
        grc20.setSigner(signerPubkey);
        return grc20;
    }

    public static GRC20 getMint(String symbol, String tokenId, String sig, long nonce) {
        GRC20 grc20 = getInstance(Constant.MINT);
        grc20.setSymbol(symbol);
        grc20.setTokenId(tokenId);
        grc20.setSig(sig);
        grc20.setNonce(nonce);
        return grc20;
    }

    public static GRC20 getUpgrade(String symbol, String tokenId, String metaHash, String sig) {
        GRC20 grc20 = getInstance(Constant.UPGRADE);
        grc20.setSymbol(symbol);
        grc20.setMetaHash(metaHash);
        grc20.setTokenId(tokenId);
        grc20.setSig(sig);
        return grc20;
    }

    public String toJson() {
        if (!getOp().equals(Constant.DEPLOY)) {
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
            filter.getExcludes().add(Constant.FIELD_MAX);
            return JSON.toJSONString(this, filter);
        }
        return JSON.toJSONString(this);
    }

    public String toHex() {
        return Utils.toHex(toJson());
    }


}
