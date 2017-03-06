package cn.studyjamscn.s1.sj120.r3lish.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import cn.studyjamscn.s1.sj120.r3lish.App;
import cn.studyjamscn.s1.sj120.r3lish.R;
import cn.studyjamscn.s1.sj120.r3lish.base.BaseFgm;
import cn.studyjamscn.s1.sj120.r3lish.utils.AppToast;
import cn.studyjamscn.s1.sj120.r3lish.utils.AppUtil;
import cn.studyjamscn.s1.sj120.r3lish.utils.ScoreUtils;

/**
 * Created by r3lis on 2016/3/15.
 */
public class AboutFgm extends BaseFgm {

    @Bind(R.id.tvVersion)
    TextView tvVersion;

    @Override
    protected int layoutResId() {
        return R.layout.fgm_about;
    }


    @Override
    protected void initView() {
        tvVersion.setText(getString(R.string.version, "v" + AppUtil.getAppVersionName(getActivity())));
    }

    @OnClick({R.id.tvCommentUs, R.id.tvSuggest})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.tvCommentUs:
                final ArrayList<String> markets = ScoreUtils.InstalledAPPs(getActivity());
                if (markets.size() >= 0 && markets.contains(App.GOOGLE_PLAY)) {
                    ScoreUtils.launchAppDetail(getActivity().getPackageName(), App.GOOGLE_PLAY);
                } else {
                    AppToast.showShort("您的手机上未安装GooglePlay！");
                }
                break;
            case R.id.tvSuggest:
                Intent data = new Intent(Intent.ACTION_SENDTO);
                data.setData(Uri.parse("mailto:relish-wang@gmail.com"));
                data.putExtra(Intent.EXTRA_SUBJECT, "来自【" + getString(R.string.app_name) + "】的用户反馈");
                data.putExtra(Intent.EXTRA_TEXT, "请问您对本应用【" + getString(R.string.app_name) + "】有什么意见或建议呢？\n");
                startActivity(data);
                break;
        }
    }
}
