//package com.theatro.curbside.model;
//
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//import com.theatro.curbside.BeansReponse.Items;
//
//import java.util.ArrayList;
//
//public class Post {
//
//    @SerializedName("customer")
//    @Expose
//    private String customer;
//
//    @SerializedName("id")
//    @Expose
//    private String id;
//
//    @SerializedName("name")
//    @Expose
//    private String name;
//
//
//    @SerializedName("type")
//    @Expose
//    private String type;
//
//    @SerializedName("order")
//    @Expose
//    private String order;
//
//    public String getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(String customer) {
//        this.customer = customer;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getOrder() {
//        return order;
//    }
//
//    public void setOrder(String order) {
//        this.order = order;
//    }
//
//    public ArrayList<Items> getItemsArray() {
//        return itemsArray;
//    }
//
//    public void setItemsArray(ArrayList<Items> itemsArray) {
//        this.itemsArray = itemsArray;
//    }
//
//    public int getSku() {
//        return sku;
//    }
//
//    public void setSku(int sku) {
//        this.sku = sku;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    @SerializedName("items")
//    @Expose
//    private ArrayList<Items> itemsArray;
//
//    @SerializedName("sku")
//    @Expose
//    private int sku;
//
//    @SerializedName("description")
//    @Expose
//    private String description;
//}