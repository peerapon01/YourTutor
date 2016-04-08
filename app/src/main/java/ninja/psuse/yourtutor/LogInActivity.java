package ninja.psuse.yourtutor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import ninja.psuse.yourtutor.Async.CheckAlreadyHaveThisID;
import ninja.psuse.yourtutor.Async.QueryBuilder;
import ninja.psuse.yourtutor.Async.RegisterAsyncTask;
import ninja.psuse.yourtutor.other.RegisterInfo;
import ninja.psuse.yourtutor.other.RoundImageView;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LogInActivity extends ActionBarActivity {
    CallbackManager callbackManager;
    private Intent intent;
    private ProfileTracker profileTracker;
    private AccessTokenTracker accessTokenTracker;
    private TextView userDisplay;
    private LoginButton loginButton;
    private ImageView userPic;
    private Intent intent2;
    ProgressDialog pDialog;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = new Intent(this, MainActivity.class);
        intent2 = new Intent(this, Register.class);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        userPic = (ImageView) this.findViewById(R.id.user_pic);
        userDisplay = (TextView) this.findViewById(R.id.username);
        loginButton = (LoginButton) this.findViewById(R.id.login_button);
        videoView = (VideoView) this.findViewById(R.id.videoView);
        loginButton.setReadPermissions(Arrays.asList("user_friends", "public_profile", "email"));


        // If using in a fragment
        //loginButton.setFragment(this);
        // Other app specific specialization

        // Callback registration
        /*CallbackManager callbackManager = new CallbackManager() {
            @Override
            public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
                return false;
            }
        };*/

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                displayMessage(profile);
                accessToken.getPermissions();
                Log.v("USERID_FB", accessToken.getUserId());


            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                displayMessage(newProfile);
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
        Profile profile = Profile.getCurrentProfile();
        displayMessage(profile);
    }

    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    public void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    public void displayMessage(Profile profile) {

        if (profile != null) {
            RoundImageView roundImageView = new RoundImageView(this);
            userDisplay.setText(profile.getName() + " ");
            String userDisplayString = profile.getProfilePictureUri(320, 320).toString();
            Log.v("img", userDisplayString);
            Glide.with(this).load(userDisplayString).asBitmap().centerCrop().into(new BitmapImageViewTarget(userPic) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    userPic.setImageDrawable(circularBitmapDrawable);

                }
            });
            Log.v("USERID_PROFILE", profile.getId());
            intent.putExtra("displayPic", userDisplayString);
            intent2.putExtra("displayPic", userDisplayString);

            RegisterInfo registerInfo = new RegisterInfo();

            String facebookId = profile.getId();
            registerInfo.facebookID = facebookId;
            String facebookName = profile.getName();
            registerInfo.facebookName = facebookName;
            intent2.putExtra("facebookId", facebookId);
            intent2.putExtra("facebookName", facebookName);
            intent.putExtra("facebookId", facebookId);
            intent.putExtra("facebookName", facebookName);


            CheckAlreadyHaveThisID checkAlreadyHaveThisID = new CheckAlreadyHaveThisID();
            checkAlreadyHaveThisID.execute(registerInfo);

        } else {
            userDisplay.setText("Please Log In First");
            Glide.with(this).load(R.mipmap.icon_test_login).into(userPic);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public class CheckAlreadyHaveThisID extends AsyncTask<RegisterInfo, Void, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected void onPreExecute() {
            pDialog.setMessage("Loading Data.. Please Wait.");
            pDialog.show();
            super.onPreExecute();
        }

        protected String doInBackground(RegisterInfo... arg0) {
            try {
                QueryBuilder qb = new QueryBuilder();
                RegisterInfo contact = arg0[0];
                String facebookId = contact.facebookID;
                Request request = new Request.Builder()
                        .url("https://api.mlab.com/api/1/databases/yourtutor/collections/users?q={\"facebookid\":\"" + facebookId + "\"}&c=true&apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR")
                        .build();
                Log.v("urltest", "https://api.mlab.com/api/1/databases/yourtutor/collections/users?q={\"facebookid\":\"" + facebookId + "\"}&c=true&apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR");
                Response response = client.newCall(request).execute();
                String check = response.body().string();
                Log.v("response", check);
                return check;


           /* if(response.body().string().equals(0)){
                Log.v("test","eiei");
                String json = qb.createRegisterInfo(contact);
                RequestBody body = RequestBody.create(JSON, json);
                Request request2 = new Request.Builder()
                        .url("https://api.mlab.com/api/1/databases/yourtutor/collections/users?apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR")
                        .post(body)
                        .build();
                Response response2 = client.newCall(request2).execute();

                if(response2.isSuccessful())
                {
                    Log.v("Successsend","body");
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else{
                return false;
            }*/
            } catch (Exception e) {
                String val = e.getMessage();
                String val2 = val;
                return null;
            }

        }

        @Override
        protected void onPostExecute(String aString) {
            super.onPostExecute(aString);

            Log.v("StringCheck", aString);

            pDialog.dismiss();
            launchRegisterActivity(aString);
        }

        protected void launchRegisterActivity(String aString) {
            if (aString.equals("0")) {
                startActivity(intent2);
            } else {
                startActivity(intent);
            }
        }
    }
}
