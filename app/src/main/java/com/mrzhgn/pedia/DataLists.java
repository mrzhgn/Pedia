package com.mrzhgn.pedia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DataLists {

    private static List<Course> data;
    private static List<Course> data2;
    private static List<Course> data3;

    private static List<Course> favCourse;
    private static List<Course> basketCourse;
    private static List<Course> myCourse;

    private static HashMap<Integer, Course> allCourses;
    private static HashMap<String, List<Integer>> myInfo, favInfo;

    static {
        data = new ArrayList<>();
        data2 = new ArrayList<>();
        data3 = new ArrayList<>();
        favCourse = new ArrayList<>();
        basketCourse = new ArrayList<>();
        myCourse = new ArrayList<>();
        allCourses = new HashMap<>();
        myInfo = new HashMap<>();
        favInfo = new HashMap<>();

        setInitialData();
    }

    public static void setInitialData() {
        data.add(new Course (R.drawable.course, "Learn Python from Scratch", "$9.99", Arrays.asList(Categories.IT, Categories.SKILL, Categories.BEGINNER)));
        data.add(new Course (R.drawable.course, "Advanced Java", "$9.99", Arrays.asList(Categories.IT, Categories.SKILL, Categories.ADVANCED)));
        data.add(new Course (R.drawable.course, "Ethical Hacking for Beginners", "$9.99", Arrays.asList(Categories.IT, Categories.SKILL, Categories.BEGINNER)));
        data.add(new Course (R.drawable.course, "Cisco ICND1&ICND2 Exams", "$9.99", Arrays.asList(Categories.IT, Categories.CERTIFICATION, Categories.INTERMEDIATE)));
        data.add(new Course (R.drawable.course, "Complete Linux Bootcamp", "$9.99", Arrays.asList(Categories.IT, Categories.SKILL, Categories.BEGINNER, Categories.INTERMEDIATE, Categories.ADVANCED)));
        data2.add(new Course (R.drawable.course, "Microsoft Excel Beginnner2Advanced", "$9.99", Arrays.asList(Categories.BUSINESS, Categories.SKILL, Categories.BEGINNER, Categories.INTERMEDIATE, Categories.ADVANCED)));
        data2.add(new Course (R.drawable.course, "Microsoft Word Beginnner2Advanced", "$9.99", Arrays.asList(Categories.BUSINESS, Categories.SKILL, Categories.BEGINNER, Categories.INTERMEDIATE, Categories.ADVANCED)));
        data2.add(new Course (R.drawable.course, "Microsoft PowerPoint Beginnner2Advanced", "$9.99", Arrays.asList(Categories.BUSINESS, Categories.SKILL, Categories.BEGINNER, Categories.INTERMEDIATE, Categories.ADVANCED)));
        data2.add(new Course (R.drawable.course, "Microsoft Outlook Beginnner2Advanced", "$9.99", Arrays.asList(Categories.BUSINESS, Categories.SKILL, Categories.BEGINNER, Categories.INTERMEDIATE, Categories.ADVANCED)));
        data2.add(new Course (R.drawable.course, "Microsoft OneNote Beginnner2Advanced", "$9.99", Arrays.asList(Categories.BUSINESS, Categories.SKILL, Categories.BEGINNER, Categories.INTERMEDIATE, Categories.ADVANCED)));
        data3.add(new Course (R.drawable.course, "Become Guitar God", "$9.99", Arrays.asList(Categories.MUSIC, Categories.SKILL, Categories.BEGINNER)));
        data3.add(new Course (R.drawable.course, "Become Drums God", "$9.99", Arrays.asList(Categories.MUSIC, Categories.SKILL, Categories.BEGINNER)));
        data3.add(new Course (R.drawable.course, "Become Vocals God", "$9.99", Arrays.asList(Categories.MUSIC, Categories.SKILL, Categories.BEGINNER)));
        data3.add(new Course (R.drawable.course, "Become Piano God", "$9.99", Arrays.asList(Categories.MUSIC, Categories.SKILL, Categories.BEGINNER)));
        data3.add(new Course (R.drawable.course, "Become Bass God", "$9.99", Arrays.asList(Categories.MUSIC, Categories.SKILL, Categories.BEGINNER)));
    }

    public static List<Course> getData() {
        return data;
    }

    public static void setData(List<Course> data) {
        DataLists.data = data;
    }

    public static void addData(Course course) {
        data.add(course);
    }

    public static List<Course> getData2() {
        return data2;
    }

    public static void setData2(List<Course> data2) {
        DataLists.data2 = data2;
    }

    public static void addData2(Course course) {
        data2.add(course);
    }

    public static List<Course> getData3() {
        return data3;
    }

    public static void setData3(List<Course> data3) {
        DataLists.data3 = data3;
    }

    public static void addData3(Course course) {
        data3.add(course);
    }

    public static List<Course> getFavCourse() {
        return favCourse;
    }

    public static void setFavCourse(List<Course> favCourse) {
        DataLists.favCourse = favCourse;
    }

    public static void addFavCourse(Course course) {
        favCourse.add(course);
        if (MainActivity.getLogin() != null) addFavCourseToLogin(MainActivity.getLogin(), course);
    }

    public static List<Course> getBasketCourse() {
        return basketCourse;
    }

    public static void addBasketCourse(Course course) {
        basketCourse.add(course);
    }
    public static void removeBasketCourse(Course course) {
        if (basketCourse.contains(course)) {
            basketCourse.remove(course);
        }
    }

    public static void setBasketCourse(List<Course> basketCourse) {
        DataLists.basketCourse = basketCourse;
    }

    public static List<Course> getMyCourse() {
        return myCourse;
    }

    public static void setMyCourse(List<Course> myCourse) {
        DataLists.myCourse = myCourse;
    }

    public static void addMyCourse(Course course) {
        myCourse.add(course);
        if (MainActivity.getLogin() != null) addMyCourseToLogin(MainActivity.getLogin(), course);
    }

    public static HashMap<Integer, Course> getAllCourses() {
        return allCourses;
    }

    public static void setAllCourses(HashMap<Integer, Course> allCourses) {
        DataLists.allCourses = allCourses;
    }

    public static void addToHashMap (Course course) {
        allCourses.put(course.getId(), course);
    }

    public static Course findCourseById(int id) {
        for (Map.Entry<Integer, Course> pair : allCourses.entrySet()) {
            if (Objects.equals(pair.getKey(), id)) return pair.getValue();
        }
        return null;
    }

    public static HashMap<String, List<Integer>> getMyInfo() {
        return myInfo;
    }

    public static void setMyInfo(HashMap<String, List<Integer>> myInfo) {
        DataLists.myInfo = myInfo;
    }

    public static HashMap<String, List<Integer>> getFavInfo() {
        return favInfo;
    }

    public static void setFavInfo(HashMap<String, List<Integer>> favInfo) {
        DataLists.favInfo = favInfo;
    }

    public static void addMyCourseToLogin(String login, Course c) {
        for (Map.Entry<String, List<Integer>> pair : myInfo.entrySet()) {
            if (pair.getKey().equals(login)) pair.getValue().add(c.getId());
        }
    }

    public static void addFavCourseToLogin(String login, Course c) {
        for (Map.Entry<String, List<Integer>> pair : favInfo.entrySet()) {
            if (pair.getKey().equals(login)) pair.getValue().add(c.getId());
        }
    }
}
