package com.mrzhgn.pedia;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity implements DataAdapter.OnItemListener {

    private RecyclerView catList;
    private DataAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private Categories value;
    private List<Course> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Intent intent = getIntent();
        value = Categories.valueOf(intent.getStringExtra("key"));

        list = new ArrayList<>();
        setInitialData();

        catList = (RecyclerView) findViewById(R.id.courses_list_act);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        catList.setLayoutManager(layoutManager);

        mAdapter = new DataAdapter(this, list, this);
        catList.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(Course course) {
        Intent intent = new Intent(this, CourseActivity.class);
        intent.putExtra("key", course.getId());
        startActivity(intent);
    }

    private void setInitialData(){

        List<Course> data = DataLists.getData();

        for (Course c : data) {
            list.add(c);
        }

        List<Course> data2 = DataLists.getData2();

        for (Course c : data2) {
            list.add(c);
        }

        List<Course> data3 = DataLists.getData3();

        for (Course c : data3) {
            list.add(c);
        }

        for (int i = 0; i < list.size(); ) {
            if (!list.get(i).getCategories().contains(value)) list.remove(i);
            else i++;
        }

    }
}
