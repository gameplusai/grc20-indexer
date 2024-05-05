package com.gameplus.indexer.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Data
public class GRC20NFT {

    String owner;
    String symbol;
    String tokenId;
    String metaHash;

    String sig;
    long nonce;
    long price;

    //meta info
    String tokeUri;
    String name;
    String description;
    String image;
    NftMeta meta;

    public boolean checkData() {
        return StringUtils.hasLength(symbol) && StringUtils.hasLength(tokenId)
                && StringUtils.hasLength(sig) && nonce > 0;
    }

    public static GRC20NFT getInstance(InscriptionGRC20Data data) {
        GRC20NFT nft = JSON.parseObject(data.getInscriptionData(), GRC20NFT.class);
        nft.setOwner(data.getTo());
        return nft;
    }

    public void updateMetaInfo() {
        if (Objects.isNull(meta)) return;
        setName(meta.getName());
        setDescription(meta.getDescription());
        setImage(meta.getImage());
    }


}
