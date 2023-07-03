package app.allever.android.sample.toolbox.dailytools

import android.annotation.SuppressLint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.toolbox.databinding.ActivityLevelBinding

class LevelActivity : BaseActivity<ActivityLevelBinding, BaseViewModel>(), SensorEventListener {
    private lateinit var mSensorManager: SensorManager
    private lateinit var accSensor: Sensor
    private lateinit var magSensor: Sensor
    private var accValues = FloatArray(3)
    private var magValues = FloatArray(3)
    private val r = FloatArray(9)
    private val values = FloatArray(3)
    override fun inflateChildBinding() = ActivityLevelBinding.inflate(layoutInflater)

    override fun init() {
        initTopBar("水平仪")
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
    }

    public override fun onResume() {
        super.onResume()
        accSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        mSensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL)
        mSensorManager.registerListener(this, magSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        // 取消方向传感器的监听
        mSensorManager.unregisterListener(this)
        super.onPause()
    }

    override fun onStop() {
        // 取消方向传感器的监听
        mSensorManager.unregisterListener(this)
        super.onStop()
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    override fun onSensorChanged(event: SensorEvent) {
        // 获取手机触发event的传感器的类型
        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> accValues = event.values.clone()
            Sensor.TYPE_MAGNETIC_FIELD -> magValues = event.values.clone()
        }
        SensorManager.getRotationMatrix(r, null, accValues, magValues)
        SensorManager.getOrientation(r, values)

        // 获取　沿着Z轴转过的角度
        val azimuth = values[0]

        // 获取　沿着X轴倾斜时　与Y轴的夹角
        val pitchAngle = values[1]

        // 获取　沿着Y轴的滚动时　与X轴的角度
        //此处与官方文档描述不一致，所在加了符号（https://developer.android.google.cn/reference/android/hardware/SensorManager.html#getOrientation(float[], float[])）
        val rollAngle = -values[2]
        onAngleChanged(rollAngle, pitchAngle, azimuth)
    }

    /**
     * 角度变更后显示到界面
     *
     * @param rollAngle
     * @param pitchAngle
     * @param azimuth
     */
    @SuppressLint("SetTextI18n")
    private fun onAngleChanged(rollAngle: Float, pitchAngle: Float, azimuth: Float) {
        binding.levelView.setAngle(rollAngle.toDouble(), pitchAngle.toDouble())
        binding.tvvHorz.text = (Math.toDegrees(rollAngle.toDouble()).toInt().toString()) + "°"
        binding.tvvVertical.text = (Math.toDegrees(pitchAngle.toDouble()).toInt().toString()) + "°"
    }
}