package com.gameplus.indexer.indexer;

import com.alibaba.fastjson.JSON;
import com.gameplus.indexer.model.*;
import com.gameplus.indexer.utils.MetaUtil;
import com.gameplus.indexer.utils.SigUtil;

import java.util.List;
import java.util.Objects;

public class Royalty {

    public static void royalty(InscriptionGRC20Data data) {

        GRC20 grc20 = GRC20.parse(data);
        if (Objects.isNull(grc20)) return;

        String symbol = grc20.getSymbol();

        //check collection exist
        if (!GRC20Indexer.checkGRC20CollectionExist(symbol)) return;

        //Check quotes
        List<String> quotes = grc20.getQuote();
        if (Objects.isNull(quotes) || quotes.isEmpty()) return;
        if (!checkQuotePrice(quotes)) return;

        GRC20NFT grc20NFT = GRC20NFT.getInstance(data);
        String tokenId = grc20NFT.getTokenId();
        long nonce = grc20NFT.getNonce();
        String sig = grc20NFT.getSig();

        //get meta info
        GRC20Collection collection = GRC20Indexer.getCollection(symbol);
        String tokenUri = collection.getGRC20TokenUri(tokenId);
        grc20NFT.setTokeUri(tokenUri);

        NftMeta meta = MetaUtil.getMeta(tokenUri);
        if (Objects.isNull(meta)) {
            GRC20Indexer.addInvalidHistory(symbol, data);
            return;
        }

        //update meta info
        grc20NFT.setMeta(meta);
        grc20NFT.updateMetaInfo();

        //verify meta info
        String metaHash = meta.getMetaHash();
        String quotesJson = JSON.toJSONString(quotes);
        String verifyMsg = metaHash + quotesJson + tokenId + nonce;
        boolean metaVerifySuccess = SigUtil.verifySig(collection.getSigner(), verifyMsg, sig);
        if (!metaVerifySuccess) {
            GRC20Indexer.addInvalidHistory(symbol, data);
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
        GRC20Indexer.addValidHistory(symbol, data);

    }

    private static boolean checkQuotePrice(List<String> quotes) {
        boolean checkResult = true;
        for (String tokenId : quotes) {
            long price = GRC20Indexer.getGRC20NFTPrice(tokenId);
            if (price == 0) {
                checkResult = false;
                break;
            }
        }
        return checkResult;
    }

}
