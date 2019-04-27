package robot.steeringSystem.motor;

import robot.pwmPin.*;

public class MotorImpl implements Motor {
    private PwmPin mPinForward;
    private PwmPin mPinBackWard;
    private final static int MAX_SPEED = 100;
    private PwmPin mPinInUse;
    private int mSpeed;
    private boolean mForward;

    public MotorImpl(PwmPin pinForward, PwmPin pinBackWard) {
        mPinForward = pinForward;
        mPinBackWard = pinBackWard;
        mPinInUse = null;
        mForward = true;
        mSpeed = 0;

    }

    @Override
    public boolean isForward() {
        return mForward;
    }

    @Override
    public void revolve() {
        prepareBeforeRevolve();
        ride();
    }

    @Override
    public void setSpeed(int speed) {
        if (speed > MAX_SPEED) {
            mSpeed = MAX_SPEED;
        } else if (speed < 0) {
            mSpeed = 0;
        } else {
            mSpeed = speed;
        }
        onSetSpeed();
    }

    private void ride() {
        try {
            mPinInUse.write(mSpeed);
        } catch (Exception e) {
            stopPin();
        }
    }

    @Override
    public int getSpeed() {
        return mSpeed;
    }

    @Override
    public void stop() {
        setSpeed(0);
        mPinInUse = null;
    }

    @Override
    public void setForwardDirection() {
        mForward = true;
        onDirectionChange();
    }

    @Override
    public void setBackWardDirection() {
        mForward = false;
        onDirectionChange();
    }


    private void prepareBeforeRevolve() {
        mPinInUse = mForward ? mPinForward : mPinBackWard;
    }

    private void onSetSpeed() {
        if (mPinInUse != null) {
            ride();
        }
    }

    private void onDirectionChange() {
        if (mPinInUse == null) return;

        try {
            mPinInUse.write(0);
            prepareBeforeRevolve();
            mPinInUse.write(mSpeed);
        } catch (Exception e) {
            stopPin();
        }
    }

    private void stopPin() {
        mPinInUse.stop();
        mPinInUse = null;
        mSpeed = 0;
    }
}
