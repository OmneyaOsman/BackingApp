package com.omni.backingapp.db;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Omni on 06/12/2017.
 */

public class RecipeEntry extends RealmObject {


    private Integer id;

    private String name;

    private RealmList<IngredientEntry> ingredients = null;

    public RecipeEntry() {
    }

    public RecipeEntry(Integer id, String name, RealmList<IngredientEntry> ingredients) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<IngredientEntry> getIngredients() {
        return ingredients;
    }

    public void setIngredients(RealmList<IngredientEntry> ingredients) {
        this.ingredients = ingredients;
    }
}
