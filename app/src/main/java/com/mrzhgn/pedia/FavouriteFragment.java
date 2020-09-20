package com.mrzhgn.pedia;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment implements FavDataAdapter.OnFavItemListener {

    private RecyclerView myList;
    private FavDataAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    List<Course> courses;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favourite, container, false);
        myList = (RecyclerView) v.findViewById(R.id.my_fav_list);
        courses = new ArrayList<>();
        setInitialData();
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        myList.setLayoutManager(layoutManager);
        mAdapter = new FavDataAdapter(getActivity(), courses, this);
        myList.setAdapter(mAdapter);
        return v;
    }

    private void setInitialData(){
        courses = DataLists.getFavCourse();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), CourseActivity.class);
        intent.putExtra("key", position);
        startActivity(intent);
    }
}
