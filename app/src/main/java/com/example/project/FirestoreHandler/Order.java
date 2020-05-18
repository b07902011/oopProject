package com.example.project.FirestoreHandler;

public class Order {
    public String object, material, size, other, id, status, user;
    @Override
    public String toString(){
        return "項目: "+object +"; " +"材料: "+material +"; "+"尺寸: "+size +"; "+"備註: "+other +"; ";
    }
}
