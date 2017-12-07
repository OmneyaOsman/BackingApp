package com.omni.backingapp.db;

import io.realm.RealmObject;

/**
 *
 */

public class IngredientEntry extends RealmObject {

    private Float quantity;

    private String measure;

    private String ingredient;

    public IngredientEntry() {
    }

    public IngredientEntry(Float quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
