package com.example.room.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.example.room.db.NetworkAppDatabase;
import com.example.room.db.NetworkApp;
import com.example.room.model.LocalApp;
import com.example.room.network.OkhttpUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class AppUtils {
    private static final String TAG = "AppInfoUtils";

    /**
     * 获取手机已安装应用列表
     * @param ctx
     * @param isFilterSystem 是否过滤系统应用
     * @return
     */
    // https://blog.csdn.net/wujiahang129/article/details/121969523
    public static ArrayList<LocalApp> getAppsAtLocal(Context ctx, boolean isFilterSystem) {
        ArrayList<LocalApp> apps = new ArrayList<>();
        PackageManager packageManager = ctx.getPackageManager();
        List<PackageInfo> list = packageManager.getInstalledPackages(0);
        for (PackageInfo p : list) {
            LocalApp app = new LocalApp();
            app.setLabel(packageManager.getApplicationLabel(p.applicationInfo).toString());
            app.setIcon(p.applicationInfo.loadIcon(packageManager));
            app.setPackageName(p.packageName);
            int flags = p.applicationInfo.flags;
            // 判断是否是属于系统的apk
            if ((flags & ApplicationInfo.FLAG_SYSTEM) != 0&&isFilterSystem) {
//                bean.setSystem(true);
            } else {
                apps.add(app);
            }
        }
        return apps;
    }

    public static void addApp(Context context, NetworkApp app) {
        NetworkAppDatabase.getInstance(context).getNetworkAppService().add(app);
    }

    public static List<NetworkApp> getAllApp(Context context) {
        return NetworkAppDatabase.getInstance(context).getNetworkAppService().getAll();
    }
    
    public static List<NetworkApp> getAppsAtNetwork(Context context) {
        ArrayList<NetworkApp> apps = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            String url = ConstantUtils.PREV_URL + "/" + i + ConstantUtils.NEXT_URL;
            apps.addAll(getApps(context, OkhttpUtils.getHtml(url)));
            Log.e(TAG, "getAppsAtNetwork: " + apps.size());
        }
        return apps;
    }

    private static List<NetworkApp> getApps(Context context, String html) {
        ArrayList<NetworkApp> apps = new ArrayList<>();
        Elements elements = Jsoup.parse(html).select("body > div.panel-box > div > div.clearfix.data-list.dataList.V-marony.Vmarony.masonry > div");
        for (Element element : elements) {
            // "div > a.img-box > img
            Element element1 = element.selectFirst("div > a.img-box > img");
            String src = element1.attr("src");
            String title = element1.attr("title");
            NetworkApp app = new NetworkApp();
            app.setUrl(src);
            app.setLabel(title);
            apps.add(app);
            addApp(context, app);
        }
        return apps;
    }
}
