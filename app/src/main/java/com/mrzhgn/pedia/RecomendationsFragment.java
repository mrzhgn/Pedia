package com.mrzhgn.pedia;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecomendationsFragment extends Fragment implements DataAdapter.OnItemListener {

    private RecyclerView myList, myList2, myList3;
    private RecyclerView categoriesList;
    private DataAdapter mAdapter, mAdapter2, mAdapter3;
    private CategoriesAdapter mCategoriesAdapter;
    private RecyclerView.LayoutManager layoutManager, layoutManager2, layoutManager3,
            layoutManagerCat;
    private Button allButton;

    List<Course> courses, courses2, courses3;
    List<Categories> categories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recomendations, container, false);
        myList = (RecyclerView) v.findViewById(R.id.my_list);
        myList2 = (RecyclerView) v.findViewById(R.id.my_list_2);
        myList3 = (RecyclerView) v.findViewById(R.id.my_list_3);
        categoriesList = (RecyclerView) v.findViewById(R.id.categories_list);
        allButton = (Button) v.findViewById(R.id.categories_all);
        //myList.setHasFixedSize(true);

        courses = new ArrayList<>();
        courses2 = new ArrayList<>();
        courses3 = new ArrayList<>();
        categories = new ArrayList<>();

        setInitialData();

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        myList.setLayoutManager(layoutManager);
        myList2.setLayoutManager(layoutManager2);
        myList3.setLayoutManager(layoutManager3);

        mAdapter = new DataAdapter(getActivity(), courses, this);
        mAdapter2 = new DataAdapter(getActivity(), courses2, this);
        mAdapter3 = new DataAdapter(getActivity(), courses3, this);
        myList.setAdapter(mAdapter);
        myList2.setAdapter(mAdapter2);
        myList3.setAdapter(mAdapter3);

        layoutManagerCat = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.HORIZONTAL, false);
        categoriesList.setLayoutManager(layoutManagerCat);

        mCategoriesAdapter = new CategoriesAdapter(getActivity(), categories);
        categoriesList.setAdapter(mCategoriesAdapter);

        allButton.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), AllCatActivity.class);
            startActivity(intent);
        });
        return v;
    }

    private void setInitialData(){

        List<Course> data = DataLists.getData();

        if (data.size() >= 5) {
            for (int i = 0; i < 5; i++) {
                courses.add(data.get(i));
            }
        }
        else {
            for (int i = 0; i < data.size(); i++) {
                courses.add(data.get(i));
            }
        }

        List<Course> data2 = DataLists.getData2();

        if (data2.size() >= 5) {
            for (int i = 0; i < 5; i++) {
                courses2.add(data2.get(i));
            }
        }
        else {
            for (int i = 0; i < data2.size(); i++) {
                courses2.add(data2.get(i));
            }
        }

        List<Course> data3 = DataLists.getData3();

        if (data3.size() >= 5) {
            for (int i = 0; i < 5; i++) {
                courses3.add(data3.get(i));
            }
        }
        else {
            for (int i = 0; i < data3.size(); i++) {
                courses3.add(data3.get(i));
            }
        }

        List<Categories> catData = new ArrayList<>();
        Collections.addAll(catData, Categories.values());

        if (catData.size() >= 10) {
            for (int i = 0; i < 10; i++) {
                categories.add(catData.get(i));
            }
        }
        else {
            for (int i = 0; i < catData.size(); i++) {
                categories.add(catData.get(i));
            }
        }

    }

    @Override
    public void onItemClick(Course course) {
        Intent intent = new Intent(getActivity(), CourseActivity.class);
        intent.putExtra("key", course.getId());
        startActivity(intent);
    }
}
