package com.omni.backingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omni.backingapp.R;
import com.omni.backingapp.model.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Omni on 17/11/2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.RecipeViewHolder> {

    private Context context ;
    private List<Ingredient> ingredientList ;


    public IngredientAdapter(Context context, List<Ingredient> ingredientList) {
        this.context = context;
        this.ingredientList = ingredientList;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.ingredient_list_item, parent, false);
        RecipeViewHolder mHolder = new RecipeViewHolder(v) ;
        return mHolder ;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {

        Ingredient ingredient = ingredientList.get(position);
        holder.quntityTv.setText(String.valueOf(ingredient.getQuantity()));
        holder.measureTv.setText(ingredient.getMeasure());
        holder.ingredientTv.setText(ingredient.getIngredient());

    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }



    class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.quantity_ingredient_item)
        TextView quntityTv ;

        @BindView(R.id.measure_ingredient_item)
        TextView measureTv ;

        @BindView(R.id.ingredient_ingredient_item)
        TextView ingredientTv ;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }

    }
}
