package com.company;

class Transaction {

    //Input
    private int sequenceNumber;
    private Person sender;
    private Person receiver;
    private long transactionAmount;
    private String hashOfSenderLastTransaction;
    private String inputHash; //HASH(sender + receiver + transaction amount + transaction sequence number)
    //private String senderSignature;

    //Output
    TransactionOutput output;

    //Default constructor
    public Transaction(){

    }

    //Other constructor
    public Transaction(int sequenceNumber, Person sender, Person receiver,
                       long transactionAmount, String hashOfSenderLastTransaction,
                       String inputHash, TransactionOutput output){//, String senderSignature){
        this.sequenceNumber = sequenceNumber;
        this.sender = sender;
        this.receiver = receiver;
        this.transactionAmount = transactionAmount;
        this.hashOfSenderLastTransaction = hashOfSenderLastTransaction;
        this.inputHash = inputHash;
        //this.senderSignature = senderSignature;
        this.output = output;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public Person getSender() {
        return sender;
    }

    public Person getReceiver() {
        return receiver;
    }

    public long getTransactionAmount() {
        return transactionAmount;
    }

    public String getHashOfSenderLastTransaction() {
        return hashOfSenderLastTransaction;
    }

    public String getInputHash() {
        return inputHash;
    }

    @Override
    public String toString(){
        return "Sequence number: " + sequenceNumber + "\nSender is " + sender + "\nTransaction amount: " + transactionAmount +
                "\nReceiver is " + receiver + "\nHash of sender last transaction is " + hashOfSenderLastTransaction + "\nInput hash calculated " + inputHash + "\n";
    }
}
