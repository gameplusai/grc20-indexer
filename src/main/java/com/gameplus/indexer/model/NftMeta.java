package com.gameplus.indexer.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gameplus.indexer.utils.Utils;
import lombok.Data;

import java.util.*;

@Data
public class NftMeta {

//    https://docs.opensea.io/docs/metadata-standards#metadata-structure
//    {
//           "description": "Friendly OpenSea Creature that enjoys long swims in the ocean.",
//            "external_url": "https://openseacreatures.io/3",
//            "image": "https://storage.googleapis.com/opensea-prod.appspot.com/puffs/3.png",
//            "name": "Dave Starbelly",
//            "attributes": [ ... ]
//    }

    @JSONField(ordinal = 1)
    String name;

    @JSONField(ordinal = 2)
    String description;

    @JSONField(ordinal = 3)
    String image;

    @JSONField(ordinal = 4)
    List<MetaAttribute> attributes;

    @JsonIgnore
    Map<String, MetaAttribute> traitTypes = new HashMap<>();

    public void addAttribute(MetaAttribute attribute) {
        if (Objects.isNull(attributes)) attributes = new ArrayList<>();
        traitTypes.put(attribute.getTraitType(), attribute);
        List<String> traits = new ArrayList<>(traitTypes.keySet());
        Collections.sort(traits);
        attributes.clear();
        for (String trait : traits) {
            attributes.add(traitTypes.get(trait));
        }
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    @JsonIgnore
    public String getMetaHash() {
        return Utils.toHash256(toJson());
    }

    public String toJsonFormat() {
        return JSON.toJSONString(this, true);
    }

}
