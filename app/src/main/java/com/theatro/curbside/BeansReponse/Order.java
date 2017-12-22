package com.theatro.curbside.BeansReponse;

import java.util.ArrayList;

/**
 * Created by sethugayu on 12/20/17.
 */

public class Order {
    String id;
    String type;
    ArrayList<Items> itemses;

    public Order(String id, String type, ArrayList<Items> itemses) {
        this.id = id;
        this.type = type;
        this.itemses = itemses;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Items> getItemses() {
        return itemses;
    }

    public void setItemses(ArrayList<Items> itemses) {
        this.itemses = itemses;
    }
}
