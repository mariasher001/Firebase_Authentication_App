package hu.unideb.inf.mobilef10.maria.authenticationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import hu.unideb.inf.mobilef10.maria.authenticationapp.databinding.ActivityRegisterUserBinding;

public class RegisterUser extends AppCompatActivity {

    private ActivityRegisterUserBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mReal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

    }

    public void registerButtonClicked(View view) {

        String fullName = binding.fullName.getText().toString();
        String email = binding.emailAddress.getText().toString();
        String password = binding.password.getText().toString();

        if(fullName.isEmpty()){
            binding.fullName.setError("Full-Name is Required!");
            binding.fullName.requestFocus();
            return;
        }

        if(email.isEmpty()){
            binding.emailAddress.setError("Email-Address is Required!");
            binding.emailAddress.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailAddress.setError("Please Enter correct Email-Address");
            binding.emailAddress.requestFocus();
            return;
        }

        if(password.isEmpty()){
            binding.password.setError("Password is Required!");
            binding.password.requestFocus();
            return;
        }

        if(password.length()<6){
            binding.password.setError("Password should be min 6 characters");
            binding.password.requestFocus();
            return;
        }

        //register in database
        binding.progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(fullName,email);
                            realTime(user);
                        }
                        else{
                            Toast.makeText(RegisterUser.this, "Failed to Register,Try Again!!", Toast.LENGTH_SHORT).show();
                            binding.progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }

    private void realTime(User user) {
        mReal = FirebaseDatabase.getInstance();
        mReal.getReference("Users")      //Table-Name
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())              //Primary key from Authentication Database as RowNumber
                .setValue(user)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterUser.this, "User has been Registered Successfully :)", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(RegisterUser.this, "Failed to Register!!Try Again!!", Toast.LENGTH_SHORT).show();
                    }
                    binding.progressBar.setVisibility(View.GONE);
                });
    }
}