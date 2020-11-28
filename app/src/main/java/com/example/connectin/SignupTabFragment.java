package com.example.connectin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupTabFragment extends Fragment {

    EditText enteremail;
    EditText enterpass;
    EditText confirmPass;
    Button register;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    float v = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);

        enteremail = root.findViewById(R.id.enteremail);
        enterpass = root.findViewById(R.id.enterpassword);
        confirmPass = root.findViewById(R.id.confirmpassword);
        register = root.findViewById(R.id.register);
        progressBar = root.findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();

        enteremail.setTranslationX(800);
        enterpass.setTranslationX(800);
        confirmPass.setTranslationX(800);
        register.setTranslationX(800);

        enteremail.animate().translationX(0).alpha(1).setDuration(1500).setStartDelay(400).start();
        enterpass.animate().translationX(0).alpha(1).setDuration(1500).setStartDelay(500).start();
        confirmPass.animate().translationX(0).alpha(1).setDuration(1500).setStartDelay(700).start();
        register.animate().translationX(0).alpha(1).setDuration(1500).setStartDelay(900).start();

        //if user has already registered
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }

        //on clicking register button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = enteremail.getText().toString().trim();
                String password = enterpass.getText().toString().trim();
                String confpassword = confirmPass.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    enteremail.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    enterpass.setError("Password is Required");
                    return;
                }
                if (TextUtils.isEmpty(confpassword)) {
                    confirmPass.setError("Enter password one more time here");
                    return;
                }
                if(password.length() < 6) {
                    enterpass.setError("Password Must be >= 6 Characters");
                    return;
                }
                if(!(confpassword.equals(password))){
                    confirmPass.setText("");
                    confirmPass.setError("Wrong password");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "User Created.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();

                        } else {
                            Toast.makeText(getActivity(), "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        return root;
    }
}
