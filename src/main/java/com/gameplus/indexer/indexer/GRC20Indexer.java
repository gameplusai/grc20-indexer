package com.gameplus.indexer.indexer;

import com.gameplus.indexer.constant.Constant;
import com.gameplus.indexer.model.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GRC20Indexer {

    static ConcurrentHashMap<String, GRC20Collection> GRC20CollectionInfoMap = new ConcurrentHashMap<>();
    static ConcurrentHashMap<String, GRC20History> GRC20HistoryMap = new ConcurrentHashMap<>();
    static ConcurrentHashMap<String, Long> GRC20NFTPriceMap = new ConcurrentHashMap<>();
    //address -> symbol - > nfts
    static ConcurrentHashMap<String, Map<String, List<GRC20NFT>>> userGRC20NFTsMap = new ConcurrentHashMap<>();
    //symbol -> nfts
    static ConcurrentHashMap<String, List<GRC20NFT>> GRC20NftsMap = new ConcurrentHashMap<>();

    public static boolean checkGRC20CollectionExist(String symbol) {
        return GRC20CollectionInfoMap.containsKey(symbol);
    }


    public static void addCollection(GRC20Collection collection) {
        GRC20CollectionInfoMap.put(collection.getSymbol(), collection);
    }

    public static GRC20Collection getCollection(String symbol) {
        return GRC20CollectionInfoMap.get(symbol);
    }

    public static void addUserGRC20NFT(GRC20NFT nft) {
        String address = nft.getOwner();
        String symbol = nft.getSymbol();
        Map<String, List<GRC20NFT>> userNftsMap = userGRC20NFTsMap.get(address);
        if (Objects.isNull(userNftsMap)) {
            userNftsMap = new HashMap<>();
            userGRC20NFTsMap.put(address, userNftsMap);
        }
        List<GRC20NFT> nfts = userNftsMap.get(symbol);
        if (Objects.isNull(nfts)) {
            nfts = new ArrayList<>();
            userNftsMap.put(symbol, nfts);
        }
        nfts.add(nft);
    }

    public static void removeUserGRC20NFT(String address, GRC20NFT nft) {
        String symbol = nft.getSymbol();
        Map<String, List<GRC20NFT>> userNftsMap = userGRC20NFTsMap.get(address);
        if (Objects.isNull(userNftsMap) || userNftsMap.isEmpty()) return;
        List<GRC20NFT> list = userNftsMap.get(symbol);
        for (int i = list.size() - 1; i >= 0; i--) {
            GRC20NFT grc20NFT = list.get(i);
            if (grc20NFT.getTokenId().equals(nft.getTokenId())) {
                list.remove(i);
                break;
            }
        }
    }

    public static void removeGRC20NFT(GRC20NFT nft) {
        String symbol = nft.getSymbol();
        List<GRC20NFT> nfts = GRC20NftsMap.get(symbol);
        if (Objects.isNull(nfts) || nfts.isEmpty()) return;
        for (int i = nfts.size() - 1; i >= 0; i--) {
            GRC20NFT grc20NFT = nfts.get(i);
            if (grc20NFT.getTokenId().equals(nft.getTokenId())) {
                nfts.remove(i);
                break;
            }
        }
    }


    public static long getGRC20CollectionMaxSupply(String symbol) {
        GRC20Collection collection = GRC20CollectionInfoMap.get(symbol);
        if (Objects.isNull(collection)) return 0;
        return collection.getMaxSupply();
    }

    public static long getGRC20CollectionCount(String symbol) {
        List<GRC20NFT> nfts = GRC20NftsMap.get(symbol);
        if (Objects.isNull(nfts)) return 0;
        return nfts.size();
    }

    public static void addGRC20NFT(GRC20NFT nft) {
        String symbol = nft.getSymbol();
        List<GRC20NFT> nfts = GRC20NftsMap.get(symbol);
        if (Objects.isNull(nfts)) {
            nfts = new ArrayList<>();
            GRC20NftsMap.put(symbol, nfts);
        }
        nfts.add(nft);
    }

    public static void putGRC20NFTPrice(String tokenId, long price) {
        GRC20NFTPriceMap.put(tokenId, price);
    }

    public static long getGRC20NFTPrice(String tokenId) {
        Long price = GRC20NFTPriceMap.get(tokenId);
        if (Objects.isNull(price)) return 0;
        return price;
    }

    public static void addInvalidHistory(String symbol, InscriptionGRC20Data data) {
        Inscription inscription = Inscription.getInstance(data);
        GRC20History history = GRC20History.getInstance(Constant.MINT, false, data, inscription);
        GRC20HistoryMap.put(symbol, history);
    }

    public static void addValidHistory(String symbol, InscriptionGRC20Data data) {
        Inscription inscription = Inscription.getInstance(data);
        GRC20History history = GRC20History.getInstance(Constant.MINT, true, data, inscription);
        GRC20HistoryMap.put(symbol, history);
    }


}
