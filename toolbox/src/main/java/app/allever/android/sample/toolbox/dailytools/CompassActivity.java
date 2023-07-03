package app.allever.android.sample.toolbox.dailytools;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import app.allever.android.lib.common.BaseActivity;
import app.allever.android.lib.mvvm.base.BaseViewModel;
import app.allever.android.sample.toolbox.databinding.ActivityCompassBinding;


public class CompassActivity extends BaseActivity<ActivityCompassBinding, BaseViewModel> {

    private String directiona = "UNKNOWN";
    private SensorManager mSensorManager;
    private final String[] mDirectionText = new String[]{"北", "东北", "东", "东南", "南", "西南", "西", "西北"};

    @NonNull
    @Override
    public ActivityCompassBinding inflateChildBinding() {
        return ActivityCompassBinding.inflate(getLayoutInflater());
    }


    @Override
    public void init() {
        initTopBar("指南针", true, null);
        binding.compass.setLayoutParams(new LinearLayout.LayoutParams(this.getResources().getDisplayMetrics().widthPixels, this.getResources().getDisplayMetrics().widthPixels));

        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) == null) {
            //Toast.makeText(this,"你的设备不支持陀螺仪",Toast.LENGTH_SHORT).show();
        }
        Vibrator mVibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        SensorEventListener sensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] _rotationMatrix = new float[16];
                SensorManager.getRotationMatrixFromVector(_rotationMatrix, event.values);
                float[] _remappedRotationMatrix = new float[16];
                SensorManager.remapCoordinateSystem(_rotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, _remappedRotationMatrix);
                float[] _orientations = new float[3];
                SensorManager.getOrientation(_remappedRotationMatrix, _orientations);
                for (int _i = 0; _i < 3; _i++) {
                    _orientations[_i] = (float) (Math.toDegrees(_orientations[_i]));
                }
                final double _x = _orientations[0];
                final double _y = _orientations[1];
                final double _z = _orientations[2];
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        mSensorManager.registerListener(sensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected boolean isFullScreen() {
        return true;
    }

    @Override
    protected boolean showTopBar() {
        return true;
    }

    @SuppressWarnings("deprecation")
    private void initSensor() {
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager != null) {
            Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
            //注册监听
            mSensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float dirAngel = event.values[0];

            binding.compass.setDirectionAngle(dirAngel);
            directiona = mDirectionText[((int) (dirAngel + 22.5f) % 360) / 45];
            binding.direction.setText(directiona);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            if (accuracy != SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM
                    && accuracy != SensorManager.SENSOR_STATUS_ACCURACY_HIGH) {
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        initSensor();
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);
    }
}