package com.mrzhgn.pedia;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class RegistrationActivity extends AppCompatActivity {

    final String LOG_TAG = "Registration Activity";

    private EditText login;
    private EditText password;
    private EditText confirm;
    private EditText firstName;
    private EditText secondName;

    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    private int currentState;

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        mDb.close();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.registration_activity);

        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        confirm = (EditText) findViewById(R.id.confirm);
        firstName = (EditText) findViewById(R.id.first);
        secondName = (EditText) findViewById(R.id.second);

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

        findViewById(R.id.register).setOnClickListener(view -> {
            setState(addClient());
            if (currentState == RESULT_CANCELED) Toast.makeText(getApplicationContext(), "Ошибка в введённых данных", Toast.LENGTH_SHORT).show();
            else if (currentState == RESULT_OK) {
                setResult(RESULT_OK);
                mDb.close();
                finish();
            }
        });
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("current_state", getCurrentState());
        outState.putString("login", login.getText().toString());
        outState.putString("password", password.getText().toString());
        outState.putString("confirm", confirm.getText().toString());
        outState.putString("first", firstName.getText().toString());
        outState.putString("second", secondName.getText().toString());
        Log.d(LOG_TAG, "onSaveInstanceState");
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setCurrentState(savedInstanceState.getInt("current_state"));
        login.setText(savedInstanceState.getString("login"));
        password.setText(savedInstanceState.getString("password"));
        confirm.setText(savedInstanceState.getString("confirm"));
        firstName.setText(savedInstanceState.getString("first"));
        secondName.setText(savedInstanceState.getString("second"));
        Log.d(LOG_TAG, "onRestoreInstanceState");
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public boolean addClient() { //need checks for mDb existence
        if (!password.getText().toString().equals(confirm.getText().toString())) return false; //need to add regex to check login

        Cursor cursor = mDb.rawQuery("SELECT * FROM clients", null);
        ArrayList<String> clientData = new ArrayList<>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            clientData.add(cursor.getString(1));

            cursor.moveToNext();
        }

        cursor.close();
        if (clientData.contains(login.getText().toString())) return false;

        if (!login.getText().toString().toUpperCase().matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$")) return false;

        mDb.beginTransaction();
        insert(mDb, login.getText().toString(), password.getText().toString(), firstName.getText().toString(), secondName.getText().toString());
        mDb.setTransactionSuccessful();
        mDb.endTransaction();

        return true;
    }

    private void setState(boolean client) {
        if (client) currentState = RESULT_OK;
        else currentState = RESULT_CANCELED;
    }

    public void insert(SQLiteDatabase db, String mLogin, String mPassword, String mFirstName, String mSecondName) {
        if (!mFirstName.isEmpty() && !mSecondName.isEmpty()) {
            SQLiteStatement stmt = db.compileStatement("INSERT INTO clients(Login,Password_1,FirstName,SecondName) VALUES(?,?,?,?)");
            stmt.bindString(1, mLogin);
            stmt.bindString(2, mPassword);
            stmt.bindString(3, mFirstName);
            stmt.bindString(4, mSecondName);
            stmt.executeInsert();
        }
        else if (mFirstName.isEmpty() && !mSecondName.isEmpty()) {
            SQLiteStatement stmt = db.compileStatement("INSERT INTO clients(Login,Password_1,SecondName) VALUES(?,?,?)");
            stmt.bindString(1, mLogin);
            stmt.bindString(2, mPassword);
            stmt.bindString(3, mSecondName);
            stmt.executeInsert();
        }
        else if (!mFirstName.isEmpty() && mSecondName.isEmpty()) {
            SQLiteStatement stmt = db.compileStatement("INSERT INTO clients(Login,Password_1,FirstName) VALUES(?,?,?)");
            stmt.bindString(1, mLogin);
            stmt.bindString(2, mPassword);
            stmt.bindString(3, mFirstName);
            stmt.executeInsert();
        }
        else {
            SQLiteStatement stmt = db.compileStatement("INSERT INTO clients(Login,Password_1) VALUES(?,?)");
            stmt.bindString(1, mLogin);
            stmt.bindString(2, mPassword);
            stmt.executeInsert();
        }
    }

}
