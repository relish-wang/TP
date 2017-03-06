package cn.studyjamscn.s1.sj120.r3lish.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;
import cn.studyjamscn.s1.sj120.r3lish.R;
import cn.studyjamscn.s1.sj120.r3lish.base.AppActionBar;
import cn.studyjamscn.s1.sj120.r3lish.base.BaseAty;
import cn.studyjamscn.s1.sj120.r3lish.base.BaseRequest;
import cn.studyjamscn.s1.sj120.r3lish.entity.UserEntity;
import cn.studyjamscn.s1.sj120.r3lish.networks.ModifyAddressRequest;
import cn.studyjamscn.s1.sj120.r3lish.networks.ModifyLogoRequest;
import cn.studyjamscn.s1.sj120.r3lish.networks.ModifyNameRequest;
import cn.studyjamscn.s1.sj120.r3lish.networks.ModifyRealNameRequest;
import cn.studyjamscn.s1.sj120.r3lish.utils.AppToast;
import cn.studyjamscn.s1.sj120.r3lish.utils.AppUtil;

/**
 * 修改个人信息
 * Created by r3lis on 2016/4/5.
 */
public class ModifyMineAty extends BaseAty {

    /**
     * 头像名称
     */
    private static final String IMAGE_FILE_NAME = "head.jpg";

    /**
     * 请求码
     */
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;

    @Bind(R.id.ivHead)
    ImageView ivHead;
    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.tvRealName)
    TextView tvRealName;
    @Bind(R.id.tvAddress)
    TextView tvAddress;

    AutoCompleteTextView etData;
    LayoutInflater inflater;

    @Override
    protected int layoutResId() {
        return R.layout.aty_modify_mine;
    }

    @Override
    protected void initActionBar(AppActionBar appActionBar) {
        appActionBar.setTitleText("修改个人资料");
    }

    @Override
    protected void initViews() {
        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ivHead.setImageBitmap(AppUtil.toRoundBitmap(UserEntity.getLogo()));
        tvName.setText(UserEntity.getName());
        tvRealName.setText(UserEntity.getRealName());
        tvAddress.setText(UserEntity.getAddress());
    }


    @OnClick({R.id.trHead, R.id.trName, R.id.trRealName, R.id.trAddress})
    public void modify(View v) {
        switch (v.getId()) {
            case R.id.trHead:
                modifyLogo();
                break;
            case R.id.trName:
                modifyName();
                break;
            case R.id.trRealName:
                modifyRealName();
                break;
            case R.id.trAddress:
                modifyAddress();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) return;
        switch (requestCode) {
            case IMAGE_REQUEST_CODE:
                startPhotoZoom(data.getData());
                break;
            case CAMERA_REQUEST_CODE:
                if (AppUtil.isSDcardExist()) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory()
                                    +"/"+ IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                } else {
                    AppToast.showShort("未找到存储卡，无法存储照片！");
                }

                break;
            case RESULT_REQUEST_CODE:
                if (data != null) {
                    getImageToView(data);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 修改头像
     */
    public void modifyLogo() {
        new AlertDialog.Builder(this)
                .setTitle("设置头像")
                .setNegativeButton("选择本地照片", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intentFromGallery = new Intent();
                        intentFromGallery.setType("image/*"); // 设置文件类型
                        intentFromGallery
                                .setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intentFromGallery,
                                IMAGE_REQUEST_CODE);
                    }
                })
//                .setNegativeButton("拍照", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intentFromCapture = new Intent(
//                                MediaStore.ACTION_IMAGE_CAPTURE);
//                        // 判断存储卡是否可以用，可用进行存储
//                        if (AppUtil.isSDcardExist()) {
//                            intentFromCapture.putExtra(
//                                    MediaStore.EXTRA_OUTPUT,
//                                    Uri.fromFile(new File(Environment
//                                            .getExternalStorageDirectory(),
//                                            IMAGE_FILE_NAME)));
//                            startActivityForResult(intentFromCapture,
//                                    CAMERA_REQUEST_CODE);
//                        }
//                    }
//                })
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }


    /**
     * 修改用户名
     */
    public void modifyName() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dialog_modify, null);
        dialog.setView(layout);
        etData = (AutoCompleteTextView) layout.findViewById(R.id.et);
        etData.setHint("修改用户名");
        dialog.setPositiveButton("保存", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int which) {
                final String name = etData.getText().toString();
                if (!TextUtils.isEmpty(name)) {
                    ModifyNameRequest request = new ModifyNameRequest(name);
                    request.setOnResponseListener(new BaseRequest.OnResponseListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            UserEntity.setName(name);
                            tvName.setText(name);
                        }

                        @Override
                        public void onFail(String message) {
                            AppToast.showShort(message);
                        }
                    });
                    request.request();
                }
            }
        });
        dialog.setNegativeButton("取消", null);
        dialog.setTitle("修改用户名");
        dialog.show();
    }

    /**
     * 修改真实姓名
     */
    public void modifyRealName() {
        AlertDialog.Builder dialogRealName = new AlertDialog.Builder(this);
        LinearLayout layoutRealName = (LinearLayout) inflater.inflate(R.layout.dialog_modify, null);
        dialogRealName.setView(layoutRealName);
        etData = (AutoCompleteTextView) layoutRealName.findViewById(R.id.et);
        etData.setHint("修改真实姓名");
        dialogRealName.setPositiveButton("保存", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                final String realName = etData.getText().toString();
                if (!TextUtils.isEmpty(realName)) {
                    ModifyRealNameRequest request = new ModifyRealNameRequest(realName);
                    request.setOnResponseListener(new BaseRequest.OnResponseListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            UserEntity.setRealName(realName);
                            tvRealName.setText(realName);
                        }

                        @Override
                        public void onFail(String message) {
                            AppToast.showShort(message);
                        }
                    });
                    request.request();
                }
            }
        });
        dialogRealName.setNegativeButton("取消", null);
        dialogRealName.setTitle("修改真实姓名");
        dialogRealName.show();
    }

    /**
     * 修改收货地址
     */
    public void modifyAddress() {
        AlertDialog.Builder dialogAddress = new AlertDialog.Builder(this);
        LinearLayout layoutAddress = (LinearLayout) inflater.inflate(R.layout.dialog_modify, null);
        dialogAddress.setView(layoutAddress);
        etData = (AutoCompleteTextView) layoutAddress.findViewById(R.id.et);
        etData.setHint("修改收货地址");
        dialogAddress.setPositiveButton("保存", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                final String address = etData.getText().toString();
                if (!TextUtils.isEmpty(address)) {
                    ModifyAddressRequest request = new ModifyAddressRequest(address);
                    request.setOnResponseListener(new BaseRequest.OnResponseListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            UserEntity.setAddress(address);
                            tvAddress.setText(address);
                        }

                        @Override
                        public void onFail(String message) {
                            AppToast.showShort(message);
                        }
                    });
                    request.request();
                }
            }
        });
        dialogAddress.setNegativeButton("取消", null);
        dialogAddress.setTitle("修改收货地址");
        dialogAddress.show();
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        if(uri==null)return;
        Intent intent = new Intent();
    /*设置调用裁剪工具的action*/
        intent.setAction("com.android.camera.action.CROP");
    /*创建一个指向需要操作文件（filename）的文件流。（可解决无法“加载问题”）*/
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);// 输出图片大小
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param data Intent
     */
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            final Bitmap photo = extras.getParcelable("data");
            showLoading(true);
            ModifyLogoRequest request = new ModifyLogoRequest(photo);
            request.setOnResponseListener(new BaseRequest.OnResponseListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    ivHead.setImageBitmap(AppUtil.toRoundBitmap(photo));
                    UserEntity.setLogo(photo);
                    showLoading(false);
                }

                @Override
                public void onFail(String message) {
                    showLoading(false);
                }
            });
            request.request();
        }
    }

    @Override
    public void finish() {
        setResult(1);
        super.finish();
    }
}
