//package app.allever.android.learning.audiovideo.kernel
//
///***
// * 抽象工厂类
// */
//abstract class AbsPlayerFactory {
//    companion object {
//        inline fun <reified F> create(): F = F::class.java.newInstance()
//    }
//
//    abstract fun createPlayer(): AbsPlayer
//}