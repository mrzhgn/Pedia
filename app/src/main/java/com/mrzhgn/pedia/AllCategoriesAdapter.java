package com.mrzhgn.pedia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AllCategoriesAdapter extends RecyclerView.Adapter<AllCategoriesAdapter.ViewHolder>  {

    private LayoutInflater inflater;
    private List<Categories> categories;
    private AllCategoriesAdapter.OnItemListener mOnItemListener;

    AllCategoriesAdapter(Context context, List<Categories> categories, AllCategoriesAdapter.OnItemListener onItemListener) {
        this.categories = categories;
        this.inflater = LayoutInflater.from(context);
        mOnItemListener = onItemListener;
    }
    @Override
    public AllCategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_cat_list_item, null);
        return new AllCategoriesAdapter.ViewHolder(view, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(AllCategoriesAdapter.ViewHolder holder, int position) {
        Categories category = categories.get(position);
        holder.imageView.setImageResource(R.drawable.ic_priority_high_black_24dp);
        holder.textView.setText(category.toString());

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView imageView;
        final TextView textView;

        AllCategoriesAdapter.OnItemListener onItemListener;

        ViewHolder(@NonNull View view, AllCategoriesAdapter.OnItemListener onItemListener){
            super(view);
            imageView = (ImageView) view.findViewById(R.id.cat_image);
            textView = (TextView) view.findViewById(R.id.cat_name);
            this.onItemListener = onItemListener;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemListener {
        void onItemClick(int position);
    }
}
