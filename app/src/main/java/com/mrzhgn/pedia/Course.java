package com.mrzhgn.pedia;

import java.util.ArrayList;
import java.util.List;

public class Course {

    private static int num = 0;

    private int image;
    private String title;
    private String cost;
    private List<Categories> categories;
    private int id;

    public Course(int image, String title, String cost){

        this.image = image;
        this.title = title;
        this.cost = cost;
        categories = new ArrayList<>();
        num++;
        id = num;
        DataLists.addToHashMap(this);
    }

    public Course(int image, String title, String cost, List<Categories> categories){

        this.image = image;
        this.title = title;
        this.cost = cost;
        this.categories = categories;
        num++;
        id = num;
        DataLists.addToHashMap(this);

    }

    public int getImage() {
        return this.image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCost() {
        return this.cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    public void addCategory(Categories category) {
        categories.add(category);
    }

    public int getId() {
        return id;
    }
}
