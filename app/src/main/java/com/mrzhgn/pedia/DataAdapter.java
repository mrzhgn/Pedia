package com.mrzhgn.pedia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Course> courses;
    private OnItemListener mOnItemListener;

    DataAdapter(Context context, List<Course> courses, OnItemListener onItemListener) {
        this.courses = courses;
        this.inflater = LayoutInflater.from(context);
        mOnItemListener = onItemListener;
    }
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
        return new ViewHolder(view, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.imageView.setImageResource(course.getImage());
        holder.titleView.setText(course.getTitle());
        holder.costView.setText(course.getCost());
        holder.favouriteView.setOnClickListener(v -> {
            if (!DataLists.getFavCourse().contains(course)) DataLists.addFavCourse(course);
        });
        holder.basketCourse.setOnClickListener(v -> {
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
        final Button favouriteView, basketCourse, courseView;

        OnItemListener onItemListener;

        ViewHolder(@NonNull View view, OnItemListener onItemListener){
            super(view);
            imageView = (ImageView) view.findViewById(R.id.course_image);
            titleView = (TextView) view.findViewById(R.id.title);
            costView = (TextView) view.findViewById(R.id.cost);
            favouriteView = (Button) view.findViewById(R.id.add_favourite);
            basketCourse = (Button) view.findViewById(R.id.add_basket);
            courseView = (Button) view.findViewById(R.id.add_course);
            this.onItemListener = onItemListener;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Course clicked = courses.get(getAdapterPosition());
            onItemListener.onItemClick(clicked);
        }
    }

    public interface OnItemListener {
        void onItemClick(Course course);
    }
}