package com.gameplus.indexer.indexer;

import com.gameplus.indexer.model.*;
import com.gameplus.indexer.utils.SigUtil;

import java.util.Objects;

public class Quote {

    public static void setPrice(InscriptionGRC20Data data) {

        GRC20 grc20 = GRC20.parse(data);
        if (Objects.isNull(grc20)) return;

        String symbol = grc20.getSymbol();

        //check collection exist
        if (!GRC20Indexer.checkGRC20CollectionExist(symbol)) {
            return;
        }

        GRC20NFT grc20NFT = GRC20NFT.getInstance(data);
        String tokenId = grc20NFT.getTokenId();
        long price = grc20NFT.getPrice();
        long nonce = grc20NFT.getNonce();
        String sig = grc20NFT.getSig();

        //verify meta info
        GRC20Collection collection = GRC20Indexer.getCollection(symbol);
        if (collection.needVerifySig()) {
            String verifyMsg = price + tokenId + nonce;
            boolean metaVerifySuccess = SigUtil.verifySig(collection.getSigner(), verifyMsg, sig);
            if (!metaVerifySuccess) {
                GRC20Indexer.addInvalidHistory(symbol, data);
                return;
            }
        }

        //update price
        GRC20Indexer.putGRC20NFTPrice(tokenId, price);

        //add valid history
        GRC20Indexer.addValidHistory(symbol, data);

    }


}
