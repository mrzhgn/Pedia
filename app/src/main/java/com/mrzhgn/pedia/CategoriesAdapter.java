package com.mrzhgn.pedia;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Categories> categories;

    CategoriesAdapter(Context context, List<Categories> categories) {
        this.categories = categories;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_list_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoriesAdapter.ViewHolder holder, int position) {
        Categories category = categories.get(position);
        holder.viewAll.setText(category.toString());
        holder.viewAll.setOnClickListener(v -> {
            Intent intent = new Intent(inflater.getContext(), CategoryActivity.class);
            intent.putExtra("key", category.toString());
            inflater.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final Button viewAll;

        ViewHolder(@NonNull View view){
            super(view);
            viewAll = (Button) view.findViewById(R.id.course_category);
        }

    }
}