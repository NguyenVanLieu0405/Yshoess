package com.example.nvl.Class;

public class Comment {
    int id;
    String name;
    String comment;


    public Comment(int id, String name, String comment) {
        this.id = id;
        this.name = name;
        this.comment = comment;

    }

    public Comment() {
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
