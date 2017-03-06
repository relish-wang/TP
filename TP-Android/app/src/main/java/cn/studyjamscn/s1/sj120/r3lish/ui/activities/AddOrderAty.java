package cn.studyjamscn.s1.sj120.r3lish.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.OnClick;
import cn.studyjamscn.s1.sj120.r3lish.R;
import cn.studyjamscn.s1.sj120.r3lish.base.AppActionBar;
import cn.studyjamscn.s1.sj120.r3lish.base.BaseAty;
import cn.studyjamscn.s1.sj120.r3lish.base.BaseRequest;
import cn.studyjamscn.s1.sj120.r3lish.entity.UserEntity;
import cn.studyjamscn.s1.sj120.r3lish.networks.PostOrderRequest;
import cn.studyjamscn.s1.sj120.r3lish.utils.AppToast;

/**
 * Created by r3lis on 2016/4/7.
 */
public class AddOrderAty extends BaseAty {

    @Bind(R.id.tvTimeGet)
    TextView tvTimeGet;
    @Bind(R.id.tvTimeSend)
    TextView tvTimeSend;

    @Bind(R.id.etSource)
    AutoCompleteTextView etSource;
    @Bind(R.id.etDestination)
    AutoCompleteTextView etDestination;
    @Bind(R.id.etCost)
    AutoCompleteTextView etCost;
    @Bind(R.id.etContent)
    AutoCompleteTextView etContent;
    @Bind(R.id.btnPostOrder)
    Button btnPostOrder;


    LayoutInflater inflater;

    @Override
    protected int layoutResId() {
        return R.layout.aty_add_order;
    }

    @Override
    protected void initActionBar(AppActionBar appActionBar) {
        appActionBar.setTitleText("发布订单");
    }

    @Override
    protected void initViews() {
        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        btnPostOrder.setTextColor(ContextCompat.getColor(this, R.color.white));
        etDestination.setText(UserEntity.getAddress());
        tvTimeGet.setText("11:30~12:20");
        Date d = new Date();
        tvTimeSend.setText(d.getHours() + ":" + d.getMinutes() + "~22:30");
        etContent.setText("无");
    }

    @OnClick(R.id.btnPostOrder)
    public void postOrder() {
        String timeGet = tvTimeGet.getText().toString();
        String timeSend = tvTimeSend.getText().toString();
        String source = etSource.getText().toString();
        String destination = etDestination.getText().toString();
        Integer cost = Integer.parseInt(etCost.getText().toString());
        String content = etContent.getText().toString();
        if (TextUtils.isEmpty(timeGet)) {
            AppToast.showShort("请填写收货时间");
            return;
        }
        if (TextUtils.isEmpty(timeSend)) {
            AppToast.showShort("请填写取货时间");
            return;
        }
        if (TextUtils.isEmpty(source)) {
            AppToast.showShort("请填写取货地点");
            return;
        }
        if (TextUtils.isEmpty(destination)) {
            AppToast.showShort("请填写收货地点");
            return;
        }
        if (TextUtils.isEmpty(etCost.getText().toString())) {
            AppToast.showShort("请填写热心值");
            return;
        }
        if (TextUtils.isEmpty(content)) {
            AppToast.showShort("请填写取货短信");
            return;
        }


        showLoading(true);
        PostOrderRequest request = new PostOrderRequest(
                source, timeGet, destination, timeSend, content, cost);
        request.setOnResponseListener(new BaseRequest.OnResponseListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                setResult(0xbb);//// FIXME: 2016/4/7
                finish();
                showLoading(false);
            }

            @Override
            public void onFail(String message) {
                AppToast.showShort(message);
                showLoading(false);
            }
        });
        request.request();
    }

    @OnClick({R.id.tvTimeGet, R.id.tvTimeSend})
    public void pickTime(View v) {
        switch (v.getId()) {
            case R.id.tvTimeGet:
                AlertDialog.Builder getDialog = new AlertDialog.Builder(this);
                LinearLayout llGet = (LinearLayout) inflater.inflate(R.layout.dialog_time_picker, null);
                getDialog.setView(llGet);
                final TimePicker from = (TimePicker) llGet.findViewById(R.id.timePickerFrom);
                final TimePicker to = (TimePicker) llGet.findViewById(R.id.timePickerTo);
                setTimePick(tvTimeGet.getText().toString(), from, to);
                from.setIs24HourView(true);
                to.setIs24HourView(true);
                getDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String time;
                        if (Build.VERSION.SDK_INT >= 23) {
                            time = String.format("%02d", from.getHour()) + ":" + String.format("%02d", from.getMinute()) + " ~ "
                                    + String.format("%02d", to.getHour()) + ":" + String.format("%02d", to.getMinute());
                        } else {
                            time = String.format("%02d", from.getCurrentHour()) + ":" + String.format("%02d", from.getCurrentMinute()) + " ~ "
                                    + String.format("%02d", to.getCurrentHour()) + ":" + String.format("%02d", to.getCurrentMinute());
                        }
                        tvTimeGet.setText(time);
                    }
                });
                getDialog.setNegativeButton("取消", null);
                getDialog.create().show();
                break;
            case R.id.tvTimeSend:
                AlertDialog.Builder sendDialog = new AlertDialog.Builder(this);
                LinearLayout llSend = (LinearLayout) inflater.inflate(R.layout.dialog_time_picker, null);
                sendDialog.setView(llSend);
                final TimePicker from2 = (TimePicker) llSend.findViewById(R.id.timePickerFrom);
                final TimePicker to2 = (TimePicker) llSend.findViewById(R.id.timePickerTo);
                setTimePick(tvTimeSend.getText().toString(), from2, to2);
                from2.setIs24HourView(true);
                to2.setIs24HourView(true);
                sendDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String time;
                        if (Build.VERSION.SDK_INT >= 23) {
                            time = String.format("%02d", from2.getHour()) + ":" + String.format("%02d", from2.getMinute()) + " ~ "
                                    + String.format("%02d", to2.getHour()) + ":" + String.format("%02d", to2.getMinute());
                        } else {
                            time = String.format("%02d", from2.getCurrentHour()) + ":" + String.format("%02d", from2.getCurrentMinute()) + " ~ "
                                    + String.format("%02d", to2.getCurrentHour()) + ":" + String.format("%02d", to2.getCurrentMinute());
                        }
                        tvTimeSend.setText(time);
                    }
                });
                sendDialog.setNegativeButton("取消", null);
                sendDialog.create().show();
                break;
        }
    }

    private static void setTimePick(String text, TimePicker from, TimePicker to) {
        int[] num = new int[4];
        int i = 0;
        Matcher m = Pattern.compile("[0-9]+").matcher(text);
        while (m.find()) {
            num[i++] = Integer.parseInt(m.group());
        }
        if (Build.VERSION.SDK_INT >= 23) {
            from.setHour(num[0]);
            from.setMinute(num[1]);
            to.setHour(num[2]);
            to.setMinute(num[3]);
        } else {
            from.setCurrentHour(num[0]);
            from.setCurrentMinute(num[1]);
            to.setCurrentHour(num[2]);
            to.setCurrentMinute(num[3]);
        }
    }
}
