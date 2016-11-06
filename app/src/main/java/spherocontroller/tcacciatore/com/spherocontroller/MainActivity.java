package spherocontroller.tcacciatore.com.spherocontroller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.orbotix.DualStackDiscoveryAgent;
import com.orbotix.common.Robot;
import com.orbotix.le.DiscoveryAgentLE;
import com.orbotix.le.RobotLE;
import com.orbotix.common.RobotChangedStateListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        FloatingActionButton fabPink = (FloatingActionButton) findViewById(R.id.pinkButton);
        fabPink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isColorSet = SpheroManager.getInstance().setPink((RobotColorStateListener) getSupportFragmentManager().findFragmentById(R.id.fragment));

                if (!isColorSet) {
                    onFailedSettingColor(view);
                }
            }
        });

        FloatingActionButton fabGreen = (FloatingActionButton) findViewById(R.id.greenButon);
        fabGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isColorSet = SpheroManager.getInstance().setGreen((RobotColorStateListener) getSupportFragmentManager().findFragmentById(R.id.fragment));
                if (!isColorSet) {
                    onFailedSettingColor(view);
                }
            }
        });

        FloatingActionButton fabYellow = (FloatingActionButton) findViewById(R.id.yellowButton);
        fabYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isColorSet = SpheroManager.getInstance().setYellow((RobotColorStateListener) getSupportFragmentManager().findFragmentById(R.id.fragment));
                if (!isColorSet) {
                    onFailedSettingColor(view);
                }
            }
        });
    }

    public void onFailedSettingColor (View view) {
        Snackbar stateSnackBar = Snackbar.make(view, "failed setting color...", Snackbar.LENGTH_LONG);
        stateSnackBar.show();
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
}
