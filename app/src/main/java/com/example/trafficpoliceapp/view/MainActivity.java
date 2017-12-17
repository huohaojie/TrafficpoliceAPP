package com.example.trafficpoliceapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.trafficpoliceapp.R;
import com.example.trafficpoliceapp.entity.MyUser;
import com.example.trafficpoliceapp.ui.AboutActivity;
import com.example.trafficpoliceapp.ui.ChePaiActivity;
import com.example.trafficpoliceapp.ui.LocationActivity;
import com.example.trafficpoliceapp.ui.QrCodeActivity;
import com.example.trafficpoliceapp.ui.QueryActivity;
import com.example.trafficpoliceapp.ui.SettingActivity;
import com.example.trafficpoliceapp.ui.UserActivity;
import com.example.trafficpoliceapp.ui.WebActivity;
import com.example.trafficpoliceapp.ui.WechatActivity;
import com.example.trafficpoliceapp.utils.UtilTools;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private TextView nav_header_tv;
    //圆形头像
    private CircleImageView profile_image;


    private ViewPager mViewPager;
    //容器
    private List<View> mList=new ArrayList<>();
    private View view1,view2,view3;
    //小圆点
    private ImageView point1,point2,point3;
    //viewpager图片
    private ImageView pager_one,pager_two,pager_three;
    //lineatlayout
    private LinearLayout linearLayout_chepai,linearLayout_dengji,
    linearLayout_weizhang,linearLayout_daima,linearLayout_xianxing,linearLayout_dingwei,linearLayout_news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();



    }

    private void initView() {
        point1= (ImageView) findViewById(R.id.point1);
        point2= (ImageView) findViewById(R.id.point2);
        point3= (ImageView) findViewById(R.id.point3);

        view1 = View.inflate(this,R.layout.pager_one,null);
        view2 = View.inflate(this,R.layout.pager_two,null);
        view3 = View.inflate(this,R.layout.pager_three,null);

        pager_one= (ImageView) view1.findViewById(R.id.pager_one);
        pager_one.setOnClickListener(this);
        pager_two= (ImageView) view2.findViewById(R.id.pager_two);
        pager_two.setOnClickListener(this);
        pager_three= (ImageView) view3.findViewById(R.id.pager_three);
        pager_three.setOnClickListener(this);

        //设置默认的图片
        setPointImg(true,false,false);

        mViewPager = (ViewPager) findViewById(R.id.mViewPager);



        mList.add(view1);
        mList.add(view2);
        mList.add(view3);

        //设置适配器
        mViewPager.setAdapter(new MainAdapter());
        //监听ViewPager滑动
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            //pager切换
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        setPointImg(true,false,false);

                        break;
                    case 1:
                        setPointImg(false,true,false);

                        break;
                    case 2:
                        setPointImg(false,false,true);

                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //微信精选
        linearLayout_news = (LinearLayout) findViewById(R.id.linearLayout_news);
        linearLayout_news.setOnClickListener(this);
        //百度定位
        linearLayout_dingwei = (LinearLayout) findViewById(R.id.linearLayout_dingwei);
        linearLayout_dingwei.setOnClickListener(this);

        //车牌识别
        linearLayout_chepai = (LinearLayout) findViewById(R.id.linearLayout_chepai);
        linearLayout_chepai.setOnClickListener(this);

        //限行
        linearLayout_xianxing = (LinearLayout) findViewById(R.id.linearLayout_xianxing);
        linearLayout_xianxing.setOnClickListener(this);

        //违章
        linearLayout_weizhang = (LinearLayout) findViewById(R.id.linearlayout_weizhang);
        linearLayout_weizhang.setOnClickListener(this);

        NavigationView naviView = (NavigationView) findViewById(R.id.nav_view);

        nav_header_tv= (TextView)naviView.getHeaderView(0).findViewById(R.id.nav_header_tv);
        //设置具体值
        profile_image = (CircleImageView) naviView.getHeaderView(0).findViewById(R.id.profile_image);


        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        nav_header_tv.setText(userInfo.getUsername()+"");

        UtilTools.getImageToShare(this,profile_image);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //设置小圆点的选中状态
    private void setPointImg(boolean isCheck1,boolean isCheck2,boolean isCheck3){
        if (isCheck1){
            point1.setBackgroundResource(R.drawable.point_on);
        }else{
            point1.setBackgroundResource(R.drawable.point_off);
        }
        if (isCheck2){
            point2.setBackgroundResource(R.drawable.point_on);
        }else{
            point2.setBackgroundResource(R.drawable.point_off);
        }
        if (isCheck3){
            point3.setBackgroundResource(R.drawable.point_on);
        }else{
            point3.setBackgroundResource(R.drawable.point_off);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_personal) {
        Intent intent = new Intent(this,UserActivity.class);
        startActivity(intent);

        } else if (id == R.id.nav_scan) {
            Intent openCameraIntent = new Intent(this,CaptureActivity.class);
            startActivityForResult(openCameraIntent, 0);

        } else if (id == R.id.nav_share) {
            startActivity(new Intent(this, QrCodeActivity.class));

        } else if (id == R.id.nav_location) {

        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this,AboutActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pager_one:
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra("title",getString(R.string.pager_one_title));
                intent.putExtra("url",getString(R.string.pager_one_url));
                startActivity(intent);
                break;
            case R.id.pager_two:
                intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra("title",getString(R.string.pager_two_title));
                intent.putExtra("url",getString(R.string.pager_two_url));
                startActivity(intent);
                break;
            case R.id.pager_three:
                intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra("title",getString(R.string.pager_three_title));
                intent.putExtra("url",getString(R.string.pager_three_url));
                startActivity(intent);
                break;
            case R.id.linearLayout_dingwei:
                startActivity(new Intent(MainActivity.this, LocationActivity.class));
                break;
            case R.id.linearLayout_news:
                startActivity(new Intent(MainActivity.this, WechatActivity.class));
                break;
            case R.id.linearLayout_chepai:
                ChePaiActivity.start(this);
                break;
            case R.id.linearlayout_weizhang:
                //TODO 违章activity
                break;
            case R.id.linearLayout_xianxing:
                QueryActivity.start(this);
                break;
        }


    }



    private class MainAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager)container).addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager)container).removeView(mList.get(position));
            //super.destroyItem(container, position, object);
        }
    }
}
