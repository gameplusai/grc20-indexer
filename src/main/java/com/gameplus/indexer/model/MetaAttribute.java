package com.gameplus.indexer.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class MetaAttribute {

    //GamePlus dragon attributes
    public static final String displayPercentage = "percentage";
    public static final String displayNumber = "number";
    public static final String level = "level";
    public static final String hashPower = "hashPower";
    public static final String rarity = "rarity";
    public static final String rarityType = "rarityType";

    @JSONField(name = "display_type")
    String displayType;

    @JSONField(name = "trait_type")
    String traitType;

    Object value;

    public static MetaAttribute getLevelAttr(int level) {
        MetaAttribute attribute = new MetaAttribute();
        attribute.setDisplayType(displayNumber);
        attribute.setTraitType(MetaAttribute.level);
        attribute.setValue(level);
        return attribute;
    }

    public static MetaAttribute getHashPowerAttr(double hashPower) {
        MetaAttribute attribute = new MetaAttribute();
        attribute.setDisplayType(displayNumber);
        attribute.setTraitType(MetaAttribute.hashPower);
        attribute.setValue(hashPower);
        return attribute;
    }

    public static MetaAttribute getRarityAttr(int rarity) {
        MetaAttribute attribute = new MetaAttribute();
        attribute.setDisplayType(displayPercentage);
        attribute.setTraitType(MetaAttribute.rarity);
        attribute.setValue(rarity);
        return attribute;
    }

    public static MetaAttribute getRarityTypeAttr(String rarityType) {
        MetaAttribute attribute = new MetaAttribute();
        attribute.setTraitType(MetaAttribute.rarityType);
        attribute.setValue(rarityType);
        return attribute;
    }


}
