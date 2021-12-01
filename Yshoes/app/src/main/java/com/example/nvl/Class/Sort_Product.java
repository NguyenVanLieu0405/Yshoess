package com.example.nvl.Class;

import java.util.ArrayList;
import java.util.List;

public class Sort_Product {

    public static List<Product> NikeProduct(List<Product> list) {
        List<Product> pr = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getKind().equals("Nike"))
                pr.add(list.get(i));
        }
        return pr;
    }

    public static List<Product> AdidasProduct(List<Product> list) {
        List<Product> pr = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getKind().equals("Adidas"))
                pr.add(list.get(i));
        }
        return pr;
    }

    public static List<Product> VansProduct(List<Product> list) {
        List<Product> pr = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getKind().equals("Vans"))
                pr.add(list.get(i));
        }
        return pr;
    }

    public static List<Product> FashionProduct(List<Product> list) {
        List<Product> pr = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getKind().equals("Giày thời trang"))
                pr.add(list.get(i));
        }
        return pr;
    }


}
