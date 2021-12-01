package com.example.nvl.Class;

import java.io.Serializable;

public class Product implements Serializable {
    int id;
    String name;
    String review;
    int money;
    String kind;
    String img;
    double rate;
    String size;
    String color;
    int number;

    public Product(int id, String name, String review, int money, String kind, String img, double rate, String size, String color, int number) {
        this.id = id;
        this.name = name;
        this.review = review;
        this.money = money;
        this.kind = kind;
        this.img = img;
        this.rate = rate;
        this.size = size;
        this.color = color;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
