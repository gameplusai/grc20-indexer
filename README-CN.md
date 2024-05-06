# GRC20 Protocol Indexer

### GRC20-Game protocol based on Bitcoin Ordinal

This document mainly introduces the GRC20 protocol and Indexer

### GRC20

The GRC20 protocol defines common Mint licensing, synthesis, upgrades, reference royalties and other operations common in games

#### Deploy

Release of new grc20 game NFT asset collection

````
{
  "p": "grc-20",
  "op": "deploy",
  "name":"GamePlus Box",
  "symbol": "GPB",
  "maxSupply": "10000",
  "baseTokenUri": "https://ipfs.io/game-plus/dragon/",
  "nonce":0,
  "signer":"mint singer pubkey"
}
```` 

| Key          | Required | Description                                                              |
|--------------|----------|--------------------------------------------------------------------------|
| p            | Yes      | Protocol: Helps other systems identify and process grc20 events          |
| op           | Yes      | Operation: Type of event (deploy, mint, upgrade)                         |
| name         | Yes      | The collection name                                                      |
| symbol       | Yes      | Identifier of the grc20, case insensitive                                |
| maxSupply    | Yes      | The collection max supply                                                |
| baseTokenUri | Yes      | The collection all nfts base token uri                                   |
| nonce        | Yes      | Nonce,every collection has an independent monotonically increasing nonce |
| signer       | Yes      | mint singer pubkey                                                       |

### Mint

Create a new NFT from a collection , need to verify Mint permissions.

Use `metaHash` to ensure the credibility of NFT Meta information under the chain
Convert Meta object to Json, the order of Json Key is: `name`, `description`, `image`, `attributes`

`metaHash = sha256(metaJson)`

`sig` uses `ecdsa` signature algorithm to control mint’s permissions. The signature content is: `metaHash` + `tokenId` + `nonce`.

`sig = ecdsa(metaHash + tokenId + nonce)`



````
{
  "p": "grc-20",
  "op": "mint",
  "symbol":"GP",
  "tokenId":"tokenId",
  "metaHash":"hash256",
  "nonce":1,
  "sig": "metaHash + tokenId + nonce"
}

````

| Key      | Required | Description                                                     |
|----------|----------|-----------------------------------------------------------------|
| p        | Yes      | Protocol: Helps other systems identify and process grc20 events |
| op       | Yes      | Operation: Type of event (deploy, mint, upgrade)                |
| symbol   | Yes      | Identifier of the grc20, case insensitive                       |
| tokenId  | Yes      | The nft tokenId                                                 |
| metaHash | Yes      | The nft meta info hash , use hash256                            |
| nonce    | Yes      | Nonce                                                           |
| sig      | Yes      | ecdsa(metaHash + tokenId + nonce)                               |

### Transfer

It's simple to transfer an grc20 nft, just send the inscription minted above to the receiver. There is no need to mint a transfer inscription before sending like brc-20.

### Upgrade

When the properties of the NFT are upgraded, the same `tokenId` NFT needs to be re-engraved. This operation is mainly to update the NFTMeta information and use Sig to control permissions.

````
{
"p": "grc-20",
"op": "upgrade",
"symbol":"GP",
"tokenId":"tokenId",
"metaHash":"new metaHash",
"nonce":2,
"sig": "metaHash + tokenId + nonce"
}
````
| Key      | Required | Description                                                     |
|----------|----------|-----------------------------------------------------------------|
| p        | Yes      | Protocol: Helps other systems identify and process grc20 events |
| op       | Yes      | Operation: Type of event (deploy, mint, upgrade)                |
| symbol   | Yes      | Identifier of the grc20, case insensitive                       |
| tokenId  | Yes      | The nft tokenId                                                 |
| metaHash | Yes      | The nft meta info hash , use hash256                            |
| nonce    | Yes      | Nonce                                                           |
| sig      | Yes      | ecdsa(metaHash + tokenId + nonce)                               |

### Synthesis `quote-price`

合成时,引用官方NFT,或者引用玩家NFT进行合成时,被引用资产设置被引用价格

````
{
  "p": "grc-20",
  "op": "quote-price",
  "symbol":"GP",
  "tokenId":"tokenId",
  "price":20000,
  "nonce":3,
  "sig": "price + tokenId + nonce"
}
````

| Key     | Required | Description                                                     |
|---------|----------|-----------------------------------------------------------------|
| p       | Yes      | Protocol: Helps other systems identify and process grc20 events |
| op      | Yes      | Operation: Type of event (deploy, mint, upgrade)                |
| symbol  | Yes      | Identifier of the grc20, case insensitive                       |
| tokenId | Yes      | The nft tokenId                                                 |
| price   | Yes      | The nft quote price,unit is satoshi                             |
| nonce   | Yes      | Nonce                                                           |
| sig     | Yes      | ecdsa(price + tokenId + nonce)                                  |

### Synthesis

引用其它NFT进行合成

````
{
  "p": "grc-20",
  "op": "synthesis",
  "symbol":"GP",
  "tokenId":1,
  "metaHash":"new metaHash",
  "quote":[
    "inscriptionId1",
    "inscriptionId2"
  ],
  "nonce":3,
  "sig": "metaHash + json(quote) + tokenId + nonce"
}

````

| Key     | Required | Description                                                     |
|---------|----------|-----------------------------------------------------------------|
| p       | Yes      | Protocol: Helps other systems identify and process grc20 events |
| op      | Yes      | Operation: Type of event (deploy, mint, upgrade)                |
| symbol  | Yes      | Identifier of the grc20, case insensitive                       |
| tokenId | Yes      | The nft tokenId                                                 |
| price   | Yes      | The nft quote price,unit is satoshi                             |
| quote   | Yes      | Quote inscription ids                                           |
| nonce   | Yes      | Nonce                                                           |
| sig     | Yes      | ecdsa(metaHash + json(quote) + tokenId + nonce)                 |

