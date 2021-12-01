package com.example.nvl.Interface;

import com.example.nvl.Class.Product;
import com.example.nvl.Class.ProductCart;

import java.util.List;

public interface ISendData {
    void sendData(List<Product> list);
    void sendDataCart(List<ProductCart> list);
}
