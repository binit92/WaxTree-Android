package com.waxtree.waxtree.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.waxtree.waxtree.R;
import com.waxtree.waxtree.data.FirebaseTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by inbkumar01 on 9/24/2017.
 */

public class LaunchActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.editEmail)
    EditText editEmail;

    @BindView(R.id.editPassword)
    EditText editPassword;

    @BindView(R.id.btnSignUp)
    Button btnSignUp;

    @BindView(R.id.btnSignIn)
    Button btnSignIn;

    FirebaseTask firebaseTask;
    private final String TAG = LaunchActivity.class.getSimpleName();
    private static final String LOGIN_EMAIL = "loginEmail";
    private static final String LOGIN_PASSWORD = "loginPassword";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_activity);
        ButterKnife.bind(this);
        firebaseTask = FirebaseTask.getInstance(getApplicationContext());

        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        firebaseTask.tryAutomaticSignIn();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseTask.addAuthListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseTask.removeAuthListener();
    }

    @Override
    public void onClick(View view) {
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();


        if (email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
            switch (view.getId()) {
                case R.id.btnSignUp:
                    firebaseTask.signUpNewUser(editEmail.getText().toString(), editPassword.getText().toString());
                    break;

                case R.id.btnSignIn:
                    firebaseTask.signInExistingUser(editEmail.getText().toString(), editPassword.getText().toString());
                    break;
            }
        }
    }


}
