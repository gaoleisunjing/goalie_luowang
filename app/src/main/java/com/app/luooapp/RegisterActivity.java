package com.app.luooapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.DaoMaster;
import com.app.UserEntity;
import com.app.UserEntityDao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by peiyangyang on 2016/10/6.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBackIv;
    private EditText mEmail, mPw, mNickName;
    private TextView mRegTv;
    private CheckBox mCheckBox;
    private DaoMaster mDaoMaster;
    private UserEntityDao mUserEntityDao;
    private Cursor mCursor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initGenerate();
        initView();

    }

    private void initGenerate() {

        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(
                getApplicationContext(),
                "user.db", null);
        SQLiteDatabase database = openHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(database);
        mUserEntityDao = mDaoMaster.newSession().getUserEntityDao();
        mCursor = database.query(mUserEntityDao.getTablename(),
                mUserEntityDao.getAllColumns(),
                null, null, null, null, null);//查询数据库包含的所有内容
        Log.d("cursor","111111----"+mCursor.getCount());
    }

    private void initView() {

        mBackIv = (ImageView) findViewById(R.id.register_reg_back);
        mBackIv.setOnClickListener(this);
        mEmail = (EditText) findViewById(R.id.reg_user_email);
        mPw = (EditText) findViewById(R.id.reg_user_pw);
        mNickName = (EditText) findViewById(R.id.reg_user_nickname);
        mRegTv = (TextView) findViewById(R.id.reg_user_reg);
        mRegTv.setOnClickListener(this);
        mCheckBox = (CheckBox) findViewById(R.id.reg_agree_check);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.register_reg_back:
                finish();
                break;
            case R.id.reg_user_reg:
                if (mCheckBox.isChecked()) {
                    UserEntity userEntity = new UserEntity();
                    String EmailString = mEmail.getText().toString();
                    String password = mPw.getText().toString();
                    String nickName = mNickName.getText().toString();
                    if (EmailString != null && password != null && nickName != null) {
                        mRegTv.setBackgroundColor(Color.RED);
                        mRegTv.setClickable(true);
                    } else {
                        mRegTv.setBackgroundColor(Color.GRAY);
                        mRegTv.setClickable(false);
                    }
                    if (EmailString != null && isEmail(EmailString)) {
                        userEntity.setEmail(EmailString);
                    } else {
                        Toast.makeText(this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (password.length() > 6 && password.length() < 16) {
                        userEntity.setPw(password);
                    } else {
                        Toast.makeText(this, "密码为6~16个字符", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (nickName.length() > 2 && nickName.length() < 10) {
                        userEntity.setNickname(nickName);
                    } else {
                        Toast.makeText(this, "昵称为2~10个字符", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    while (mCursor.moveToNext()){

                        if (mCursor.getString(mCursor.getColumnIndex("_email")).equals(EmailString)){
                            Toast.makeText(this, "此用户已注册", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }

                    userEntity.setIsLogin(0);
                    Log.d("test","--EmailString--"+EmailString+"--password--"+password+"--nickName--"+nickName);
                    mUserEntityDao.insert(userEntity);
                    Toast.makeText(getApplicationContext(), "注册成功。", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    //判断邮箱格式是否正确
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        Log.d("email", m.matches() + "---");
        return m.matches();
    }
}
