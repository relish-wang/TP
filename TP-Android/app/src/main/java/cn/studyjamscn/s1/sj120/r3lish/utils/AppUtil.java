package cn.studyjamscn.s1.sj120.r3lish.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

/**
 * App的工具类
 *
 * @author 王鑫
 *         Created by 王鑫 on 2015/7/19.
 */
public class AppUtil {

    public static final String[] STATE = {"未接单", "已接单", "已完成"};

    /**
     * 修改电话号码格式为 xxx-xxxx-xxxx
     *
     * @param s
     * @return
     */
    public static String formatPhone(String s) {
        s = new StringBuilder(convertformatPhone(s)).reverse().toString();
        if (s.length() <= 8) {//x{3,8}
            return new StringBuilder(s).reverse().toString();
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                sb.append(s.charAt(i));
                if (i % 4 == 3 && i != s.length() - 1) {
                    sb.append("-");
                }
            }
            return sb.reverse().toString();
        }
    }

    /**
     * 修改电话号码格式为 xxxxxxxxxxx
     *
     * @param s
     * @return
     */
    public static String convertformatPhone(String s) {
        return s.replace("-", "");
    }

    /**
     * 修改字符串格式为 xxxxx...
     *
     * @param s
     * @return
     */
    public static String cutTail(String s, int len) {
        try {
            int end = 0, count = 0;
            for (int i = 0; i < s.length(); i++) {
                count += ("" + s.charAt(i)).getBytes("UTF-8").length;
                if (count >= len) {
                    end = i;
                    break;
                }
            }
            if (s.length() - 1 == end) {
                return s;
            } else if (count >= len) {
                return s.substring(0, end) + "...";
            } else {
                return s;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e("wx", "string cutTail failed");
            return s;
        }
    }

    /**
     * 计算字符串的字节数
     *
     * @param s
     * @return
     */
    public static int getStringBitLength(String s) {
        int count = 0;
        try {
            for (int i = 0; i < s.length(); i++) {
                count += ("" + s.charAt(i)).getBytes("UTF-8").length;
            }
        } catch (Exception e) {
            Log.e("wx", "string get bitLength failed");
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 将字符串转换成Bitmap类型
     *
     * @param string 字符串
     * @return bitmap 图像
     */
    public static Bitmap str2Bitmap(String string) {
        if (TextUtils.isEmpty(string))
            return null;
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AppLog.d("Bitmap", "Bitmap", (bitmap == null)+"");
        return bitmap;
    }

    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2 - 5;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2 - 5;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst_left + 15, dst_top + 15, dst_right - 20, dst_bottom - 20);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    /**
     * 将Bitmap转换成字符串
     *
     * @param bitmap 图像
     * @return string 字符串
     */
    public static String bitmap2Str(Bitmap bitmap) {
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    public static String path2Str(String path) {
        if (path.length() > 0) {
            File file = new File(path);
            if (!file.exists()) {
                return null;
            }
            BitmapFactory.Options opt = new BitmapFactory.Options();
            long fileSize = file.length();
            int maxSize = 2 * 1024 * 1024;
            if (fileSize <= maxSize) {
                opt.inSampleSize = 1;
            } else if (fileSize <= maxSize * 4) {
                opt.inSampleSize = 2;
            } else {
                long times = fileSize / maxSize;
                opt.inSampleSize = (int) (Math.log(times) / Math.log(2.0)) + 1;//Math.log返回以e为底的对数</strong>
            }

            Bitmap mLoadedBitmap = BitmapFactory.decodeFile(path, opt);//opt为缩小的倍数</strong>
            return bitmap2Str(mLoadedBitmap);
        } else
            return null;
    }

    public static boolean isPhoneFormatFixed(String phone) {
        return Pattern.compile("^1[0-9]{10}$").matcher(phone).matches();
    }

    public static boolean isNameChinese(String str) {
        return Pattern.compile("^[\u4e00-\u9fa5]{2,4}$").matcher(str).matches();
    }

    /**
     * 检查是否存在SDCard
     *
     * @return 是否存在
     */
    public static boolean isSDcardExist() {
        return TextUtils.equals(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取App版本号
     *
     * @param context 上下文
     * @return App版本号
     */
    public static String getAppVersionName(Context context) {
        return getAppVersionName(context, context.getPackageName());
    }

    /**
     * 获取App版本号
     *
     * @param context     上下文
     * @param packageName 包名
     * @return App版本号
     */
    public static String getAppVersionName(Context context, String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取App图标
     *
     * @param context 上下文
     * @return App图标
     */
    public static Drawable getAppIcon(Context context) {
        return getAppIcon(context, context.getPackageName());
    }

    /**
     * 获取App图标
     *
     * @param context     上下文
     * @param packageName 包名
     * @return App图标
     */
    public static Drawable getAppIcon(Context context, String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isSpace(String s) {
        return (s == null || s.trim().length() == 0);
    }
}


