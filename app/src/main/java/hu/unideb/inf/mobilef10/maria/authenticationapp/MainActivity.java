package hu.unideb.inf.mobilef10.maria.authenticationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import hu.unideb.inf.mobilef10.maria.authenticationapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

    }

    public void loginButtonClicked(View view) {
        String email = binding.emailAddress.getText().toString();
        String password = binding.password.getText().toString();

        if(email.isEmpty()){
            binding.emailAddress.setError("Email cannot be Empty");
            binding.emailAddress.requestFocus();
            return;
        }
        if(password.isEmpty()){
            binding.password.setError("Password cannot be Empty");
            binding.password.requestFocus();
            return;
        }

        binding.progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            binding.progressBar.setVisibility(View.GONE);
                            userProfile();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Failed to Login!! Please Check Again!", Toast.LENGTH_SHORT).show();
                            binding.progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }

    private void userProfile() {
        Intent intent = new Intent(this,AfterLogin.class);
        startActivity(intent);

    }

    public void registerTextViewClicked(View view) {
        Intent intent = new Intent(this,RegisterUser.class);
        startActivity(intent);
    }

}