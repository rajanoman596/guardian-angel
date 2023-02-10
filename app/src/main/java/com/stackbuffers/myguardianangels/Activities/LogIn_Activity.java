package com.stackbuffers.myguardianangels.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.stackbuffers.myguardianangels.App;
import com.stackbuffers.myguardianangels.Models.Login_Response;
import com.stackbuffers.myguardianangels.Models.User;
import com.stackbuffers.myguardianangels.R;
import com.stackbuffers.myguardianangels.RetrofitClasses.RetrofitClientInstance;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogIn_Activity extends AppCompatActivity {
    Button login_btn;
    TextView get_new, sign_up;
    EditText email_et;
    TextInputEditText password_et;
    List<User> users = new ArrayList<>();
    ImageView google_img;
    int RC_SIGN_IN = 0;
    LoginButton login_button;
    private CallbackManager c;
    TextView e;
    GoogleSignInClient mGoogleSignInClient;

    LoginButton loginButton;
    ProgressBar progressBar;


    private static final String EMAIL = "email";

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 120) {
            if (grantResults.length > 0 && grantResults[2] != PackageManager.PERMISSION_GRANTED) {

                new AlertDialog.Builder(this)
                        .setTitle("Location permission required")
                        .setMessage("Please allow location permissions to continue?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                finishAffinity();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        // .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


                //Toast.makeText(this,"Location permissions required",Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        verifyUserLogIn();
        get_new = findViewById(R.id.get_new);
        sign_up = findViewById(R.id.sign_up);
        login_btn = findViewById(R.id.login_btn);
        progressBar = findViewById(R.id.progress_bar);

        requestPermissions();

        findViewById(R.id.facebook_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();
            }
        });

        e = findViewById(R.id.e);

        c = CallbackManager.Factory.create();


        email_et = findViewById(R.id.email_et);
        password_et = findViewById(R.id.password_et);

        google_img = findViewById(R.id.google_img);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        fbLogin();
//        login_button.setPermissions(Arrays.asList("email"));
//
//        login_button.registerCallback(c, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                // App code
//            }
//
//            @Override
//            public void onCancel() {
//                // App code
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                // App code
//            }
//        });


        google_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


        get_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ForgetPassword_Activity.class));
            }
        });


        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUp_Activity.class));
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!allowContinue()) {
                    return;
                }

                String email = email_et.getText().toString();
                String password = password_et.getText().toString();


                if (email.isEmpty()) {
                    email_et.setText("Email is Required!");
                    email_et.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    password_et.setError("Password is Required!");
                    password_et.requestFocus();
                    return;
                }
                if (password.length() < 8) {
                    password_et.setError("Password length must be greater than 8");
                    password_et.requestFocus();
                    return;
                }
                login(email, password);
            }
        });
    }

    private void fbLogin() {
        FacebookSdk.sdkInitialize(App.getAppContext());
        Log.d("TAG", "fbLogin: Facebook hash key: " + FacebookSdk.getApplicationSignature(this));
        getHashKey();
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration


        CallbackManager callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("TAG", "facebook:onSuccess:" + loginResult);
                // App code
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(@Nullable JSONObject jsonObject, @Nullable GraphResponse graphResponse) {
                        if (graphResponse.getError() != null) {
                            // handle error
                        } else {
                            String email = jsonObject.optString("email");
                            String id = jsonObject.optString("id");

                            if (email != null && !email.isEmpty()) {
                                googleSignInMethod(email);
                            }

                            Log.d("TAG", "onCompleted fb auth: " + email);
                            // send email and id to your web server
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d("TAG", "facebook:onCancel:");
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("TAG", "facebook:onError:");
                Toast.makeText(LogIn_Activity.this, exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                // App code
            }
        });

//        LoginManager.getInstance().registerCallback(callbackManager,
//                new FacebookCallback<LoginResult>() {
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//                        // App code
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        // App code
//                    }
//
//                    @Override
//                    public void onError(FacebookException exception) {
//                        // App code
//                    }
//                });
    }

    private void getHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.stackbuffers.myguardianangels", PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String sign = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.d("tag", "MY KEY HASH: " + sign);

            }
        } catch (Exception e) {
        }
    }


    boolean allowContinue() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager
                .PERMISSION_DENIED) {
            requestPermissions();
            return false;
        }
        return true;
    }

    private void signIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void verifyUserLogIn() {
        SharedPreferences preferences = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        if (preferences.contains("userEmail")) {
            startActivity(new Intent(getApplicationContext(), Home_Activity.class));
            finish();
        }
    }

    private void login(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        Call<Login_Response> call = RetrofitClientInstance.getInstance().getApi().login(email, password);
        call.enqueue(new Callback<Login_Response>() {
            @Override
            public void onResponse(Call<Login_Response> call, Response<Login_Response> response) {
                progressBar.setVisibility(View.GONE);
                Login_Response res = response.body();


                if (response.isSuccessful() && res.getStatus() == 200) {
                    users = res.getData();
                    for (int i = 0; i < users.size(); i++) {


                        Toast.makeText(getApplicationContext(), res.getMessage() + " \n" + users.get(i).getName(), Toast.LENGTH_SHORT).show();

                        SharedPreferences preferences = getSharedPreferences("credentials", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("userEmail", email);
                        editor.putString("userpassword", password);
                        editor.putString("userId", String.valueOf(res.getData().get(i).getId()));
                        editor.putString("userName", res.getData().get(i).getName());
                        editor.putString("phone", res.getData().get(i).getPhone());
                        editor.putString("dob", res.getData().get(i).getDob());
                        editor.putString("address", res.getData().get(i).getAddress());
                        editor.putString("time1", res.getData().get(i).getTimePeriod());


                        editor.commit();
                        editor.apply();
                        startActivity(new Intent(getApplicationContext(), Home_Activity.class));
                    }
                } else {
                    Toast.makeText(getApplicationContext(), res != null ? res.getMessage() : "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login_Response> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "LogIn Failed! " + t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });
    }


    AccessTokenTracker t = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null) {
                e.setText("");
                Toast.makeText(getApplicationContext(), "logout", Toast.LENGTH_SHORT).show();

            } else {
                loadUserProfile(currentAccessToken);
            }
        }
    };

    private void loadUserProfile(AccessToken currentAccessToken) {


        GraphRequest request = GraphRequest.newMeRequest(currentAccessToken, ((object, response) -> {


            if (object != null) {
                try {
                    String email = object.getString("email");

                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }

            }
        }));
        Bundle parameters = new Bundle();
        parameters.putString("fields", "email");
        request.setParameters(parameters);
        request.executeAsync();

    }

    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);


            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();

                googleSignInMethod(personEmail);
            } else {
                handleSignInResult(task);
            }


        }
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager
                .PERMISSION_DENIED || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager
                .PERMISSION_DENIED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager
                .PERMISSION_DENIED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager
                .PERMISSION_DENIED || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager
                .PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE}, 120);

        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {

        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);


            if (account != null) {
                String userEmail = account.getEmail();
                String userId = account.getId();
                String userName = account.getDisplayName();


                googleSignInMethod(userEmail);

            }
        } catch (ApiException apiException) {
            apiException.printStackTrace();
        }
    }

    private void googleSignInMethod(String userEmail) {
        progressBar.setVisibility(View.VISIBLE);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Call<Login_Response> call = RetrofitClientInstance.getInstance().getApi().googleSignIn(userEmail);
            call.enqueue(new Callback<Login_Response>() {
                @Override
                public void onResponse(Call<Login_Response> call, Response<Login_Response> response) {
                    progressBar.setVisibility(View.GONE);
                    Login_Response response1 = response.body();
                    if (response.isSuccessful() && response1.getStatus() == 200) {
                        users = response1.getData();
                        if (users == null || users.isEmpty()) {
                            Toast.makeText(LogIn_Activity.this, "User not found! Please signup", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (int i = 0; i < users.size(); i++) {


                            SharedPreferences pref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("GuserEmail", response1.getData().get(i).getEmail());
                            editor.putString("userEmail", response1.getData().get(i).getEmail());
                            editor.putString("GuserId", String.valueOf(response1.getData().get(i).getId()));
                            editor.putString("userId", String.valueOf(response1.getData().get(i).getId()));
                            editor.putString("userName", String.valueOf(response1.getData().get(i).getName()));

                            editor.putString("phone", response1.getData().get(i).getPhone());
                            editor.putString("dob", response1.getData().get(i).getDob());
                            editor.putString("address", response1.getData().get(i).getAddress());
                            editor.putString("time1", response1.getData().get(i).getTimePeriod());

                            editor.commit();

                            String phone = response1.getData().get(i).getPhone();

                            Toast.makeText(getApplicationContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();

                            if (phone!=null && !phone.isEmpty()){
                                Intent intent = new Intent(getApplicationContext(), Home_Activity.class);

                                startActivity(intent);
                            }else {
                                Intent intent = new Intent(getApplicationContext(), Edit_Profile_Activity.class);

                                startActivity(intent);
                            }


                        }


                    } else {
                        Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Login_Response> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Login error " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }, 500);


    }
}