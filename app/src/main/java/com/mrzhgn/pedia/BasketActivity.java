package com.mrzhgn.pedia;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class BasketActivity extends AppCompatActivity implements DataAdapter.OnItemListener {

    private RecyclerView basketList;
    private DataAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button buyButton;

    private List<Course> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        basketList = (RecyclerView) findViewById(R.id.basket_list_act);
        buyButton = (Button) findViewById(R.id.basket_buy);

        list = new ArrayList<>();
        setInitialData();

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        basketList.setLayoutManager(layoutManager);

        mAdapter = new DataAdapter(this, list, this);
        basketList.setAdapter(mAdapter);

        buyButton.setOnClickListener(v -> {
            for (int i = 0; i < list.size(); ) {
                DataLists.addMyCourse(list.get(i));
                DataLists.removeBasketCourse(list.get(i));
            }
            finish();
        });
    }

    private void setInitialData(){
        list = DataLists.getBasketCourse();
    }

    @Override
    public void onItemClick(Course course) {
        Intent intent = new Intent(this, CourseActivity.class);
        intent.putExtra("key", course.getId());
        startActivity(intent);
    }
}
