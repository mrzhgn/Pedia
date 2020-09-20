package com.mrzhgn.pedia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigation;
    private ImageView basket;

    SharedPreferences sPref;
    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    final Fragment fragment1 = new RecomendationsFragment();
    final Fragment fragment2 = new SearchFragment();
    final Fragment fragment3 = new CourseFragment();
    final Fragment fragment4 = new FavouriteFragment();
    final Fragment fragment5 = new UserFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;

    private static String login;
    private List<Integer> myInfo, favInfo;

    private final static String SHARED_PREFERENCES_KEY_USER_COURSES_MAP = "User_Courses_Map";
    private final static String SHARED_PREFERENCES_KEY_FAV_COURSES_MAP = "Fav_Courses_Map";
    private final static String SHARED_PREFERENCES_KEY_ALL_COURSES_MAP = "All_Courses_Map";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_recomendations:
                    if (active != fragment1) {
                        fm.beginTransaction().hide(active).show(fragment1).addToBackStack("1").detach(fragment1).attach(fragment1).commit();
                        active = fragment1;
                    }
                    return true;
                case R.id.navigation_search:
                    if (active != fragment2) {
                        fm.beginTransaction().hide(active).show(fragment2).addToBackStack("2").detach(fragment2).attach(fragment2).commit();
                        active = fragment2;
                    }
                    return true;
                case R.id.navigation_course:
                    if (active != fragment3) {
                        fm.beginTransaction().hide(active).show(fragment3).addToBackStack("3").detach(fragment3).attach(fragment3).commit();
                        active = fragment3;
                    }
                    return true;
                case R.id.navigation_favourite:
                    if (active != fragment4) {
                        fm.beginTransaction().hide(active).show(fragment4).addToBackStack("4").detach(fragment4).attach(fragment4).commit();
                        active = fragment4;
                    }
                    return true;
                case R.id.navigation_user:
                    if (active != fragment5) {
                        fm.beginTransaction().hide(active).show(fragment5).addToBackStack("5").detach(fragment5).attach(fragment5).commit();
                        active = fragment5;
                    }
                    return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Intent intent = getIntent();
        login = intent.getStringExtra("login");

        myInfo = new ArrayList<>();
        favInfo = new ArrayList<>();

        sPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sPref.edit();
        editor.putBoolean("isAuth", true);
        editor.putString("splash_login", login);
        editor.commit();
        boolean isFirstLaunch = sPref.getBoolean("firstLaunch", true);
        if (!isFirstLaunch) {
            loadLists();

            List<Course> myList = new ArrayList<>();
            for (Integer id : myInfo) {
                myList.add(DataLists.findCourseById(id));
            }
            DataLists.setMyCourse(myList);

            List<Course> favList = new ArrayList<>();
            for (Integer id : favInfo) {
                favList.add(DataLists.findCourseById(id));
            }
            DataLists.setFavCourse(favList);

        }
        else {
            mDBHelper = new DataBaseHelper(this);

            try {
                mDBHelper.updateDataBase();
            } catch (IOException mIOException) {
                throw new Error("UnableToUpdateDatabase");
            }

            try {
                mDb = mDBHelper.getWritableDatabase();
            } catch (SQLException mSQLException) {
                throw mSQLException;
            }

            setInfo();
        }

        basket = (ImageView) findViewById(R.id.basket);
        basket.setOnClickListener(v -> {
            Intent basketIntent = new Intent(v.getContext(), BasketActivity.class);
            startActivity(basketIntent);
        });

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fm.beginTransaction().add(R.id.fragment_container, fragment5, "5").hide(fragment5).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment1, "1").addToBackStack("1").commit();
    }

    @Override
    public void onBackPressed() {
        if (fm.getBackStackEntryCount() > 1) {
            super.onBackPressed();
            int index = fm.getBackStackEntryCount();
            FragmentManager.BackStackEntry backStackEntry = fm.getBackStackEntryAt(index - 1);
            String tag = backStackEntry.getName();
            Fragment fragment = fm.findFragmentByTag(tag);
            active = fragment;
            navigation.getMenu().getItem(Integer.parseInt(tag) - 1).setChecked(true);
        }
        else {
            saveLists();
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveLists();
    }


    public static String getLogin() {
        return login;
    }

    private void saveLists() {
        HashMap<String, List<Integer>> myList = DataLists.getMyInfo();
        HashMap<String, List<Integer>> favList = DataLists.getFavInfo();

        Gson gson = new Gson();
        String myInfo = gson.toJson(myList);
        String favInfo = gson.toJson(favList);

        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(SHARED_PREFERENCES_KEY_USER_COURSES_MAP, myInfo);
        editor.putString(SHARED_PREFERENCES_KEY_FAV_COURSES_MAP, favInfo);
        editor.putBoolean("firstLaunch", false);
        editor.commit();
        //Toast.makeText(this, "!!!", Toast.LENGTH_SHORT).show();
//        List<Course> courses = DataLists.getMyCourse();
//        for (Course c : courses) Log.d("!!!", c.getTitle());
//        HashMap<String, List<Integer>> map = DataLists.getMyInfo();
//        for (Map.Entry<String, List<Integer>> pair : map.entrySet()) {
//            //if (pair.getKey().equals(login)) {
//                if (login == null) Log.d("!!!", "NULL");
//            //}
//        }
    }

    private void loadLists() {
        String myList = sPref.getString(SHARED_PREFERENCES_KEY_USER_COURSES_MAP, "");
        String favList = sPref.getString(SHARED_PREFERENCES_KEY_FAV_COURSES_MAP, "");

        Gson gson = new Gson();

        Type type = new TypeToken<HashMap<String, List<Integer>>>(){}.getType();
        HashMap<String, List<Integer>> clonedMap = gson.fromJson(myList, type);
        HashMap<String, List<Integer>> clonedMap2 = gson.fromJson(favList, type);

        //int r = 0;
        for (Map.Entry<String, List<Integer>> pair : clonedMap.entrySet()) {
            //r++;
            if (pair.getKey().equals(login)) {
                myInfo = pair.getValue();
//                Toast.makeText(getApplicationContext(), myInfo.size(), Toast.LENGTH_SHORT).show();
                break;
            }
        }
        //Toast.makeText(getApplicationContext(), new Integer(r).toString(), Toast.LENGTH_SHORT).show();

        for (Map.Entry<String, List<Integer>> pair : clonedMap2.entrySet()) {
            if (pair.getKey().equals(login)) {
                favInfo = pair.getValue();
                break;
            }
        }

        DataLists.setMyInfo(clonedMap);
        DataLists.setFavInfo(clonedMap2);
        DataLists.setInitialData();
        //Toast.makeText(this, "!!!", Toast.LENGTH_SHORT).show();
    }

    private void setInfo() {
        ArrayList<String> clients = new ArrayList<>();
        HashMap<String, Object> clientData;
        Cursor cursor = mDb.rawQuery("SELECT * FROM clients", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            clientData = new HashMap<String, Object>();

            clientData.put("login",  cursor.getString(1));
            clientData.put("password",  cursor.getString(2));

            clients.add(clientData.get("login").toString());
            Log.d("!!!", clientData.get("login").toString());

            cursor.moveToNext();
        }

        cursor.close();

        HashMap<String, List<Integer>> myData = new HashMap<>();
        HashMap<String, List<Integer>> favData = new HashMap<>();
        for (String client : clients) {
            myData.put(client, new ArrayList<>());
            favData.put(client, new ArrayList<>());
        }
        DataLists.setMyInfo(myData);
        DataLists.setFavInfo(favData);
        myInfo = myData.get(login);
        favInfo = favData.get(login);
    }
}
