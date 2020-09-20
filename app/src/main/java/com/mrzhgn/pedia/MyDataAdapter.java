package com.mrzhgn.pedia;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyDataAdapter extends RecyclerView.Adapter<MyDataAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Course> courses;
    private OnMyItemListener mOnMyItemListener;

    MyDataAdapter(Context context, List<Course> courses, OnMyItemListener onMyItemListener) {
        this.courses = courses;
        this.inflater = LayoutInflater.from(context);
        mOnMyItemListener = onMyItemListener;
    }
    @Override
    public MyDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_list_item, null);
        return new MyDataAdapter.ViewHolder(view, mOnMyItemListener);
    }

    @Override
    public void onBindViewHolder(MyDataAdapter.ViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.imageView.setImageResource(course.getImage());
        holder.titleView.setText(course.getTitle());
        holder.costView.setText(course.getCost());

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView imageView;
        final TextView titleView, costView;

        MyDataAdapter.OnMyItemListener onMyItemListener;

        ViewHolder(View view, OnMyItemListener onMyItemListener){
            super(view);
            imageView = (ImageView) view.findViewById(R.id.my_course_image);
            titleView = (TextView) view.findViewById(R.id.my_title);
            costView = (TextView) view.findViewById(R.id.my_cost);
            this.onMyItemListener = onMyItemListener;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onMyItemListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnMyItemListener {
        void onItemClick(int position);
    }
}
