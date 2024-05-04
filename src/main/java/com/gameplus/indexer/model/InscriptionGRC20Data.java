package com.gameplus.indexer.model;

import lombok.Data;

@Data
public class InscriptionGRC20Data {

    String inscriptionData;
    long inscriptionNumber;

    String txId;
    int idx;

    long height;

    int vout;
    long satoshi;

    String from;
    String to;

}
