package com.gameplus.indexer.utils;

import com.alibaba.fastjson.JSON;
import com.gameplus.indexer.model.NftMeta;
import org.springframework.util.StringUtils;

public class MetaUtil {

    public static NftMeta getMeta(String tokenUri) {
        String metaData = HttpUtil.get(tokenUri);
        if (StringUtils.hasLength(metaData)) {
            return JSON.parseObject(metaData, NftMeta.class);
        }
        return null;
    }

}
