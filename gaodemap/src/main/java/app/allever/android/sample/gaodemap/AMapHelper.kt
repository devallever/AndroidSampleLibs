package app.allever.android.sample.gaodemap

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import app.allever.android.lib.core.function.permission.PermissionHelper
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMapUtils
import com.amap.api.maps.MapsInitializer
import com.amap.api.maps.model.LatLng

@SuppressLint("StaticFieldLeak")
object AMapHelper {

    private var mIsInit = false

    private lateinit var mContext: Context
    fun init(context: Context) {
        if (mIsInit) {
            return
        }
        mContext = context.applicationContext
        MapsInitializer.updatePrivacyShow(context, true, true)
        MapsInitializer.updatePrivacyAgree(context, true)
    }
    private val mLocationManager by lazy {
        (mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
    }

    fun isOpen(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            mLocationManager.isLocationEnabled
        } else {
            mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }
    }

//    @SuppressLint("StaticFieldLeak")
//    @Volatile
//    private var aMapLocationClient: AMapLocationClient? = null
//
//    /**
//     * 权限是否被拒绝过了
//     */
//    @Volatile
//    private var isPermissionDeny = false
//
//    /**
//     * [FragmentActivity]请求定位
//     */
//    fun requestLocation(
//        activity: FragmentActivity,
//        useCache: Boolean = true,
//        force: Boolean = false,
//        failListener: ((isPermissionOpen: Boolean) -> Unit)? = null,
//        interceptor: IPermissionInterceptor = object : IPermissionInterceptor {},
//        listener: AMapLocationListener,
//    ) {
//        requestLocationInternal(
//            activity.applicationContext,
//            useCache,
//            force,
//            XXPermissions.with(activity),
//            listener,
//            failListener,
//            interceptor
//        )
//    }
//
//    /**
//     * [Fragment]请求定位
//     */
//    fun requestLocation(
//        fragment: Fragment,
//        useCache: Boolean = true,
//        force: Boolean = false,
//        interceptor: IPermissionInterceptor = object : IPermissionInterceptor {},
//        failListener: ((isPermissionOpen: Boolean) -> Unit)? = null,
//        listener: AMapLocationListener
//    ) {
//        requestLocationInternal(
//            fragment.requireContext(),
//            useCache,
//            force,
//            XXPermissions.with(fragment),
//            listener,
//            failListener,
//            interceptor
//        )
//    }
//
//    /**
//     * 实际请求定位方法
//     */
//    private fun requestLocationInternal(
//        context: Context,
//        useCache: Boolean,
//        force: Boolean,
//        permissionX: XXPermissions,
//        listener: AMapLocationListener,
//        failListener: ((isPermissionOpen: Boolean) -> Unit)?,
//        interceptor: IPermissionInterceptor,
//    ) {
//        val time = MmkvHelper.getAppMmkv().takeLong(MmkvHelper.PERMISSION_LOCATION, 0)
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && context.checkSelfPermission(
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) == PackageManager.PERMISSION_GRANTED
//            ) { // 权限已经授权了
//                getLocation(context, useCache, permissionX, listener, failListener, interceptor)
//            } else {
//                if (force) {
//                    // 强制请求授权
//                    getLocation(context, useCache, permissionX, listener, failListener, interceptor)
//                } else {
//                    // 超过2小时后才会重新触发权限请求
//                    if (System.currentTimeMillis() / 1000 - time >= 3600 * 2 && !isPermissionDeny) {
//                        MmkvHelper.getAppMmkv()[MmkvHelper.PERMISSION_LOCATION] =
//                            System.currentTimeMillis() / 1000
//
//                        getLocation(
//                            context, useCache, permissionX, listener, failListener, interceptor
//                        )
//                    } else {
//                        failListener?.invoke(true)
//                    }
//                }
//
//            }
//        } else {
//            getLocation(context, useCache, permissionX, listener, failListener, interceptor)
//        }
//    }
//
//    private fun getLocation(
//        context: Context,
//        useCache: Boolean,
//        permissionX: XXPermissions,
//        listener: AMapLocationListener,
//        failListener: ((isPermissionOpen: Boolean) -> Unit)?,
//        interceptor: IPermissionInterceptor,
//    ) {
//        permissionX.permission(
//            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
//        ).interceptor(interceptor).request { permissions, all ->
//            Timber.v("grantedList -> $permissions")
//            if (all) {
//                isPermissionDeny = false
//
//                // 检查系统是否开启定位功能
//                val systemLocationEnable = systemLocationEnable(context)
//                if (systemLocationEnable) {
//                    getClient(context).apply {
//                        if (useCache) {
//                            lastKnownLocation?.apply {
//                                Timber.v("返回已知位置")
//                                listener.onLocationChanged(this)
//                                if (!isStarted) {
//                                    // 尝试更新定位
//                                    startLocation()
//                                }
//                                return@request
//                            }
//                        }
//                        Timber.v("开始定位")
//                        setLocationListener(listener)
//                        startLocation()
//                    }
//                } else {
//                    Timber.w("系统未开启定位功能")
//
//                    failListener?.invoke(false)
//                }
//            } else {
//                isPermissionDeny = true
//
//                Toaster.show("拒绝授权，定位失败")
//
//                failListener?.invoke(true)
//            }
//        }
//    }
//
//    private fun getClient(context: Context): AMapLocationClient {
//        aMapLocationClient?.apply { return this }
//
//        return synchronized(this) {
//            aMapLocationClient?.apply {
//                return@synchronized this
//            }
//            AMapLocationClient.updatePrivacyShow(context, true, true)
//            AMapLocationClient.updatePrivacyAgree(context, true)
//            val created = AMapLocationClient(context.applicationContext).apply {
//                setLocationOption(AMapLocationClientOption().apply {
//                    // 高精度模式
//                    locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
//                    // 单次定位模式
//                    isOnceLocation = true
//                    // 获取最近3s内精度最高的一次定位结果
//                    isOnceLocationLatest = true
//                    // 是否允许模拟位置
//                    isMockEnable = BuildConfig.DEBUG
//                    // 单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
//                    httpTimeOut = 8000
//                })
//            }
//            aMapLocationClient = created
//            return@synchronized created
//        }
//    }
//
//    /**
//     * 计算两点距离
//     * @param source 起点经纬度
//     * @param dest 终点经纬度
//     * @param unit 单位，默认km
//     * @return 距离
//     */
//    fun distance(
//        source: LatLng?, dest: LatLng?, @DistanceUnit unit: Int = DistanceUnit.KM
//    ): Float? {
//        if (source != null && dest != null) {
//            return AMapUtils.calculateLineDistance(source, dest) / unit
//        }
//
//        return null
//    }
//
//    fun systemLocationEnable(context: Context): Boolean {
//        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            locationManager.isLocationEnabled
//        } else {
//            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
//                LocationManager.NETWORK_PROVIDER
//            )
//        }
//    }
//
//    fun requestLocationPermission(fragment: Fragment, success: () -> Unit) {
//        //打开定位
//        if (!isOpen()) {
//            //打开权限
//            Toast.makeText(mContext, "请打开定位", Toast.LENGTH_SHORT).show()
//            return
//        }
//        //请求权限
//        PermissionHelper.requestPermission()
//        XXPermissions.with(fragment).permission(
//            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
//        ).request { permissions, allGranted ->
//            if (allGranted) {
//                success.invoke()
//            }
//        }
//    }
}
