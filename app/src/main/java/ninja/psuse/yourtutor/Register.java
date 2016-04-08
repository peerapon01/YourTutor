package ninja.psuse.yourtutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.AppCompatEditText;

import ninja.psuse.yourtutor.Async.RegisterAsyncTask;
import ninja.psuse.yourtutor.other.RegisterInfo;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class Register extends AppCompatActivity {
    Button registerButton;
    Button cancelButton;

    android.support.v7.widget.AppCompatEditText firstname;
    android.support.v7.widget.AppCompatEditText lastname;
    android.support.v7.widget.AppCompatEditText lineid;
    android.support.v7.widget.AppCompatEditText mobilenum;
    android.support.v7.widget.AppCompatEditText email;
    Intent intent;
    String s;
    String facebookId;
    String facebookName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bundle b = getIntent().getExtras();
        s = b.getString("displayPic");
        facebookId = b.getString("facebookId");
        facebookName = b.getString("facebookName");


        firstname = (android.support.v7.widget.AppCompatEditText) findViewById(R.id.firstnameRegis);
        lastname = (android.support.v7.widget.AppCompatEditText) findViewById(R.id.lastnameRegis);
        lineid = (android.support.v7.widget.AppCompatEditText) findViewById(R.id.lineidRegis);
        mobilenum = (android.support.v7.widget.AppCompatEditText) findViewById(R.id.mobilenumRegis);
        email = (android.support.v7.widget.AppCompatEditText) findViewById(R.id.emailRegis);
        registerButton = (Button) findViewById(R.id.registerButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        intent = new Intent(this, MainActivity.class);
        intent.putExtra("displayPic", s);
        intent.putExtra("facebookId", facebookId);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterInfo register = new RegisterInfo();
                register.firstname = firstname.getText().toString();
                register.lastname = lastname.getText().toString();
                register.lineid = lineid.getText().toString();
                register.mobilenum = mobilenum.getText().toString();
                register.email = email.getText().toString();
                register.facebookID = facebookId;
                register.facebookName = facebookName;
                RegisterAsyncTask registerAsyncTask = new RegisterAsyncTask();
                registerAsyncTask.execute(register);
                startActivity(intent);


            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroy();

            }
        });

    }
}
