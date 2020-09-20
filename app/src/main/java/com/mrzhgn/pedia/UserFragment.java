package com.mrzhgn.pedia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class UserFragment extends Fragment {

    private ImageView logout, userPhoto;
    private TextView username, userEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user, container, false);

        logout = (ImageView) v.findViewById(R.id.logout);
        userPhoto = (ImageView) v.findViewById(R.id.user_photo);
        username = (TextView) v.findViewById(R.id.username);
        userEmail = (TextView) v.findViewById(R.id.user_email);

        logout.setOnClickListener(view -> {
            SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
            SharedPreferences.Editor editor = sPref.edit();
            editor.putBoolean("isAuth", false);
            editor.putString("splash_login", "");
            editor.commit();
            Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        userEmail.setText(MainActivity.getLogin());

        return v;
    }
}
