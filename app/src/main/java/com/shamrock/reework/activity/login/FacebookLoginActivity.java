package com.shamrock.reework.activity.login;

//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.cardview.widget.CardView;
//
//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.Scopes;
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.Scope;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.material.snackbar.Snackbar;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.InstanceIdResult;
//import com.shamrock.R;
//import com.shamrock.reework.activity.LoginModule.LoginActivity;
//import com.shamrock.reework.activity.newregistrationmodule.newregistration.NewRegisterActivity;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

public class FacebookLoginActivity
//        extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener
{
//    //
//    private GoogleApiClient mGoogleApiClient;
//    GoogleSignInClient mGoogleSignInClient;
//    private static final int RC_SIGN_IN = 001;
//    RelativeLayout linearLayout;
//    private static final String client_Id = "833644050628-ccq7hf6ks8a6gcj3u1omr4l8m4v3p8v9.apps.googleusercontent.com";
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
//                .requestServerAuthCode(client_Id)
//                .requestEmail()
//                .build();
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
//
//
//
//
//
//        signIn();
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//// updateUI(account);
//    }
//
//    @Override
//    public void onClick(View v) {
//
//
//    }
//
//    private void signIn() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == RC_SIGN_IN) {
//// a listener.
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            handleSignInResult(task);
//        }
//    }
//
//    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
//        try {
//            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
//
//            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
//
//
//            if (acct != null) {
//                String personName = acct.getDisplayName();
//                String personGivenName = acct.getGivenName();
//                String personFamilyName = acct.getFamilyName();
//                String personEmail = acct.getEmail();
//                String personId = acct.getId();
//                Uri personPhoto = acct.getPhotoUrl();
//
//
//                Intent intent = new Intent(FacebookLoginActivity.this, NewRegisterActivity.class);
//                intent.putExtra("KEY_MOB", "");
//                intent.putExtra("KEY_FROM_GOOGLE",true);
//                intent.putExtra("personId", ""+personId);
//                intent.putExtra("personEmail", ""+personEmail);
//                intent.putExtra("personName", ""+personName);
//                startActivity(intent);
//
//
//                Toast.makeText(this, ""+personId, Toast.LENGTH_LONG).show();
//
//
//            }
//
////
//        } catch (ApiException e) {
//            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//
//        }
//    }
//
//
//
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        mGoogleSignInClient.signOut()
//                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//// ...
//                    }
//                });
//
//
//    }


}