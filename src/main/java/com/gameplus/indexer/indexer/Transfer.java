package com.gameplus.indexer.indexer;

import com.alibaba.fastjson.JSON;
import com.gameplus.indexer.constant.Constant;
import com.gameplus.indexer.model.*;
import com.gameplus.indexer.utils.HttpUtil;
import com.gameplus.indexer.utils.SigUtil;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class Transfer {

    public static void transfer(InscriptionGRC20Data data) {

        GRC20 grc20 = GRC20.parse(data);
        if (Objects.isNull(grc20)) return;

        String symbol = grc20.getSymbol();

        //check collection exist
        if (!GRC20Indexer.checkGRC20CollectionExist(symbol)) {
            return;
        }

        GRC20NFT grc20NFT = GRC20NFT.getInstance(data);
        String tokenId = grc20NFT.getTokenId();
        long nonce = grc20NFT.getNonce();
        String sig = grc20NFT.getSig();

        //get meta info
        GRC20Collection collection = GRC20Indexer.getCollection(symbol);
        String tokenUri = collection.getGRC20TokenUri(tokenId);
        grc20NFT.setTokeUri(tokenUri);

        NftMeta meta = getMeta(tokenUri);
        if (Objects.isNull(meta)) {
            addInvalidHistory(symbol, data);
            return;
        }

        //update meta info
        grc20NFT.setMeta(meta);
        grc20NFT.updateMetaInfo();

        //verify meta info
        String metaHash = meta.getMetaHash();
        String verifyMsg = metaHash + tokenId + nonce;
        boolean metaVerifySuccess = SigUtil.verifySig(collection.getSigner(), verifyMsg, sig);
        if (!metaVerifySuccess) {
            addInvalidHistory(symbol, data);
            return;
        }

        //remove user nft

        GRC20Indexer.removeUserGRC20NFT(data.getFrom(), grc20NFT);
        GRC20Indexer.addUserGRC20NFT(grc20NFT);

        //remove collection last nft
        GRC20Indexer.removeGRC20NFT(grc20NFT);
        // add collection new nft
        GRC20Indexer.addGRC20NFT(grc20NFT);

        //add valid history
        addValidHistory(symbol, data);

    }


    private static void addInvalidHistory(String symbol, InscriptionGRC20Data data) {
        Inscription inscription = Inscription.getInstance(data);
        GRC20History history = GRC20History.getInstance(Constant.TRANSFER, false, data, inscription);
        GRC20Indexer.addGRC20History(symbol, history);
    }

    private static void addValidHistory(String symbol, InscriptionGRC20Data data) {
        Inscription inscription = Inscription.getInstance(data);
        GRC20History history = GRC20History.getInstance(Constant.TRANSFER, true, data, inscription);
        GRC20Indexer.addGRC20History(symbol, history);
    }

    private static NftMeta getMeta(String tokenUri) {
        String metaData = HttpUtil.get(tokenUri);
        if (StringUtils.hasLength(metaData)) {
            return JSON.parseObject(metaData, NftMeta.class);
        }
        return null;
    }


}
