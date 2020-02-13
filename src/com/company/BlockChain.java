package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

import com.google.gson.*;

class BlockChain {

    public static ArrayList<Block> blockChain = new ArrayList<Block>();
    public static ArrayList<Transaction> transactionList = new ArrayList<>();
    public static HashMap<String, Person> personMap = new HashMap<>();
    public static HashSet<String> names = new HashSet<>();

    public static final int difficulty = 4;

    //Read in data from ledger.txt
    //Ledger is in format
    //[Sender] pays [Receiver] [transactionAmount] dollars
    public void readLedger(String fileName){
        int sequenceNumber = 1;
        String sender, receiver, inputHash, outputHashReceiver, outputHashSender, hashOfSenderLastTransaction = "0";
        long transactionAmount;

        Person s, r;
        TransactionOutput transactionOutput = null;

        //Read in file and extract the relevant information needed only.
        File myFile = new File(fileName);
        try {
            Scanner input = new Scanner(myFile);
            String aLine;
            Transaction temp = null;
            while(input.hasNextLine()) {
                //Read in ledger line by line.
                //Split each line into its individual array with regex " "
                aLine = input.nextLine();
                String [] split = aLine.split(" ");

                //Ledger is in format
                //[Sender] pays [Receiver] [transactionAmount] dollars
                sender = split[0];
                receiver = split[2];
                transactionAmount = Long.parseLong(split[3]);

                //Check if names already exist in the ledger.
                //If doesnt exist, create new Person Object
                if(!names.contains(sender)){
                    names.add(sender);
                    s = new Person(sender);
                    personMap.put(sender, s);
                }

                if(!names.contains(receiver)){
                    names.add(receiver);
                    r = new Person(receiver);
                    personMap.put(receiver, r);
                }

                //HASH(sender + receiver + transaction amount + transaction sequence number)
                inputHash = SHA256.getHash(sender + receiver+ transactionAmount + sequenceNumber);
                outputHashReceiver = SHA256.getHash(inputHash + receiver + transactionAmount);
                outputHashSender = SHA256.getHash(inputHash + sender + transactionAmount);

                transactionOutput = new TransactionOutput(personMap.get(receiver), transactionAmount, inputHash,
                                                          outputHashReceiver, personMap.get(sender),
                                                          personMap.get(sender).getCoins(), outputHashSender);

                if(sequenceNumber == 1){

                    temp = new Transaction(sequenceNumber, personMap.get(sender), personMap.get(receiver),
                            transactionAmount, hashOfSenderLastTransaction, inputHash, transactionOutput);
                }else{
                    temp = new Transaction(sequenceNumber, personMap.get(sender), personMap.get(receiver),
                            transactionAmount, temp.getInputHash(), inputHash, transactionOutput);
                }

                transactionList.add(temp);
                sequenceNumber++;
            }

            input.close();
        }
        catch(FileNotFoundException e) {
            System.err.println("File: " + fileName +  " is not file");
        }
    }

    //Write mine hash and transaction into new ledger
    public void writeLedger(ArrayList <Block> blockChain, String fileName){
        String transaction = "";
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            for(Block b : blockChain){
                transaction = b.getTransaction().getSender().getName() + " Pays " + b.getTransaction().getReceiver().getName() +
                        " " + b.getTransaction().getTransactionAmount() + " dollars.\n";
                fileWriter.write(transaction);
                fileWriter.write(b.getBlockId() + "\n\n");
            }

            fileWriter.close();
        }catch (IOException e) {
            System.err.println("Unable to write to " + fileName);
        }
    }

    //Ensure hashes matches current block and previous block
    public static boolean isChainValid(){
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        //Loop through blockchain to check hashes
        for(int i = 1; i < blockChain.size(); i++) {
            currentBlock = blockChain.get(i);
            previousBlock = blockChain.get(i - 1);

            //Compare registered hash and calculated hash
            if(!currentBlock.getBlockId().equals(currentBlock.calculateHash())){
                System.out.println("Current hashes are not equal");
                return false;
            }
            //Compare previous hash and registed previous hash
            if(!previousBlock.getBlockId().equals(currentBlock.getPreviousBlockHash())){
                System.out.println("Previous hashes are not equal");
                return false;
            }

            //Check if hash is solved
            if(!currentBlock.getBlockId().substring(0, difficulty).equals(hashTarget)){
                System.out.println("This block hasn't been minded");
                return false;
            }
        }
        return true;
    }

    //Handle transactions performed
    public static void handleTransactions(int sequenceNumber){
        for(Block b : blockChain){
            if(b.getTransaction().getSequenceNumber() == sequenceNumber){
                long remainingAmount = b.getTransaction().getSender().getCoins() - b.getTransaction().getTransactionAmount();
                long addedAmount = b.getTransaction().getReceiver().getCoins() + b.getTransaction().getTransactionAmount();

                b.getTransaction().getSender().setCoins(remainingAmount);
                b.getTransaction().getReceiver().setCoins(addedAmount);

                System.out.println(b.getTransaction().getSender().getName() + " has " + b.getTransaction().getSender().getCoins());
                System.out.println(b.getTransaction().getReceiver().getName() + " has " + b.getTransaction().getReceiver().getCoins());
            }
        }
    }

    //Develop and display blockchain
    public void displayBlockChain(){
        //Add our blocks to the ArrayList
        //First block is genesis block. Previous hash will be instantiated at 0.
        blockChain.add(new Block(transactionList.get(0), "0"));
        System.out.println("Mining block 1...");
        handleTransactions(1);
        blockChain.get(0).mineBlock(difficulty);

        //Add the remaining blocks into the BlockChain
        for(int i = 1; i < transactionList.size(); i++){
            blockChain.add(new Block(transactionList.get(i), blockChain.get(i-1).getBlockId()));
            System.out.println("\nMining block " + (i+1) + "....");
            handleTransactions((i+1));
            blockChain.get(i).mineBlock(difficulty);
        }

        //Check if blockchain has met the requirements of meeting hashes and previous and current blocks.
        System.out.println("\nBlockchain is valid: " + isChainValid());

        //Using GSON builder to output data
        String blockChainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);
        System.out.println("\nThe Block chain: ");
        System.out.println(blockChainJson);
    }

    //Driver function
    public static void main(String [] args){
        BlockChain b = new BlockChain();
        b.readLedger("ledger.txt");
        b.displayBlockChain();
        b.writeLedger(blockChain, "new_ledger.txt");
    }

}
