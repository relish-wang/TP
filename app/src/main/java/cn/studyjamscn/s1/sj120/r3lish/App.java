package cn.studyjamscn.s1.sj120.r3lish;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Set;

public class App extends Application {


    public static final String GOOGLE_PLAY = "com.android.vending";

    private static final String TAG = App.class.getSimpleName();

    /**
     * 屏幕宽度(px)
     */
    public static int screenWidth;
    /**
     * 屏幕高度(px)
     */
    public static int screenHeight;

    /**
     * 自定义Activity堆栈管理
     */
    private static HashMap<String, WeakReference<Activity>> mActivities = new HashMap<>();

    public static App CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = this;

        // 获取屏幕的宽高
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
    }

    public static synchronized void addActivity(Activity activity) {
        WeakReference<Activity> contextReference = new WeakReference<>(activity);
        mActivities.put(activity.getClass().getSimpleName(), contextReference);
    }

    public static synchronized Activity getActivity(String activityName) {
        WeakReference<Activity> activityReference = mActivities.get(activityName);
        if (activityReference != null) {
            return activityReference.get();
        }
        return null;
    }

    /**
     * 退出APP
     */
    public static synchronized void clearActivities() {
        Set<String> keySet = mActivities.keySet();
        String[] keyArray = new String[]{};
        keyArray = keySet.toArray(keyArray);
        removeActivities(keyArray);
    }

    public static synchronized void clearActivitiesWithout(String... activitiesName) {
        String[] keyArray = new String[]{};
        keyArray = mActivities.keySet().toArray(keyArray);

        for (String key : keyArray) {
            boolean isContain = false;
            for (String name : activitiesName) {
                if (key.equals(name)) {
                    isContain = true;
                    break;
                }
            }
            if (!isContain) {
                removeActivities(key);
            }
        }
    }

    public static synchronized void removeActivities(String... activitiesName) {
        for (String name : activitiesName) {
            WeakReference<Activity> activityWeakReference = mActivities.remove(name);
            Activity activity;
            if (activityWeakReference != null && (activity = activityWeakReference.get()) != null) {
                activity.finish();
                if (BuildConfig.DEBUG) Log.d(TAG, "Finish Activity : " + name);
            }
        }
    }
}
