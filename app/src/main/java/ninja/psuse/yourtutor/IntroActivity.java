package ninja.psuse.yourtutor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {


    // Please DO NOT override onCreate. Use init.
    @Override
    public void init(Bundle savedInstanceState) {

        // Add your slide's fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        addSlide(AppIntroFragment.newInstance("YourTutor", "best application that's build for you",R.mipmap.ic_launcher,Color.parseColor("#3F51B5")));
        addSlide(AppIntroFragment.newInstance("YourTutor", "best application that's build for you",R.mipmap.ic_launcher,Color.parseColor("#3F51B5")));
        addSlide(AppIntroFragment.newInstance("YourTutor", "best application that's build for you",R.mipmap.ic_launcher,Color.parseColor("#3F51B5")));
        addSlide(AppIntroFragment.newInstance("YourTutor", "best application that's build for you",R.mipmap.ic_launcher,Color.parseColor("#3F51B5")));

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        //addSlide(AppIntroFragment.newInstance(title, description, image, background_colour));

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#3F51B7"));
        setSeparatorColor(Color.parseColor("#2196F8"));

        // Hide Skip/Done button.
        showSkipButton(false);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permisssion in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed() {
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed() {

        Intent send = new Intent(this.getApplicationContext(),LogInActivity.class);
        this.startActivity(send);
        finish();
        // Do something when users tap on Done button.
    }

    @Override
    public void onSlideChanged() {
        // Do something when the slide changes.
    }

    @Override
    public void onNextPressed() {
        // Do something when users tap on Next button.
    }

}
