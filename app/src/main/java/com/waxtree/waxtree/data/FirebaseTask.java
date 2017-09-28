package com.waxtree.waxtree.data;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.waxtree.waxtree.R;
import com.waxtree.waxtree.activity.MainActivity;
import com.waxtree.waxtree.pojo.Project;
import com.waxtree.waxtree.pojo.ProjectAttribute;
import com.waxtree.waxtree.util.ICompletionCallback;
import com.waxtree.waxtree.util.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inbkumar01 on 9/28/2017.
 *
 *  Firebase Authentication and Real Time Database
 */

public class FirebaseTask {


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mProjectsRef = null;

    public static List<Project> allProjects = new ArrayList<>();

    private final String TAG = FirebaseTask.class.getSimpleName();
    private static final String LOGIN_EMAIL = "loginEmail";
    private static final String LOGIN_PASSWORD = "loginPassword";

    Context context;

    private FirebaseTask(Context context){
        this.context = context;
        init();
    }

    public static FirebaseTask getInstance(Context context){
        return new FirebaseTask(context);
    }

    public void init(){

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


        mProjectsRef = firebaseDatabase.getReference().child("projects");
    }

    public void addAuthListener(){
        if(mAuth != null) {
            mAuth.addAuthStateListener(mAuthListener);
        }
    }

    public void removeAuthListener(){
        if(mAuth != null) {
            if (mAuthListener != null) {
                mAuth.removeAuthStateListener(mAuthListener);
            }
        }
    }

    public void signUpNewUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG,"createUserWithEmail:onComplete:" + task.isSuccessful());

                        //If sign in fails, display a message to the user, If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener
                        if(!task.isSuccessful()){
                            Toast.makeText(context, R.string.authFailed,Toast.LENGTH_SHORT).show();
                        }else{
                            openMainActivity();
                        }
                    }
                });
    }

    public void signInExistingUser(final String email, final String password){
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG,"signInWithEmail:onComplete:" + task.isSuccessful());

                        //If sign in fails, display a message to the user. If sign in succeeds
                        // the authe sate listener will be notified and logic to handle the
                        // signed in user can be handled in the listener
                        if(!task.isSuccessful()){
                            Log.w(TAG,"signInWithEmail:failed", task.getException());
                            Toast.makeText(context, R.string.authFailed, Toast.LENGTH_SHORT).show();
                        }else {

                            Toast.makeText(context,R.string.authSuccess,Toast.LENGTH_SHORT).show();
                            //On successful-login, save creds!
                            SharedPrefs.getInstance(context).setProperty("loginEmail",email);
                            SharedPrefs.getInstance(context).setProperty("loginPassword",password);
                            openMainActivity();
                        }
                    }
                });
    }

    public void tryAutomaticSignIn(){
        // try automatic sign-in using saved credentials
        String savedEmail = SharedPrefs.getInstance(context).getProperty(LOGIN_EMAIL);
        String savedPassword = SharedPrefs.getInstance(context).getProperty(LOGIN_PASSWORD);
        if(savedEmail!= null && !savedEmail.isEmpty() && savedPassword != null && !savedPassword.isEmpty()) {
            signInExistingUser(savedEmail, savedPassword);
        }
    }

    private void openMainActivity(){
        String uid = accessSignedUserID();
        if(uid != null) {

            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("user-id", accessSignedUserID());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
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


    public void getProjectsFromDB(final ICompletionCallback completionCallback){
        mProjectsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and gain
                // whenever data at this location is updated

                for (DataSnapshot projectClassDataSnapshot : dataSnapshot.getChildren()) {
                    String projectName = projectClassDataSnapshot.getKey();
                    ProjectAttribute projectAttribute = projectClassDataSnapshot.getValue(ProjectAttribute.class);
                    allProjects.add(new Project(projectName,projectAttribute));
                }
                if(completionCallback != null) {
                    completionCallback.onCompletionCallback(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG,"Failed to read value.", databaseError.toException());

            }

        });
    }
}
