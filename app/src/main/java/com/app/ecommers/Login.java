package com.app.ecommers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ecommers.Model.Users;
import com.app.ecommers.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

public class Login extends AppCompatActivity {
    private Button LoginButton;
    private EditText InputPhoneNumber, InputPassword;
    private ProgressDialog loadingBar;
    private String ParentsDbName = "user";
    private CheckBox RememberMe;
    private TextView AdminLink, NotAdminLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AdminLink = findViewById(R.id.admin_Panel_link);
        NotAdminLink = findViewById(R.id.not_admin_Panel_link);
        RememberMe = findViewById(R.id.remember_me_chk);
        InputPassword = findViewById(R.id.login_Password_input);
        InputPhoneNumber = findViewById(R.id.login_phone_number_input);
        LoginButton = findViewById(R.id.login_btn);
        loadingBar = new ProgressDialog(this);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton();
            }
        });
        Paper.init(this);
        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                ParentsDbName = "admins";
            }
        });

        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Login ");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                ParentsDbName = "user";
            }
        });
    }

    private void loginButton() {

        String Phone = InputPhoneNumber.getText().toString();
        String Password = InputPassword.getText().toString();
        if (TextUtils.isEmpty(Phone)) {

            Toast.makeText(this, "Please Enter The Number", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Password)) {

            Toast.makeText(this, "Please Enter The Password", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please Wait, We Are Checking the Credentials...!");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            AllowAccessToAccount(Phone, Password);
        }
    }

    private void AllowAccessToAccount(final String Phone, final String Password) {
        if (RememberMe.isChecked()) {

            Paper.book().write(Prevalent.UserPhoneKey, Phone);
            Paper.book().write(Prevalent.UserPasswordKey, Password);
        }


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(ParentsDbName).child(Phone).exists()) {

                    Users user = dataSnapshot.child(ParentsDbName).child(Phone).getValue(Users.class);
                    if (user.getPhone().equals(Phone)) {
                        if (user.getPassword().equals(Password)) {
                            if (ParentsDbName.equals("admins")) {
                                loadingBar.dismiss();
                                Toast.makeText(Login.this, "Welcome Admin You Are Logged In SuccessFully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Login.this, AdminCategoryActivity.class);
                                startActivity(i);
                            } else if (ParentsDbName.equals("user")) {
                                loadingBar.dismiss();
                                Toast.makeText(Login.this, "Logged In SuccessFully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Login.this, HomeActivity.class);
                                startActivity(i);

                            }
                        } else {
                            Toast.makeText(Login.this, "Password Is Incorrect ", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                } else {
                    Toast.makeText(Login.this, "Account With This" + Phone + "Does Not Exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}

