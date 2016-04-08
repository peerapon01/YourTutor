package ninja.psuse.yourtutor;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

public class MainActivity extends AppCompatActivity {
    private BottomBar mBottomBar;
    int previousTab;
    private Intent intent;
    String displayPic;
    String facebookId;
    String facebookName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle b = getIntent().getExtras();
        displayPic = b.getString("displayPic");
        facebookId = b.getString("facebookId");
        facebookName = b.getString("facebookName");
        Log.v("displayfromb",displayPic);
        intent = new Intent(this,MapsActivity.class);
        mBottomBar = BottomBar.attach(this, savedInstanceState);
       previousTab = mBottomBar.getCurrentTabPosition();
        mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(int resId) {
                if (resId == R.id.bottomBarItemOne) {

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentContainer,MainActivityFragment.newInstance(facebookId,facebookName))
                            .commit();

                }
                if (resId == R.id.bottomBarItemTwo) {
                    mBottomBar.selectTabAtPosition(0,true);
                    startActivity(intent);



                }
                if (resId == R.id.bottomBarItemThree) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentContainer,CourseFragment.newInstance(facebookId,facebookName))
                            .commit();
                }
                if (resId == R.id.bottomBarItemFour) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentContainer,AccountFragment.newInstance(displayPic,facebookId))
                            .commit();// the user selected item number one
                }

            }
        });
        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this,R.color.primary));
        mBottomBar.mapColorForTab(1, 0xFF5D4037);
        mBottomBar.mapColorForTab(2, "#7B1FA2");
        mBottomBar.mapColorForTab(3, "#FF5252");
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, MainActivityFragment.newInstance(facebookId, facebookName), "Defualt Fragment")
                    .commit();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);

    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }
}
