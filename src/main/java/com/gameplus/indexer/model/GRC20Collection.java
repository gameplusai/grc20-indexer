package com.gameplus.indexer.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class GRC20Collection {

    String name;
    String symbol;
    String baseTokenUri;
    String signer;
    String address;
    int maxSupply;

    public static GRC20Collection getInstance(GRC20 grc20) {
        GRC20Collection grc20Collection = new GRC20Collection();
        grc20Collection.setName(grc20.getName());
        grc20Collection.setSymbol(grc20.getSymbol());
        grc20Collection.setBaseTokenUri(grc20.getBaseTokenUri());
        grc20Collection.setSigner(grc20.getSigner());
        grc20Collection.setMaxSupply(grc20.getMaxSupply());
        return grc20Collection;
    }

    public boolean checkData() {
        return StringUtils.hasLength(name) && StringUtils.hasLength(symbol) && StringUtils.hasLength(baseTokenUri)
                && StringUtils.hasLength(signer) && maxSupply > 0;
    }

    public static GRC20Collection parse(InscriptionGRC20Data data) {
        if (!StringUtils.hasLength(data.getInscriptionData())) return null;
        GRC20Collection collection = JSON.parseObject(data.getInscriptionData(), GRC20Collection.class);
        collection.setAddress(data.getFrom());
        return collection;
    }

    public boolean needVerifySig() {
        return StringUtils.hasLength(signer);
    }

    public String getGRC20TokenUri(String tokenId) {
        return baseTokenUri + tokenId;
    }


}
