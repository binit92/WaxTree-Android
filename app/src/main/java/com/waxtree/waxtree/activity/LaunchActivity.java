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

public class LaunchActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.editEmail)
    EditText editEmail;

    @BindView(R.id.editPassword)
    EditText editPassword;

    @BindView(R.id.btnSignUp)
    Button btnSignUp;

    @BindView(R.id.btnSignIn)
    Button btnSignIn;
/*
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;*/
    FirebaseTask firebaseTask ;
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


/*
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                }else{
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out:" );
                }
            }
        };

        // try automatic sign-in using saved credentials
        String savedEmail = SharedPrefs.getInstance(getApplicationContext()).getProperty(LOGIN_EMAIL);
        String savedPassword = SharedPrefs.getInstance(getApplicationContext()).getProperty(LOGIN_PASSWORD);
        if(savedEmail!= null && !savedEmail.isEmpty() && savedPassword != null && !savedPassword.isEmpty()) {
            signInExistingUser(savedEmail, savedPassword);
        }*/
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

/*
    private void signUpNewUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG,"createUserWithEmail:onComplete:" + task.isSuccessful());

                        //If sign in fails, display a message to the user, If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener
                        if(!task.isSuccessful()){
                            Toast.makeText(LaunchActivity.this, R.string.authFailed,Toast.LENGTH_SHORT).show();
                        }else{
                            openMainActivity();
                        }
                    }
                });
    }

    private void signInExistingUser(final String email, final String password){
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG,"signInWithEmail:onComplete:" + task.isSuccessful());

                        //If sign in fails, display a message to the user. If sign in succeeds
                        // the authe sate listener will be notified and logic to handle the
                        // signed in user can be handled in the listener
                        if(!task.isSuccessful()){
                            Log.w(TAG,"signInWithEmail:failed", task.getException());
                            Toast.makeText(LaunchActivity.this,R.string.authFailed, Toast.LENGTH_SHORT).show();
                        }else {

                            Toast.makeText(LaunchActivity.this,R.string.authSuccess,Toast.LENGTH_SHORT).show();
                            //On successful-login, save creds!
                            SharedPrefs.getInstance(getApplicationContext()).setProperty("loginEmail",email);
                            SharedPrefs.getInstance(getApplicationContext()).setProperty("loginPassword",password);
                            openMainActivity();
                        }
                    }
                });
    }


    private String accessSignedUserID(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = null;
        if(user != null){
            // Name, email address, and profile photo url
            // String name = user.getDisplayName();
            String email = user.getEmail();
            //Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            uid = user.getUid();
            Log.d(TAG,"email : "+ email + " , user-id :"+ uid);
        }
        return uid;
    }
*/

    @Override
    public void onClick(View view) {
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();


        if(email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
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
/*
    private void openMainActivity(){
        String uid = accessSignedUserID();
        if(uid != null) {

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("user-id", accessSignedUserID());
            startActivity(intent);
        }
    }*/


}
