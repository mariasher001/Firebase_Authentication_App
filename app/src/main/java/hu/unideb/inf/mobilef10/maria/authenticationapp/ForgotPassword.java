package hu.unideb.inf.mobilef10.maria.authenticationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import hu.unideb.inf.mobilef10.maria.authenticationapp.databinding.ActivityForgotPasswordBinding;

public class ForgotPassword extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
    }

    public void ResetLinkButtonClicked(View view) {
        String f_email = binding.ResetEmail.getText().toString();
        mAuth.sendPasswordResetEmail(f_email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ForgotPassword.this, "Link to the Email is Successfully sent", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ForgotPassword.this, "Email not found!Sorry Please try again!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}