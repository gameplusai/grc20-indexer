package com.gameplus.indexer.indexer;

import com.gameplus.indexer.model.*;
import com.gameplus.indexer.utils.MetaUtil;
import com.gameplus.indexer.utils.SigUtil;

import java.util.Objects;

public class Mint {

    public static void mint(InscriptionGRC20Data data) {

        GRC20 grc20 = GRC20.parse(data);
        if (Objects.isNull(grc20)) return;

        String symbol = grc20.getSymbol();

        //check collection exist
        if (!GRC20Indexer.checkGRC20CollectionExist(symbol)) return;

        //check if max supply
        long maxSupply = GRC20Indexer.getGRC20CollectionMaxSupply(symbol);
        long count = GRC20Indexer.getGRC20CollectionCount(symbol);
        if (maxSupply <= count) {
            GRC20Indexer.addInvalidHistory(symbol, data);
            return;
        }

        GRC20NFT grc20NFT = GRC20NFT.getInstance(data);

        if (!grc20NFT.checkData()) return;

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
        String verifyMsg = metaHash + tokenId + nonce;
        boolean metaVerifySuccess = SigUtil.verifySig(collection.getSigner(), verifyMsg, sig);
        if (!metaVerifySuccess) {
            GRC20Indexer.addInvalidHistory(symbol, data);
            return;
        }

        //add user nft
        GRC20Indexer.addUserGRC20NFT(grc20NFT);

        //add collection nft
        GRC20Indexer.addGRC20NFT(grc20NFT);

        //add valid history
        GRC20Indexer.addValidHistory(symbol, data);

    }

}
