package app.allever.android.sample.gaodemap

import app.allever.android.lib.mvvm.base.BaseMvvmFragment
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.project.databinding.FragmentGaoDeMapBaseBinding
import com.amap.api.maps.model.MyLocationStyle

class GaoDeBaseMapFragment : BaseMvvmFragment<FragmentGaoDeMapBaseBinding, BaseViewModel>() {
    override fun inflate() = FragmentGaoDeMapBaseBinding.inflate(layoutInflater)

    override fun init() {

        mBinding.apply {
            btnMyLocation.setOnClickListener {
                showMyLocation()
            }

            mapView.map.apply {
                isMyLocationEnabled = true// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
            }

            mapView.onCreate(arguments)
            showMyLocation()

            mapView.map.uiSettings.apply {
                isZoomControlsEnabled = false
            }


        }
    }

    override fun onResume() {
        super.onResume()
        mBinding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mBinding.mapView.onPause()
    }

    override fun onDestroy() {
        mBinding.mapView.onDestroy()
        super.onDestroy()
    }

    private fun showMyLocation() {
        mBinding.apply {
            mapView.map.apply {
                val myLocationStyle = MyLocationStyle()
                myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)
//                myLocationStyle.interval(2000) //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
                this.myLocationStyle = myLocationStyle//设置定位蓝点的Style
            }
        }
    }
}