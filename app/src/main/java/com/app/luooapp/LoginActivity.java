package com.app.luooapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.DaoMaster;
import com.app.UserEntityDao;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by peiyangyang on 2016/9/29.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBack, mWechat, mQQ, mSina, mDouban;
    private EditText mEmailEt, mPwEt;
    private TextView mLoginTv,mRegisterTv;
    DaoMaster mDaoMaster;
    UserEntityDao mUserEntityDao;
    Cursor mCursor;
    SQLiteDatabase database;


    Tencent mTencent;//定义Tencent实例化对象
    private static String APP_ID = "1105685759";

    private static final int TECENT_LOGIN = 444;
    private static final int TECENT_USERINFO = 709;
    private int action = TECENT_LOGIN;
    Intent mIntent=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initGenerate();
        initView();
        mIntent = getIntent();
        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance("11056" +
                "" +
                "" +
                "85759", getApplicationContext());
    }

    private void initGenerate() {

        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(getApplicationContext(),
                "user.db",null);
        database = openHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(database);
        mUserEntityDao = mDaoMaster.newSession().getUserEntityDao();
        mCursor = database.query(mUserEntityDao.getTablename(),
                mUserEntityDao.getAllColumns(),null,
                null,null,null,null);

    }



    private void initView() {
        mBack = (ImageView) findViewById(R.id.left_login_back);
        mBack.setOnClickListener(this);
        mQQ = (ImageView) findViewById(R.id.left_logo_qq);
        mQQ.setOnClickListener(this);
        mEmailEt = (EditText) findViewById(R.id.left_user_email);
        mPwEt = (EditText) findViewById(R.id.left_user_pw);
        mLoginTv = (TextView) findViewById(R.id.left_login_login);
        mLoginTv.setOnClickListener(this);
        mRegisterTv = (TextView) findViewById(R.id.left_login_fast_register);
        mRegisterTv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_login_back:
                finish();
                break;
            case R.id.left_logo_qq:
                Log.d("qqdenglu","0000");
                loginQQ();
                finish();
                break;
            case R.id.left_login_login:

                String userEmail = mEmailEt.getText().toString();
                String userpw = mPwEt.getText().toString();
                while (mCursor.moveToNext()){

                    if (mCursor.getString(mCursor.getColumnIndex("_email")).equals(userEmail)
                            &&mCursor.getString(mCursor.getColumnIndex("_pw")).equals(userpw)){

                        ContentValues values=new ContentValues();
                        values.put("_isLogin",1);
                        database.update(mUserEntityDao.getTablename(),values,"_email=?",
                                new String[]{userEmail});

                        Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }else{
                        ContentValues values=new ContentValues();
                        values.put("_isLogin",0);
                        database.update(mUserEntityDao.getTablename(),values,"_email=?",
                                new String[]{mCursor.getString(mCursor.getColumnIndex("_email"))});
                    }

                }

                break;
            case R.id.left_login_fast_register:
                Intent intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }


    IUiListener mIUiListener;//定义Tencent登录监听
    private void loginQQ() {
        if (mTencent.isSessionValid()) {
            Toast.makeText(getApplicationContext(),
                    "您已登录，请挂机。", Toast.LENGTH_SHORT).show();
            return;
        }
        //登录QQ
        mIUiListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Toast.makeText(getApplicationContext(), "登陆出错。。。", Toast.LENGTH_SHORT).show();
                JSONObject object = (JSONObject) o;

                switch (action) {
                    case TECENT_LOGIN:
                        try {
                            String openid = object.getString("openid");
                            String access_token = object.getString("access_token");
                            String expires_in = object.getString("expires_in");
                            Log.d("qqdenglu","111111");
                            //将本次登录获取的信息设置到QQ管理器中
                            mTencent.setOpenId(openid);
                            mTencent.setAccessToken(access_token, expires_in);

                            UserInfo userInfo = new UserInfo(getApplicationContext(), mTencent.getQQToken());
                            userInfo.getUserInfo(mIUiListener);
                            action = TECENT_USERINFO;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case TECENT_USERINFO:

                        try {
                            //拿到昵称和头像
                            String nickname = object.getString("nickname");
                            String qqicon = object.getString("figureurl_qq_2");
                            Log.d("qqdenglu","22222");
//                            //使用intent回传给mainactivity设置用户头像和名字
//                            Intent intent = new Intent("com.winter.qq");
//                            intent.putExtra("nickname", nickname);
//                            intent.putExtra("qqicon", qqicon);
//                            Log.d("receiver---nickname",""+nickname);
//                            sendBroadcast(intent);

                            Intent result = mIntent;
                            result.putExtra("nickname", nickname);
                            result.putExtra("qqicon", qqicon);
                            Log.d("qqdenglu","33333");
                            setResult(1001, result);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;
                }
            }

            @Override
            public void onError(UiError uiError) {
                Toast.makeText(getApplicationContext(), "登陆出错。。。", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "取消登录。。。", Toast.LENGTH_SHORT).show();
            }
        };
        //调用Tencent对象登录方法
        mTencent.login(this, "all", mIUiListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //调用腾讯的onActivityResultData方法
        if (requestCode== Constants.REQUEST_LOGIN){
            mTencent.onActivityResultData(requestCode, resultCode,data,mIUiListener);
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

}
