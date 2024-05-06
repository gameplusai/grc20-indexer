package com.gameplus.indexer.indexer;

import com.gameplus.indexer.model.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class Deploy {

    public static void deploy(InscriptionGRC20Data data) {

        //get collection and inscription
        GRC20Collection grc20Collection = GRC20Collection.parse(data);
        if (Objects.isNull(grc20Collection)) return;

        String symbol = grc20Collection.getSymbol();

        //check exist
        //dup symbol
        if (GRC20Indexer.checkGRC20CollectionExist(symbol)) return;

        //check data
        if (!grc20Collection.checkData()) return;

        //add collection
        GRC20Indexer.addCollection(grc20Collection);

        //add history
        GRC20Indexer.addValidHistory(symbol, data);

    }


}
