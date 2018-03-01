package com.ponduri.lab3;

import android.app.AuthenticationRequiredException;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

  private Button buttonSignUp;
  private EditText editTextEmail;
  private EditText editTextPassword;
  private TextView textViewSignUp;

  private FirebaseAuth firebaseAuth;
  private ProgressDialog progressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    firebaseAuth = FirebaseAuth.getInstance();
    if(firebaseAuth.getCurrentUser() != null){

      finish();
      startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
    }
    buttonSignUp = (Button) findViewById(R.id.butttonSignin);

    editTextEmail = (EditText) findViewById(R.id.editTextEmail);
    editTextPassword = (EditText) findViewById(R.id.editTextPassword);


    textViewSignUp = (TextView) findViewById(R.id.textViewSignin);

    progressDialog = new ProgressDialog(this);

    buttonSignUp.setOnClickListener(this);
    textViewSignUp.setOnClickListener(this);

  }

  private void userLogin(){
      String email = editTextEmail.getText().toString().trim();
      String password = editTextPassword.getText().toString().trim();


    if(TextUtils.isEmpty(email)){
      Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
      return;
    }
    if(TextUtils.isEmpty(password)){
      Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
      return;
    }

    progressDialog.setMessage("Registering User...");
    progressDialog.show();

    firebaseAuth.signInWithEmailAndPassword(email, password)
          .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              progressDialog.dismiss();
              if (task.isSuccessful()) {

                finish();
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
              }

            }

          });


  }

  @Override
  public void onClick(View view) {
    if(view == buttonSignUp){
      userLogin();
    }

    if (view == textViewSignUp){
      finish();
      startActivity(new Intent(this, MainActivity.class));
    }
  }
}
