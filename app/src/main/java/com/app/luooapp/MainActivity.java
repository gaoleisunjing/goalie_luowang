package com.app.luooapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.DaoMaster;
import com.app.UserEntityDao;
import com.app.activityfragment.ActivityFragment;
import com.app.forumfragment.ForumFragment;
import com.app.fragment.DiscoverFragment;
import com.app.service.MediaPlayerService;
import com.facebook.drawee.view.SimpleDraweeView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DiscoverFragment.OnIconClickListener {
    private DrawerLayout mDrawerLayout;
    private FragmentTransaction mTransaction;
    private DiscoverFragment mDiscoverFragment;
    private ForumFragment mForumFragment;
    private ActivityFragment mAtivityFragment;
    private Fragment mContent;
    private TextView mDiscover;
    private TextView mForum;
    private TextView mEvent;
    private TextView musicName;//音乐名
    private TextView musicContent;//音乐内容
    private RelativeLayout mLeft;
    private RelativeLayout mUserLogin;//用户登录
    private SimpleDraweeView mUserIcon;//用户头像
    private TextView mUserName;//用户名字
    private ImageView musicStop;//点击暂停
    private ImageView musicMore;//更多
    private ProgressBar musicProgress;//播放进度条
    private RelativeLayout musicLayout;
    Notification notification;
    NotificationManager manager;
    private Button mButtonfeedback;
    private static boolean isPlaying;//判断是否在播放
    private int progress;//记录当前播放位置
    Intent intent;
    static int cur;
    static int max;
    static int curProgress = 0;
    TimeReceiver receiver;
    private String musictitle, musiccontent1;
    //    private QqResultReceiver mQqResultReceiver;
    private DaoMaster mDaoMaster;
    private UserEntityDao mUserEntityDao;
    private Cursor mCursor;
    private SQLiteDatabase database;


    private static final int REQUEST_CODE = 258;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment();
        initListener();
        initGenerate();//初始化dao
        userLogin();//判断是否登录
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification = new Notification(R.mipmap.ic_launcher, "音乐播放器", System.currentTimeMillis());
        intent = new Intent(this, MediaPlayerService.class);
        receiver = new TimeReceiver();
        registerReceiver(receiver, new IntentFilter("com.kygo.time"));//注册时间广播接收器

        //注册接收qq的广播
//        mQqResultReceiver = new QqResultReceiver();
//        registerReceiver(mQqResultReceiver, new IntentFilter("com.winter.qq"));
    }

    private void initGenerate() {

        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(getApplicationContext(),
                "user.db", null);
        database = openHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(database);
        mUserEntityDao = mDaoMaster.newSession().getUserEntityDao();
        mCursor = database.query(mUserEntityDao.getTablename(),
                mUserEntityDao.getAllColumns(),
                null, null, null, null, null);

    }

    private void initFragment() {
        mDiscoverFragment = new DiscoverFragment();
        mForumFragment = new ForumFragment();
        mAtivityFragment = new ActivityFragment();
        mTransaction = getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.layout_fragment_container, mDiscoverFragment);
        mTransaction.commit();
        mContent = mDiscoverFragment;
    }

    private void initListener() {
        mDiscover.setOnClickListener(this);
        mForum.setOnClickListener(this);
        mEvent.setOnClickListener(this);
        mDiscoverFragment.setOnIconClickListener(this);
        musicStop.setOnClickListener(this);
        mUserLogin.setOnClickListener(this);

    }

    /**
     * 修改显示的内容 不会重新加载
     **/
    public void switchContent(Fragment to) {
        if (mContent != to) {
            mTransaction = getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                mTransaction.hide(mContent)
                        .add(R.id.layout_fragment_container, to)
                        .commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                mTransaction
                        .hide(mContent)
                        .show(to)
                        .commit(); // 隐藏当前的fragment，显示下一个
            }
            mContent = to;//将当前fragment赋予mContent
        }
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.layout_drawer);
        mDiscover = (TextView) findViewById(R.id.tv_discover);
        mForum = (TextView) findViewById(R.id.tv_forum);
        mEvent = (TextView) findViewById(R.id.tv_event);
        mLeft = (RelativeLayout) findViewById(R.id.layout_left);
        musicName = (TextView) findViewById(R.id.tv_music_title);
        musicContent = (TextView) findViewById(R.id.tv_music_content);
        musicStop = (ImageView) findViewById(R.id.iv_music_isplaying);
        musicMore = (ImageView) findViewById(R.id.iv_music_more);
        musicProgress = (ProgressBar) findViewById(R.id.pb_music);
        musicLayout = (RelativeLayout) findViewById(R.id.layout_music_container);
        mUserLogin = (RelativeLayout) findViewById(R.id.layout_drawer_userMsg);
        mButtonfeedback = (Button) findViewById(R.id.btn_sys_feedback);
        mButtonfeedback.setOnClickListener(this);
        mUserIcon = (SimpleDraweeView) findViewById(R.id.iv_user_icon);
        mUserName = (TextView) findViewById(R.id.tv_user_login);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_discover:
                switchContent(mDiscoverFragment);
                break;
            case R.id.tv_forum:
                switchContent(mForumFragment);
                break;
            case R.id.tv_event:
                switchContent(mAtivityFragment);
                break;
            case R.id.iv_music_isplaying:
                Intent intent1 = new Intent("com.kygo.progress");

                if (isPlaying) {
                    musicStop.setImageResource(R.drawable.player_bar_pause);
                    //发广播让音乐继续播放
                    intent1.putExtra("isContinue", true);
                    sendBroadcast(intent1);
                } else {
                    musicStop.setImageResource(R.drawable.player_bar_play);
                    //发广播让音乐暂停
                    intent1.putExtra("isPause", true);
                    sendBroadcast(intent1);
                }
                isPlaying = !isPlaying;
                break;
            case R.id.layout_drawer_userMsg:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.btn_sys_feedback:
                Intent intent2 = new Intent(this, FeedBackActivity.class);
                startActivity(intent2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == 1001) {
            String userName = data.getStringExtra("nickname");
            String userIcon = data.getStringExtra("qqicon");
            Log.d("qqdenglu","44444");
            mUserName.setText(userName);
            mUserIcon.setImageURI(userIcon);
        }

    }

    private void userLogin() {
        while (mCursor.moveToNext()) {
            if (mCursor.getInt(mCursor.getColumnIndex("_isLogin")) == 1) {
                String userName = mCursor.getString(mCursor.getColumnIndex("_nickname"));
                mUserName.setText(userName);
                Log.d("username", "-----" + mUserName.getText().toString());
                break;
            }

        }
    }

    @Override
    protected void onResume() {
        Log.d("username", "-----");
        mCursor.requery();
        userLogin();
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(mQqResultReceiver);
        unregisterReceiver(receiver);
    }

    @Override
    public void onIconClickListener() {
        mDrawerLayout.openDrawer(mLeft);
    }

    class TimeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.kygo.time")) {
                cur = intent.getExtras().getInt("cur");
                max = intent.getExtras().getInt("max");
                musictitle = intent.getStringExtra("musictitle");
                musiccontent1 = intent.getStringExtra("musiccontent");
                musicProgress.setProgress(cur);
                musicProgress.setMax(max);
                curProgress = (int) (((float) cur / (float) max) * 100);
                musicName.setText(musictitle);
                musicContent.setText(musiccontent1);
//                notification.contentView.setProgressBar(R.id.pb, 100, curProgress, false);
//                manager.notify(1, notification);
            }
        }
    }

//    class QqResultReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equals("com.winter.qq")) {
//                String userName = intent.getStringExtra("nickname");
//                String userIcon = intent.getStringExtra("qqicon");
//                mUserName.setText(userName);
//                mUserIcon.setImageURI(Uri.parse(userIcon));
//            }
//
//        }
//    }
}
