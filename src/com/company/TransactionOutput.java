package com.company;

class TransactionOutput {
    private Person receiver;
    private long transactionAmount;
    private String inputHash; //HASH(sender + receiver + transaction amount + transaction sequence number)
    private String outputHashReceiver; //HASH(input hash + receiver + transaction amount)
    private Person sender;
    private long senderWalletBalance;
    private String outputHashSender; //HASH(input hash + sender + transaction amount)

    public TransactionOutput(Person receiver, long transactionAmount, String inputHash, String outputHashReceiver,
                             Person sender, long senderWalletBalance, String outputHashSender){
        this.receiver = receiver;
        this.transactionAmount = transactionAmount;
        this.inputHash = inputHash;
        this.outputHashReceiver = outputHashReceiver;
        this.sender = sender;
        this.senderWalletBalance = senderWalletBalance;
        this.outputHashSender = outputHashSender;
    }

    public Person getReceiver() {
        return receiver;
    }

    public long getTransactionAmount() {
        return transactionAmount;
    }

    public String getInputHash() {
        return inputHash;
    }

    public String getOutputHashReceiver() {
        return outputHashReceiver;
    }

    public Person getSender() {
        return sender;
    }

    public long getSenderWalletBalance() {
        return senderWalletBalance;
    }

    public String getOutputHashSender() {
        return outputHashSender;
    }
}
