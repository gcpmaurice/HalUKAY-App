package com.example.halukay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    EditText editEmail, editPassword, editFirstName, editLastName, editNumber, editAddress;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editEmail = findViewById(R.id.editRegEmail);
        editPassword = findViewById(R.id.editRegPassword);
        editFirstName = findViewById(R.id.editRegFirstName);
        editLastName = findViewById(R.id.editRegLastName);
        editNumber = findViewById(R.id.editRegNumber);
        editAddress = findViewById(R.id.editRegAddress);

        mAuth = FirebaseAuth.getInstance();
    }


    public void signup(View view) {
        String email, password, firstname, lastname, number, address;

        email = editEmail.getText().toString().trim();
        password = editPassword.getText().toString().trim();
        firstname = editFirstName.getText().toString().trim();
        lastname = editLastName.getText().toString().trim();
        number = editNumber.getText().toString().trim();
        address = editAddress.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(email, firstname, lastname, number, address);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(SignUp.this, "Sign Up Successful", Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(SignUp.this, "Sign Up Failed", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(SignUp.this, "Sign Up Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}