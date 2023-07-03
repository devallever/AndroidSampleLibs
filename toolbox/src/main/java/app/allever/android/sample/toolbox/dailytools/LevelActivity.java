package app.allever.android.sample.toolbox.dailytools;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.annotation.NonNull;

import app.allever.android.lib.common.BaseActivity;
import app.allever.android.lib.mvvm.base.BaseViewModel;
import app.allever.android.sample.toolbox.databinding.ActivityLevelBinding;

public class LevelActivity extends BaseActivity<ActivityLevelBinding, BaseViewModel> implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor acc_sensor;
    private Sensor mag_sensor;

    private float[] accValues = new float[3];
    private float[] magValues = new float[3];
    private float r[] = new float[9];
    private float values[] = new float[3];

    @NonNull
    @Override
    public ActivityLevelBinding inflateChildBinding() {
        return ActivityLevelBinding.inflate(getLayoutInflater());
    }

    @Override
    public void init() {
        initTopBar("水平仪", true, null);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    public void onResume() {
        super.onResume();
        acc_sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mag_sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorManager.registerListener(this, acc_sensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mag_sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // 取消方向传感器的监听
        mSensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        // 取消方向传感器的监听
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // 获取手机触发event的传感器的类型
        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                accValues = event.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                magValues = event.values.clone();
                break;

        }

        SensorManager.getRotationMatrix(r, null, accValues, magValues);
        SensorManager.getOrientation(r, values);

        // 获取　沿着Z轴转过的角度
        float azimuth = values[0];

        // 获取　沿着X轴倾斜时　与Y轴的夹角
        float pitchAngle = values[1];

        // 获取　沿着Y轴的滚动时　与X轴的角度
        //此处与官方文档描述不一致，所在加了符号（https://developer.android.google.cn/reference/android/hardware/SensorManager.html#getOrientation(float[], float[])）
        float rollAngle = -values[2];

        onAngleChanged(rollAngle, pitchAngle, azimuth);

    }

    /**
     * 角度变更后显示到界面
     *
     * @param rollAngle
     * @param pitchAngle
     * @param azimuth
     */
    private void onAngleChanged(float rollAngle, float pitchAngle, float azimuth) {

        binding.levelView.setAngle(rollAngle, pitchAngle);

        binding.tvvHorz.setText((int) Math.toDegrees(rollAngle) + "°");
        binding.tvvVertical.setText((int) Math.toDegrees(pitchAngle) + "°");
    }


}