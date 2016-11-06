package spherocontroller.tcacciatore.com.spherocontroller;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.orbotix.ConvenienceRobot;
import com.orbotix.common.DiscoveryException;
import com.orbotix.common.Robot;
import com.orbotix.common.RobotChangedStateListener;

interface RobotColorStateListener {
    public void onColorChanged(float r, float g, float b);
}

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements RobotChangedStateListener, RobotColorStateListener {

    @Override
    public void handleRobotChangedState(Robot robot, RobotChangedStateNotificationType robotChangedStateNotificationType) {
        Snackbar stateSnackBar = null;
        switch (robotChangedStateNotificationType) {

            case Online:
                stateSnackBar = Snackbar.make(getView(), "Sphero named"+robot.getName()+"is online", Snackbar.LENGTH_LONG);
                break;

            case Disconnected:
                stateSnackBar = Snackbar.make(getView(), "Sphero named"+robot.getName()+"is disconnected", Snackbar.LENGTH_LONG);
                break;

            case Connecting:
                stateSnackBar = Snackbar.make(getView(), "Sphero named"+robot.getName()+"is connecting...", Snackbar.LENGTH_LONG);
        }

        if (stateSnackBar == null) return;
        stateSnackBar.show();
    }

    @Override
    public void onColorChanged(float r, float g, float b) {
        mCommandsLayout.setBackgroundColor(Color.rgb((int)r*255, (int)g*255, (int)b*255));
        mLeftAndRightCommandsLayout.setBackgroundColor(Color.rgb((int)r*255, (int)g*255, (int)b*255));
    }

    public enum Directions {
        FORWARD,
        BACKWARD,
        LEFT,
        RIGHT
    }

    private ConvenienceRobot mRobot;
    private Button mForwardButton = null;
    private Button mBackwardButton = null;
    private Button mStrafeRightButton = null;
    private Button mStrafeLeftButton = null;
    private LinearLayout mCommandsLayout = null;
    private LinearLayout mLeftAndRightCommandsLayout = null;

    public MainActivityFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();

        try {
            SpheroManager.getInstance().discoverSphero(getContext());
        } catch (DiscoveryException e) {
            e.printStackTrace();
        }
        SpheroManager.getInstance().listenSpheroStateChange(this);
    }

    @Override
    public void onStop() {
        SpheroManager.getInstance().disconnectRobot();
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        // Inflate commands buttons.
        mForwardButton = (Button) v.findViewById(R.id.forwardButton);
        mForwardButton.setTag(Directions.FORWARD);
        mForwardButton.setOnClickListener(onCommandButtonTapped);

        mBackwardButton = (Button) v.findViewById(R.id.backwardButton);
        mBackwardButton.setTag(Directions.BACKWARD);
        mBackwardButton.setOnClickListener(onCommandButtonTapped);

        mStrafeLeftButton = (Button) v.findViewById(R.id.strafeLeftButton);
        mStrafeLeftButton.setTag(Directions.LEFT);
        mStrafeLeftButton.setOnClickListener(onCommandButtonTapped);

        mStrafeRightButton = (Button) v.findViewById(R.id.strafeRightButton);
        mStrafeRightButton.setTag(Directions.RIGHT);
        mStrafeRightButton.setOnClickListener(onCommandButtonTapped);

        mCommandsLayout = (LinearLayout) v.findViewById(R.id.CommandsLayout);
        mLeftAndRightCommandsLayout = (LinearLayout) v.findViewById(R.id.LeftAndRightCommandsLayout);

        return v;
    }

    private View.OnClickListener onCommandButtonTapped = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (v.getTag() == Directions.FORWARD) {
                SpheroManager.getInstance().goForward();
            }

            else if (v.getTag() == Directions.BACKWARD) {
                SpheroManager.getInstance().goBackward();
            }

            else if (v.getTag() == Directions.LEFT) {
                SpheroManager.getInstance().strafeLeft();
            }

            else if (v.getTag() == Directions.RIGHT) {
                SpheroManager.getInstance().strafeRight();
            }
        }
    };
}
