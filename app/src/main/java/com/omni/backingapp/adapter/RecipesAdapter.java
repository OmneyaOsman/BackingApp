package com.omni.backingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.omni.backingapp.R;
import com.omni.backingapp.model.RecipeResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Omni on 17/11/2017.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    private Context context ;
    private OnItemClick mListener ;
    private List<RecipeResponse> mRecipeList ;




    public interface OnItemClick{
        void setOnRcipeClickListener(int position);
    }

    public RecipesAdapter(Context context, List<RecipeResponse> mRecipeList , OnItemClick mListener) {
        this.context = context;
        this.mListener = mListener;
        this.mRecipeList = mRecipeList ;
    }


    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recipe_list_item, parent, false);
        RecipeViewHolder mHolder = new RecipeViewHolder(v) ;
        return mHolder ;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {

        RecipeResponse recipe = mRecipeList.get(position);
        holder.recipeNameTv.setText(recipe.getName());
        holder.recipeServingTv.setText(context.getString(R.string.servings).concat(String.valueOf(recipe.getServings())));
        Glide.with(context)
                .load(recipe.getImage())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }



    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.recipe_name)
        TextView recipeNameTv ;

        @BindView(R.id.recipe_serving)
        TextView recipeServingTv ;
        @BindView(R.id.recipe_image)
        ImageView imageView ;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int pos = getAdapterPosition();
            mListener.setOnRcipeClickListener(pos);

        }
    }
}
