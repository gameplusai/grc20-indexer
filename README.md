# GRC20 Protocol Indexer

Introducing the first on-chain gaming infrastructure on Bitcoin, Game Plus leverages Bitcoin Ordinals to usher in the next level of Bitcoin-native gaming applications. GamePlus will be the gaming engine of the bitcoin ecosystem - through strategic partnerships and native solutions, GamePlus’ zkRollup powered-L2 will offer a 0 transaction fee gaming layer through zk State Channels, which composes of GRC20 Game Asset Creation, marketplace, staking, and advanced configurability (royalties, batch processing, and many others), modular yet powerful SDKs for game developers to deploy on BTC, in order to host the new era of Web3 gaming - powered and secured secured by the Bitcoin Ecosystem. Building on Babylon's Bitcoin staking protocol, Game Plus enables data integrity tracking and fraud behavior detection, which are important to game developers.

Current protocols such as BRC20 and BRC721 protocols on Bitcoin, while foundational, focus on simpler applications and don't fully cater to game development. They lack features and adaptability needed for complex game scenarios. In addition, there is a lack of execution layer and no real infrastructures for game developers to deploy execution logic on L1.
High gas costs and transaction times, a byproduct of their decentralized design, further detract from the gaming experience. The Bitcoin gaming infrastructure, still developing, doesn't fully leverage the potential of the Ordinals protocol, and the absence of a comprehensive game protocol and SDK hinders effective game development. We build Game Plus to address these pain points and enable game developers to build with state-of-the-art SDKs and deploy games on BTC chain easily.

---

### GRC20-Game protocol based on Bitcoin Ordinal

This document mainly introduces the GRC20 protocol and Indexer

---

### GRC20

The GRC20 protocol defines common Mint licensing, synthesis, upgrades, reference royalties and other operations common in games

----

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
| signer       | No       | mint singer pubkey , If the singer is not set, everyone can mint fairly  |

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

When synthesizing, when quoting official NFT, or quoting player NFT for synthesis, the quoted asset sets the quoted price.

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

Reference other NFTs for synthesis

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

----
### State Changes

#### Collection Deployer

The address holding the deploy inscription is the deployer
The receiving address of the first deploy inscription minting becomes the deployer
If the deploy inscription is transferred to a new address, the new address becomes the deployer

#### Token ID

Similar to ERC721, each token in a GRC20 collection has a unique ID
Minting inscriptions beyond the total supply are invalid
mint inscription ID should be larger than the `deploy` inscription ID

#### Token Owner

The address holding the mint inscription is the owner of the nft
When the mint inscription is transferred to a new address, the owner changes to the new address
Transfer

#### Transfer 

The NFT token using grc wallet `send <ADDRESS> <INSCRIPTION ID>`

#### Metadata

The metadata of the nft is initialized by the deploy inscription<br/>
The metadata of the nft can be updated by the update inscription<br/>
The update nft must be minted by the owner of deploy inscription and sent to the same address as the deploy inscription<br/>
update inscription ID should be larger than the deploy inscription ID<br/>

### Contribution

GRC20 is an standard that brings game non-fungible tokens (NFTs) to the Bitcoin network. With this standard, users can create, mint, transfer, and update unique digital assets

The standard allows for a series of operations that facilitate the management of non-fungible tokens, including deployment, minting, transferring, and updating metadata. Each token is assigned a unique identifier, ensuring that each NFT is distinct and cannot be exchanged on a one-to-one basis with another NFT.

GRC20 invites improvements and modifications to enhance its functionality and adapt it to the growing needs of the NFT ecosystem.



