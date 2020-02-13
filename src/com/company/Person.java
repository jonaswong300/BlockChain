package com.company;

class Person {

    private String name;
    private String hashOfLastTransaction;
    private long coins;

    //Default Constructor
    public Person(){

    }

    //Other Constructor
    public Person(String name){
        this.name = name;
        this.coins = 100;
    }

    public String getName() {
        return name;
    }

    public String getHashOfLastTransaction() {
        return hashOfLastTransaction;
    }

    public void setHashOfLastTransaction(String hashOfLastTransaction) {
        this.hashOfLastTransaction = hashOfLastTransaction;
    }

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }

    //String toString method
    @Override
    public String toString(){
        return "Name: " + name + " has " + coins + " coins remaining.";
    }
}
