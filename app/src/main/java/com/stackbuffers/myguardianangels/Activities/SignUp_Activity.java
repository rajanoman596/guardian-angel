package com.stackbuffers.myguardianangels.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
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
import com.stackbuffers.myguardianangels.Models.Google_Signup_Response;
import com.stackbuffers.myguardianangels.Models.SignUp_Model;
import com.stackbuffers.myguardianangels.Models.SignUp_Response;
import com.stackbuffers.myguardianangels.R;
import com.stackbuffers.myguardianangels.RetrofitClasses.RetrofitClientInstance;

import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp_Activity extends AppCompatActivity {
    Spinner angel_guardian;
    Button sign_up_btn;
    String userT;
    TextView login_tv;
    EditText full_name,email_et;
    TextInputEditText password_et,confirm_et;
    SignUp_Model signUpModel;
    ProgressBar progressBar;

    int RC_SIGN_IN = 0;
    ImageView google_imge,facebook_img;

    GoogleSignInClient mGoogleSignInClient;

    LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        angel_guardian = findViewById(R.id.spinner);
        sign_up_btn = findViewById(R.id.sign_up_btn);

        login_tv = findViewById(R.id.login_tv);
        full_name = findViewById(R.id.full_name);
        email_et = findViewById(R.id.email_et);
        password_et = findViewById(R.id.password_et);
        confirm_et = findViewById(R.id.confirm_et);
        progressBar = findViewById(R.id.progress_bar);

        facebook_img = findViewById(R.id.facebook_img);
        google_imge = findViewById(R.id.google_imge);

        facebook_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();
            }
        });
        fbLogin();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        google_imge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignup();
            }
        });



        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.User_Type, R.layout.signup_spinner_layout);
        angel_guardian.setAdapter(dataAdapter);
        angel_guardian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userT = angel_guardian.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        login_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LogIn_Activity.class));
            }
        });

        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)

            {

               String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

                String name = full_name.getText().toString();
               String email = email_et.getText().toString();
               String password = password_et.getText().toString();
               String confirm_pswd = confirm_et.getText().toString();


                if (name.isEmpty()) {
                    full_name.setError("Name Field Required!");
                    full_name.requestFocus();
                    return;
                }

                if (email.isEmpty()) {
                    email_et.setText("Email is Required!");
                    email_et.requestFocus();
                    return;
                }
                if(!email.matches(emailPattern)){
                    email_et.setError("Invalid email pattern!");
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


                signUp(name,email,password,confirm_pswd,userT);

            }
        });
    }

    private void fbLogin(){
        //FacebookSdk.sdkInitialize(App.getAppContext());
        Log.d("TAG", "fbLogin: Facebook hash key: "+FacebookSdk.getApplicationSignature(this));
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email"));
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
                            String name = jsonObject.optString("name");
                            String id = jsonObject.optString("id");

                            if (email!=null && !email.isEmpty()){
                                signUpWithGoogle(name,email);
                            }

                            Log.d("TAG", "onCompleted fb auth: "+email);
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
                Log.d("TAG", "facebook:onCancel:" );
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("TAG", "facebook:onError:" );
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


    private void googleSignup() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    private void signUp( String name, String email, String password,String confrim_pswd, String userT) {
        if(!confrim_pswd.equals(password)){
            confirm_et.setError("Password doesn't match!");
            confirm_et.requestFocus();
            return;
        }
        else {


            String role;

            if (userT.equals("Angels")) {
                role = "1";
            } else {
                role = "2";
            }

            Call<SignUp_Response> call = RetrofitClientInstance.getInstance().getApi().signup(email, name, password, role);
            call.enqueue(new Callback<SignUp_Response>() {
                @Override
                public void onResponse(Call<SignUp_Response> call, Response<SignUp_Response> response) {
                    SignUp_Response res = response.body();
                    if (response.isSuccessful() && res.getStatus() == 200) {
                        Toast.makeText(getApplicationContext(), res.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(),Verify_Activity.class);
                        i.putExtra("email",email);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), res.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SignUp_Response> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Poor Internet Connection!", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignupResult(task);
        }
    }

    private void handleSignupResult(Task<GoogleSignInAccount> task) {

        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);

            if (account != null) {
                String userEmail = account.getEmail();
                String userId = account.getId();
                String userName = account.getDisplayName();

                signUpWithGoogle(userName,userEmail);














                // Signed in successfully, show authenticated UI.

            }
        } catch (ApiException apiException) {

        }
    }

    private void signUpWithGoogle(String userName, String userEmail) {
        progressBar.setVisibility(View.VISIBLE);

        Call<Google_Signup_Response> call = RetrofitClientInstance.getInstance().getApi().googleSignup(userName,userEmail);
        call.enqueue(new Callback<Google_Signup_Response>() {
            @Override
            public void onResponse(Call<Google_Signup_Response> call, Response<Google_Signup_Response> response) {
                progressBar.setVisibility(View.GONE);
                Google_Signup_Response res = response.body();
                if (response.isSuccessful()&&res.getStatus()==200){
                    Toast.makeText(getApplicationContext(), res.getMessage()+"\n"+userName+"\n"+userEmail, Toast.LENGTH_LONG).show();

                    SharedPreferences pref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("GuserEmail", res.getData().getEmail());
                    editor.putString("GuserId",String.valueOf(res.getData().getId()));
                    editor.putString("userId", String.valueOf(res.getData().getId()));
                    editor.putString("userEmail", String.valueOf(res.getData().getEmail()));
                    editor.commit();

                    Intent i = new Intent(SignUp_Activity.this, Edit_Profile_Activity.class);
                    startActivity(i);
                   // finishAffinity();

                }
                else{
                    Toast.makeText(getApplicationContext(), res.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Google_Signup_Response> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Signup Failed!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}