package com.gameplus.indexer.model;

import com.gameplus.indexer.utils.Utils;
import lombok.Data;

@Data
public class Inscription {

    long height;
    long inscriptionNumber;
    String inscriptionId;
    String data;

    public static Inscription getInstance(InscriptionGRC20Data data) {
        Inscription inscription = new Inscription();
        inscription.setHeight(data.getHeight());
        inscription.setInscriptionNumber(data.getInscriptionNumber());
        inscription.setInscriptionId(Utils.getInscriptionId(data.getTxId(), data.getIdx()));
        inscription.setData(data.getInscriptionData());
        return inscription;
    }

}
