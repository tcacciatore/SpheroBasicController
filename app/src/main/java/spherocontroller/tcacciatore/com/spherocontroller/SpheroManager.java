package spherocontroller.tcacciatore.com.spherocontroller;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.orbotix.ConvenienceRobot;
import com.orbotix.DualStackDiscoveryAgent;
import com.orbotix.Ollie;
import com.orbotix.Sphero;
import com.orbotix.classic.RobotClassic;
import com.orbotix.command.RollCommand;
import com.orbotix.common.DiscoveryException;
import com.orbotix.common.Robot;
import com.orbotix.common.RobotChangedStateListener;
import com.orbotix.le.RobotLE;

/**
 * Created by thomascacciatore on 05/11/2016.
 */

public class SpheroManager {

    private static SpheroManager mInstance = null;
    private ConvenienceRobot mRobot;

    public enum Directions {
        FORWARD,
        BACKWARD,
        LEFT,
        RIGHT
    }


    private SpheroManager () {

    }

    public static SpheroManager getInstance() {
        if (mInstance == null) {
            mInstance = new SpheroManager();
        }
        return mInstance;
    }

    private void onRobotOnline(Robot robot) {
        // Bluetooth Classic (Sphero)
        if (robot instanceof RobotClassic) {
            mRobot = new Sphero(robot);
        }
        // Bluetooth LE (Ollie)
        if (robot instanceof RobotLE) {
            mRobot = new Ollie(robot);
        }

        mRobot.calibrating(true);
        mRobot.setZeroHeading();
    }

    public void listenSpheroStateChange(final RobotChangedStateListener listener) {
        // Discover Sphero devices.
        DualStackDiscoveryAgent.getInstance().addRobotStateListener(new RobotChangedStateListener() {
            @Override
            public void handleRobotChangedState(Robot robot, RobotChangedStateNotificationType type ) {
                // Notify
                listener.handleRobotChangedState(robot, type);
                Snackbar stateSnackBar = null;
                switch (type) {

                    case Online:
                        onRobotOnline(robot);
                        break;

                    case Disconnected:
                        break;

                    case Connecting:
                        break;

                }

                if (stateSnackBar == null) return;
                stateSnackBar.show();
            }
        });
    }

    public void discoverSphero(Context ctx) throws DiscoveryException {
        DualStackDiscoveryAgent.getInstance().startDiscovery(ctx);
    }

    public void disconnectRobot() {
        if( mRobot != null )
            mRobot.disconnect();
    }

    public void goForward() {
        if (mRobot == null) return;
        Log.d("MainActivity", "going forward...");
        mRobot.sendCommand( new RollCommand( 0, 1.0f, RollCommand.State.GO ) );
    }

    public void goBackward() {
        if (mRobot == null) return;
        Log.d("MainActivity", "going backward");
        mRobot.sendCommand( new RollCommand( 180, 1.0f, RollCommand.State.GO ) );
    }

    public void strafeLeft() {
        if (mRobot == null) return;
        Log.d("MainActivity", "strafing left...");
        mRobot.sendCommand( new RollCommand( 270, 1.0f, RollCommand.State.GO ) );
    }

    public void strafeRight() {
        if (mRobot == null) return;
        Log.d("MainActivity", "strafing right...");
        mRobot.sendCommand( new RollCommand( 90, 1.0f, RollCommand.State.GO ) );
    }

    public boolean setColor(float r, float g, float b, RobotColorStateListener listener) {
        if (mRobot == null) return false;

        mRobot.setLed( r, g, b);
        if (listener != null) {
            listener.onColorChanged(r, g, b);
        }
        return true;
    }

    public boolean setPink(RobotColorStateListener listener) {
        return setColor(255.0f/255.0f, 64.0f/255.0f, 129.0f/255.0f, listener);

    }

    public boolean setGreen(RobotColorStateListener listener) {
        return setColor(0.0f/255.0f, 255.0f/255.0f, 0.0f/255.0f, listener);
    }

    public boolean setYellow(RobotColorStateListener listener) {
        return setColor(255.0f/255.0f, 255.0f/255.0f, 0.0f/255.0f, listener);
    }
}
