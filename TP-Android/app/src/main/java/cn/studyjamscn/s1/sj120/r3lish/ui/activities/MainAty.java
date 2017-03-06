package cn.studyjamscn.s1.sj120.r3lish.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import cn.studyjamscn.s1.sj120.r3lish.App;
import cn.studyjamscn.s1.sj120.r3lish.R;
import cn.studyjamscn.s1.sj120.r3lish.base.AppActionBar;
import cn.studyjamscn.s1.sj120.r3lish.base.BaseAty;
import cn.studyjamscn.s1.sj120.r3lish.base.BaseFgm;
import cn.studyjamscn.s1.sj120.r3lish.entity.UserEntity;
import cn.studyjamscn.s1.sj120.r3lish.networks.LogoutRequest;
import cn.studyjamscn.s1.sj120.r3lish.ui.fragments.AboutFgm;
import cn.studyjamscn.s1.sj120.r3lish.ui.fragments.HomePageFgm;
import cn.studyjamscn.s1.sj120.r3lish.ui.fragments.OrderPostedFgm;
import cn.studyjamscn.s1.sj120.r3lish.ui.fragments.OrderTookFgm;
import cn.studyjamscn.s1.sj120.r3lish.ui.view.NonSlidePager;
import cn.studyjamscn.s1.sj120.r3lish.utils.AppUtil;

/**
 * 主界面
 * Created by r3lis on 2016/3/13.
 */
public class MainAty extends BaseAty implements NavigationView.OnNavigationItemSelectedListener {

    public static final int HOME_PAGE = 0, ORDER_POSTED = 1, ORDER_TOOK = 2, ABOUT = 3;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.fab)
    public FloatingActionButton fab;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

    @Bind(R.id.nav_view)
    NavigationView navigationView;

    @Bind(R.id.vp)
    NonSlidePager vp;

    ImageView ivHead;

    TextView tvName;

    TextView tvPhone;

    ArrayList<BaseFgm> fgms;

    @Override
    protected int layoutResId() {
        return R.layout.aty_main;
    }

    @Override
    protected void initActionBar(AppActionBar appActionBar) {
        appActionBar.hide();
    }

    @Override
    protected void initViews() {
        toolbar.setTitle("首页");
        this.setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        ivHead = (ImageView) headerView.findViewById(R.id.ivHead);
        tvName = (TextView) headerView.findViewById(R.id.tvName);
        tvPhone = (TextView) headerView.findViewById(R.id.tvPhone);
        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivityForResult(MineAty.class, 0);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivityForResult(MineAty.class, 0);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        ivHead.setClickable(true);
        ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivityForResult(MineAty.class, 0);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        fgms = new ArrayList<>();
        fgms.add(new HomePageFgm());
        fgms.add(new OrderPostedFgm());
        fgms.add(new OrderTookFgm());
        fgms.add(new AboutFgm());
        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        navigationView.setCheckedItem(R.id.nav_home_page);
        updateUserData();
    }

    private void updateUserData(){
        ivHead.setImageBitmap(AppUtil.toRoundBitmap(UserEntity.getLogo()));
        tvName.setText(UserEntity.getName());
        tvPhone.setText(UserEntity.getPhone());
        ((HomePageFgm)fgms.get(0)).requestData();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        selectPage(item.getItemId());
        if (drawer == null) {
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateUserData();
    }

    @Override
    public void onBackPressed() {
        if (drawer == null) {
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("退出")
                    .setMessage("是否退出【传送】APP？")
                    .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            LogoutRequest request = new LogoutRequest();
                            request.request();
                            App.clearActivities();
                            finish();
                        }
                    }).setPositiveButton("取消", null)
                    .create()
                    .show();
        }
    }

    @OnClick({R.id.fab})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                goActivityForResult(AddOrderAty.class, 0xbb);
                break;
        }
    }

    /**
     * 选择页卡
     *
     * @param
     */
    public void selectPage(int id) {
        switch (id) {
            case R.id.nav_home_page:
                vp.setCurrentItem(HOME_PAGE);
                toolbar.setTitle("首页");
                fab.show();
                break;
            case R.id.nav_take:
                vp.setCurrentItem(ORDER_TOOK);
                toolbar.setTitle("我接的单");
                fab.hide();
                break;
            case R.id.nav_issue:
                vp.setCurrentItem(ORDER_POSTED);
                toolbar.setTitle("我发的单");
                fab.hide();
                break;
            case R.id.nav_about:
                vp.setCurrentItem(ABOUT);
                toolbar.setTitle("关于");
                fab.hide();
                break;
        }

    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fgms.get(position);
        }

        @Override
        public int getCount() {
            return fgms.size();
        }
    }

}
