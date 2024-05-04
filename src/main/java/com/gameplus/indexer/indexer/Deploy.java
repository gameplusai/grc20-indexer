package com.gameplus.indexer.indexer;

import com.gameplus.indexer.constant.Constant;
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
        if (GRC20Indexer.checkGRC20CollectionExist(symbol)) {
            //dup symbol
            return;
        }

        //check data
        if (!grc20Collection.checkData()) return;

        //get inscription
        Inscription inscription = Inscription.getInstance(data);

        //add history
        GRC20History history = GRC20History.getInstance(Constant.DEPLOY, true, data, inscription);
        GRC20Indexer.addGRC20History(symbol, history);

    }


}
