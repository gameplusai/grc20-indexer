package com.gameplus.indexer.indexer;

import com.gameplus.indexer.constant.Constant;
import com.gameplus.indexer.model.InscriptionGRC20Data;

public class Init {

    public static void addIndex(String type, InscriptionGRC20Data data) {
        switch (type) {
            case Constant.DEPLOY:
                Deploy.deploy(data);
                break;
            case Constant.MINT:
                Mint.mint(data);
                break;
            case Constant.TRANSFER:
                Transfer.transfer(data);
                break;
            case Constant.UPGRADE:
                Upgrade.upgrade(data);
                break;
        }
    }


}
