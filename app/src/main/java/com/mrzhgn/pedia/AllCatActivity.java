package com.mrzhgn.pedia;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllCatActivity extends AppCompatActivity implements AllCategoriesAdapter.OnItemListener {

    private RecyclerView catList;
    private AllCategoriesAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Categories> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categories);

        list = new ArrayList<>();
        setInitialData();

        catList = (RecyclerView) findViewById(R.id.categories_list_act);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        catList.setLayoutManager(layoutManager);

        mAdapter = new AllCategoriesAdapter(this, list, this);
        catList.setAdapter(mAdapter);
    }

    private void setInitialData() {
        Collections.addAll(list, Categories.values());
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra("key", list.get(position).toString());
        startActivity(intent);
    }
}
