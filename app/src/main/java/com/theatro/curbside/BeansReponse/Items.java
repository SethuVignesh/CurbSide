package com.theatro.curbside.BeansReponse;

/**
 * Created by sethugayu on 12/20/17.
 */

public class Items {
    int sku;
    String name;
    String description;

    public Items(int sku, String name, String description) {
        this.sku = sku;
        this.name = name;
        this.description = description;
    }

    public int getSku() {

        return sku;
    }

    public void setSku(int sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
