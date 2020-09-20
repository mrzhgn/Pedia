package com.mrzhgn.pedia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class CourseActivity extends AppCompatActivity {

    private ImageView courseImageAct;
    private TextView titleAct, costAct;
    private Button addFavouriteAct, addBasketAct, addCourseAct;

    private HashMap<Integer, Course> data;
    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Intent intent = getIntent();
        int value = intent.getIntExtra("key", 0);

        courseImageAct = (ImageView) findViewById(R.id.course_image_act);
        titleAct = (TextView) findViewById(R.id.title_act);
        costAct = (TextView) findViewById(R.id.cost_act);
        addFavouriteAct = (Button) findViewById(R.id.add_favourite_act);
        addBasketAct = (Button) findViewById(R.id.add_basket_act);
        addCourseAct = (Button) findViewById(R.id.add_course_act);

        data = DataLists.getAllCourses();
        course = DataLists.findCourseById(value);

        courseImageAct.setImageResource(course.getImage());
        titleAct.setText(course.getTitle());
        costAct.setText(course.getCost());
        addFavouriteAct.setOnClickListener(v -> {
            if (!DataLists.getFavCourse().contains(course)) DataLists.addFavCourse(course);
        });
        addBasketAct.setOnClickListener(v -> {
            if (!(DataLists.getBasketCourse().contains(course) || DataLists.getMyCourse().contains(course))) DataLists.addMyCourse(course);
        });
        addCourseAct.setOnClickListener(v -> {
            if (!DataLists.getMyCourse().contains(course)) DataLists.addMyCourse(course);
            DataLists.removeBasketCourse(course);
        });

    }
}
