package com.omni.backingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omni.backingapp.R;
import com.omni.backingapp.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Omni on 17/11/2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.RecipeViewHolder> {

    private Context context ;
    private List<Step> stepList ;

    private OnShowMoreClickListener mListener ;

    public interface OnShowMoreClickListener{
        void setOnClickListener(int position);
    }


    public StepAdapter(Context context, List<Step> stepList , OnShowMoreClickListener mListener) {
        this.context = context;
        this.stepList = stepList;
        this.mListener = mListener ;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.step_list_item, parent, false);
        RecipeViewHolder mHolder = new RecipeViewHolder(v) ;
        return mHolder ;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {

        Step step = stepList.get(position);
        holder.stepID.setText(String.valueOf(step.getId()));
        holder.shortDescTv.setText(step.getShortDescription());

    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }



    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.id_step_item)
        TextView stepID ;

        @BindView(R.id.short_desc_step_item)
        TextView shortDescTv ;

        @BindView(R.id.step_show_details)
        TextView showMoreTv ;



        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
            showMoreTv.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            mListener.setOnClickListener(pos);
        }
    }
}
