package ding.multiwindowsample;

import android.app.ActivityOptions;
import android.app.PictureInPictureParams;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Rational;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private View mFlSplitScreen, mFlPip, mFlFreeForm;
    private TextView mTextMessage;
    private Button mButtonPip, mButtonFreeForm;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mFlSplitScreen.setVisibility(View.VISIBLE);
                    mFlPip.setVisibility(View.GONE);
                    mFlFreeForm.setVisibility(View.GONE);
                    mTextMessage.setText(R.string.title_split_screen);
                    return true;
                case R.id.navigation_dashboard:
                    mFlSplitScreen.setVisibility(View.GONE);
                    mFlPip.setVisibility(View.VISIBLE);
                    mFlFreeForm.setVisibility(View.GONE);
                    mTextMessage.setText(R.string.title_picture_in_picture);
                    return true;
                case R.id.navigation_notifications:
                    mFlSplitScreen.setVisibility(View.GONE);
                    mFlPip.setVisibility(View.GONE);
                    mFlFreeForm.setVisibility(View.VISIBLE);
                    mTextMessage.setText(R.string.title_free_form);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFlSplitScreen = findViewById(R.id.fl_split_Screen);
        mFlPip = findViewById(R.id.fl_pip);
        mFlFreeForm = findViewById(R.id.fl_free_form);
        mTextMessage = (TextView) findViewById(R.id.message);
        mButtonPip = findViewById(R.id.bt_pip);
        mButtonPip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minimize();
            }
        });
        mButtonFreeForm = findViewById(R.id.bt_free_form);
        mButtonFreeForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStartLaunchBoundsActivity(view);
            }
        });
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onUserLeaveHint () {
        // when user press home key
        //minimize();
    }

    /** Enters Picture-in-Picture mode. */
    void minimize() {
        // Calculate the aspect ratio of the PiP screen.
        Rational aspectRatio = new Rational(400, 300);
        PictureInPictureParams.Builder mPictureInPictureParamsBuilder = new PictureInPictureParams.Builder();
        mPictureInPictureParamsBuilder
                .setAspectRatio(aspectRatio)
                .build();
        enterPictureInPictureMode(mPictureInPictureParamsBuilder.build());
    }


    public void onStartLaunchBoundsActivity(View view) {

        // Define the bounds in which the Activity will be launched into.
        //Rect bounds = new Rect(500, 300, 100, 0);
        Rect bounds = new Rect(0,0,300,300);
        // Set the bounds as an activity option.
        ActivityOptions options = ActivityOptions.makeBasic();
        options.setLaunchBounds(bounds);

        // Start the LaunchBoundsActivity with the specified options
        Intent intent = new Intent(this, FreeFormActivity.class);
        intent.addFlags(
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent, options.toBundle());

    }
}
