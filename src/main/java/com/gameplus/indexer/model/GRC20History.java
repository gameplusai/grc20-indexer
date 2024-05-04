package com.gameplus.indexer.model;

import lombok.Data;

@Data
public class GRC20History {

    String type;
    boolean valid;

    String from;
    String to;

    String txId;
    int vout;
    long satoshi;

    Inscription inscription;


    public static GRC20History getInstance(String type, boolean valid, InscriptionGRC20Data data, Inscription inscription) {
        GRC20History history = new GRC20History();
        history.setType(type);
        history.setValid(valid);
        history.setFrom(data.getFrom());
        history.setTo(data.getTo());
        history.setTxId(data.getTxId());
        history.setVout(data.getVout());
        history.setSatoshi(data.getSatoshi());
        history.setInscription(inscription);
        return history;
    }


}
