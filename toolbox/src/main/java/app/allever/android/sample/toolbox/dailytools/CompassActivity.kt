package app.allever.android.sample.toolbox.dailytools

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Vibrator
import android.widget.LinearLayout
import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.toolbox.databinding.ActivityCompassBinding

class CompassActivity : BaseActivity<ActivityCompassBinding, BaseViewModel>() {
    private var directiona = "UNKNOWN"
    private lateinit var mSensorManager: SensorManager
    private val mDirectionText = arrayOf("北", "东北", "东", "东南", "南", "西南", "西", "西北")
    override fun inflateChildBinding(): ActivityCompassBinding {
        return ActivityCompassBinding.inflate(
            layoutInflater
        )
    }

    override fun init() {
        initTopBar("指南针", true, null)
        binding.compass.layoutParams = LinearLayout.LayoutParams(
            this.resources.displayMetrics.widthPixels,
            this.resources.displayMetrics.widthPixels
        )
        mSensorManager = this.getSystemService(SENSOR_SERVICE) as SensorManager
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) == null) {
            //Toast.makeText(this,"你的设备不支持陀螺仪",Toast.LENGTH_SHORT).show();
        }
        val mVibrator = this.getSystemService(VIBRATOR_SERVICE) as Vibrator
        val sensorListener: SensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val _rotationMatrix = FloatArray(16)
                SensorManager.getRotationMatrixFromVector(_rotationMatrix, event.values)
                val _remappedRotationMatrix = FloatArray(16)
                SensorManager.remapCoordinateSystem(
                    _rotationMatrix,
                    SensorManager.AXIS_X,
                    SensorManager.AXIS_Z,
                    _remappedRotationMatrix
                )
                val _orientations = FloatArray(3)
                SensorManager.getOrientation(_remappedRotationMatrix, _orientations)
                for (_i in 0..2) {
                    _orientations[_i] = Math.toDegrees(
                        _orientations[_i].toDouble()
                    ).toFloat()
                }
                val _x = _orientations[0].toDouble()
                val _y = _orientations[1].toDouble()
                val _z = _orientations[2].toDouble()
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
        }
        mSensorManager.registerListener(
            sensorListener,
            mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR),
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    private fun initSensor() {
        mSensorManager = this.getSystemService(SENSOR_SERVICE) as SensorManager
        if (mSensorManager != null) {
            val sensor = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION)
            //注册监听
            mSensorManager!!.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }

    private val sensorEventListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val dirAngel = event.values[0]
            binding.compass.setDirectionAngle(dirAngel)
            directiona = mDirectionText[(dirAngel + 22.5f).toInt() % 360 / 45]
            binding.direction.text = directiona
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            if (accuracy != SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM
                && accuracy != SensorManager.SENSOR_STATUS_ACCURACY_HIGH
            ) {
            }
        }
    }

    public override fun onResume() {
        super.onResume()
        initSensor()
    }

    public override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(sensorEventListener)
    }
}