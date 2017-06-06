package co.syntax.firebasedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import co.syntax.firebasedemo.base.BaseActivity;
import co.syntax.firebasedemo.model.User;
import co.syntax.firebasedemo.prefs.UserPrefs;
import co.syntax.firebasedemo.util.Constants;

/**
 * Created by rukarayan on 19-Jan-17.
 */

public class SignInActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = "SignInActivity";
    private EditText etEmail, etPassword;
    private Button btnSend;
    private TextView tvCreateAccount;
    private DatabaseReference rRef;
    private FirebaseAuth rAuth;
    private UserPrefs prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = new UserPrefs(this);
        if (prefs.isUserLogin(UserPrefs.USER_LOGIN_KEY)) {
            goToMainActivity();
        }
        setContentView(R.layout.activity_sign_in);

        initialiseViews();
        rRef = FirebaseDatabase.getInstance().getReference();
        rAuth = FirebaseAuth.getInstance();


    }

    private void initialiseViews() {
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnSend = (Button) findViewById(R.id.btnSend);
        tvCreateAccount = (TextView) findViewById(R.id.tvCreateAccount);
        btnSend.setOnClickListener(this);
        tvCreateAccount.setOnClickListener(this);
    }

    private void singIn() {
        showProgressDialog();
        String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();

        rAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser(), password);
                        }

                    }
                })

                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, e.getMessage().toString());
                        Toast.makeText(SignInActivity.this, "not Success--" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void signUp() {
        showProgressDialog();
        String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();

        rAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            Toast.makeText(SignInActivity.this, "User Created Successfully",
                                    Toast.LENGTH_SHORT).show();
                        } else {

                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignInActivity.this, e.getMessage().toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void onAuthSuccess(FirebaseUser user, String password) {
        String userName = userNameFromEmail(user.getEmail());
        writeNewUser(user.getUid(), userName, user.getEmail(), password);

    }

    protected String userNameFromEmail(String email) {
        if (email.contains("@")) {
            String username = email.split("@")[0];
            return "@" + username;
        } else {
            return email;
        }
    }

    private boolean isValid() {
        boolean isValid = true;

        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            isValid = false;
            etEmail.setError("required");
        } else {
            etEmail.setError(null);
        }

        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            isValid = false;
            etPassword.setError("required");
        } else {
            etPassword.setError(null);
        }

        return isValid;
    }

    private void writeNewUser(String userId, String userName, String email, String password) {
        User user = new User(userId, userName, email, password, Constants.imageUrl);
        rRef.child("users").child(userId).setValue(user);
        prefs.putObject(UserPrefs.USER_TOKEN_KEY, user);
        prefs.putIsUserLogin(UserPrefs.USER_LOGIN_KEY, true);
        goToMainActivity();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnSend) {
            if (isValid()) {
                singIn();
            }
        } else if (id == R.id.tvCreateAccount) {
            if (isValid()) {
                signUp();
            }
        }
    }
}
