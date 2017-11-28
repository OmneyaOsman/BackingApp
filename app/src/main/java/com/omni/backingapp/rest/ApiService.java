package com.omni.backingapp.rest;

import com.omni.backingapp.model.RecipeResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiService {


    @GET("baking.json")
    Call<List<RecipeResponse>> callRecipes();
}

