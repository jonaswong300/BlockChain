package com.company;

import java.util.Date;

class Block {

    private String blockId;
    private String previousBlockHash;
    private long timeStamp;
    private Transaction transaction = null;
    private String merkleRootHash;
    private long nonce;

    //Other constructor
    public Block(Transaction transaction, String previousBlockHash){
        this.transaction = transaction;
        this.previousBlockHash = previousBlockHash;
        this.timeStamp = new Date().getTime();
        this.merkleRootHash = calculateMerkleRootHash();
        this.blockId = calculateHash();
    }

    public String getBlockId() {
        return blockId;
    }

    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    //Calculate Merkle root hash
    public String calculateMerkleRootHash(){
        return SHA256.getHash(transaction.getInputHash());
    }

    //Calculate BlockID hash
    public String calculateHash(){
        return SHA256.getHash( previousBlockHash + timeStamp + nonce+ merkleRootHash);
    }

    //Mine the Nonce value to ensure the hash has met the difficult level required.
    public void mineBlock(int difficulty){
        nonce = 0;

        //Create a string with difficult * 0
        String target = new String(new char[difficulty]).replace('\0', '0');

        while(!blockId.substring(0, difficulty).equals(target)){
            nonce++;
            blockId = calculateHash();
        }

        System.out.println("Block mined!: " + blockId);
    }

}
