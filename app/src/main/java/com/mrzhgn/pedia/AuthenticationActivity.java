package com.mrzhgn.pedia;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AuthenticationActivity extends AppCompatActivity {

    private EditText login;
    private EditText password;

    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    private int currentState;

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Необходимо войти в аккаунт", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.authentication_activity);

        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);

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

        findViewById(R.id.entry).setOnClickListener(view -> {
            setState(getClients());
            if (currentState == RESULT_CANCELED) Toast.makeText(getApplicationContext(), "Неправильный логин или пароль", Toast.LENGTH_SHORT).show();
            else if (currentState == RESULT_OK) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.putExtra("login", login.getText().toString());
                startActivity(intent);
            }
        });

        findViewById(R.id.registration).setOnClickListener(view -> {
            //mDb.close();
            //mDBHelper.close();
            startActivityForResult(new Intent(view.getContext(), RegistrationActivity.class), RequestCode.REQUEST_CODE_REGISTRATION);
        });
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("current_state", getCurrentState());
        outState.putString("login", login.getText().toString());
        outState.putString("password", password.getText().toString());
        Log.d("Authentication Activity", "onSaveInstanceState");
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setCurrentState(savedInstanceState.getInt("current_state"));
        login.setText(savedInstanceState.getString("login"));
        password.setText(savedInstanceState.getString("password"));
//        display(quantity);
        Log.d("Authentication Activity", "onRestoreInstanceState");
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    private ArrayList<HashMap<String, Object>> getClients() {
        ArrayList<HashMap<String, Object>> clients = new ArrayList<>();
        HashMap<String, Object> clientData;
        Cursor cursor = mDb.rawQuery("SELECT * FROM clients", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            clientData = new HashMap<String, Object>();

            clientData.put("login",  cursor.getString(1));
            clientData.put("password",  cursor.getString(2));

            clients.add(clientData);

            cursor.moveToNext();
        }

        cursor.close();

        return clients;
    }

    private void setState(ArrayList<HashMap<String, Object>> clients) {
        for (HashMap<String, Object> clientData : clients) {
            if (login.getText().toString().equals(clientData.get("login"))) {
                if (password.getText().toString().equals(clientData.get("password"))) {
                    currentState = RESULT_OK;
                    return;
                }
            }
        }

        currentState = RESULT_CANCELED;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Cursor cursor = mDb.rawQuery("SELECT * FROM clients", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Log.d("CLIENTS", cursor.getString(1));
            Log.d("CLIENTS", cursor.getString(2));
            if (cursor.getString(4) != null) Log.d("CLIENTS", cursor.getString(4));
            if (cursor.getString(5) != null) Log.d("CLIENTS", cursor.getString(5));

            cursor.moveToNext();
        }

        cursor.close();
    }

}
