package com.example.mgl2.jellybeancafeserver.Model;

public class Cart {
    private int id,amount,size;
    private String name,link,toppingExtras;
    private double price;
    private short sugar;

    public Cart() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getToppingExtras() {
        return toppingExtras;
    }

    public void setToppingExtras(String toppingExtras) {
        this.toppingExtras = toppingExtras;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public short getSugar() {
        return sugar;
    }

    public void setSugar(short sugar) {
        this.sugar = sugar;
    }

}
