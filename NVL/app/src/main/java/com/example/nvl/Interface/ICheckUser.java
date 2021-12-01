package com.example.nvl.Interface;

public interface ICheckUser {
    public abstract boolean onPassConfirm(String sdt,  String mk);

    public abstract boolean hasLength(String sdt,String mk);

    public abstract boolean hasSymbol(String mk);
    public abstract boolean hasOnlyNumber(String sdt) ;

    public abstract boolean hasUpperCase(String password) ;

    public abstract boolean hasLowerCase(String password);

    public abstract boolean hasNumber(String password) ;
}
