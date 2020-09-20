package com.mrzhgn.pedia;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FavDataAdapter extends RecyclerView.Adapter<FavDataAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Course> courses;
    private OnFavItemListener mOnFavItemListener;

    FavDataAdapter(Context context, List<Course> courses, OnFavItemListener onFavItemListener) {
        this.courses = courses;
        this.inflater = LayoutInflater.from(context);
        mOnFavItemListener = onFavItemListener;
    }
    @Override
    public FavDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_list_item, null);
        return new FavDataAdapter.ViewHolder(view, mOnFavItemListener);
    }

    @Override
    public void onBindViewHolder(FavDataAdapter.ViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.imageView.setImageResource(course.getImage());
        holder.titleView.setText(course.getTitle());
        holder.costView.setText(course.getCost());
        holder.basketView.setOnClickListener(v -> {
            if (!(DataLists.getBasketCourse().contains(course) || DataLists.getMyCourse().contains(course))) DataLists.addBasketCourse(course);
        });
        holder.courseView.setOnClickListener(v -> {
            if (!DataLists.getMyCourse().contains(course)) DataLists.addMyCourse(course);
            DataLists.removeBasketCourse(course);
        });

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView imageView;
        final TextView titleView, costView;
        final Button basketView, courseView;

        OnFavItemListener onFavItemListener;

        ViewHolder(View view, OnFavItemListener onFavItemListener){
            super(view);
            imageView = (ImageView) view.findViewById(R.id.fav_course_image);
            titleView = (TextView) view.findViewById(R.id.fav_title);
            costView = (TextView) view.findViewById(R.id.fav_cost);
            basketView = (Button) view.findViewById(R.id.fav_add_basket);
            courseView = (Button) view.findViewById(R.id.fav_add_course);
            this.onFavItemListener = onFavItemListener;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onFavItemListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnFavItemListener {
        void onItemClick(int position);
    }
}
