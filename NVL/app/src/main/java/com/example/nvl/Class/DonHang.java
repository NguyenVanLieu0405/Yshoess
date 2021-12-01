package com.example.nvl.Class;

import java.util.Date;
import java.util.List;

public class DonHang {
    int id;
    String date;
    String phone;
    String address;
    List<ProductCart> cartList;
    int money;

    public DonHang(int id, String date, String phone, String address, List<ProductCart> cartList, int money) {
        this.id = id;
        this.date = date;
        this.phone = phone;
        this.address = address;
        this.cartList = cartList;
        this.money = money;
    }

    public DonHang() {
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ProductCart> getCartList() {
        return cartList;
    }

    public void setCartList(List<ProductCart> cartList) {
        this.cartList = cartList;
    }
}
