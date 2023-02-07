package app.allever.android.sample.aop;

import me.ele.lancet.base.Origin;
import me.ele.lancet.base.annotations.Proxy;
import me.ele.lancet.base.annotations.TargetClass;

public class LancetUtils {
    @Proxy("d")
    @TargetClass("android.util.Log")
    public static int anyName(String tag, String msg){
        msg = msg + " lancet";
        return (int) Origin.call();
    }
}
