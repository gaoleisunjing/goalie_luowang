package com.app;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.app.UserEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table USER_ENTITY.
*/
public class UserEntityDao extends AbstractDao<UserEntity, Long> {

    public static final String TABLENAME = "USER_ENTITY";

    /**
     * Properties of entity UserEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Email = new Property(1, String.class, "email", false, "_email");
        public final static Property Pw = new Property(2, String.class, "pw", false, "_pw");
        public final static Property Nickname = new Property(3, String.class, "nickname", false, "_nickname");
        public final static Property IsLogin = new Property(4, int.class, "isLogin", false, "_isLogin");
    };


    public UserEntityDao(DaoConfig config) {
        super(config);
    }
    
    public UserEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'USER_ENTITY' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'_email' TEXT NOT NULL UNIQUE ," + // 1: email
                "'_pw' TEXT NOT NULL ," + // 2: pw
                "'_nickname' TEXT NOT NULL ," + // 3: nickname
                "'_isLogin' INTEGER NOT NULL );"); // 4: isLogin
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'USER_ENTITY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, UserEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getEmail());
        stmt.bindString(3, entity.getPw());
        stmt.bindString(4, entity.getNickname());
        stmt.bindLong(5, entity.getIsLogin());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public UserEntity readEntity(Cursor cursor, int offset) {
        UserEntity entity = new UserEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // email
            cursor.getString(offset + 2), // pw
            cursor.getString(offset + 3), // nickname
            cursor.getInt(offset + 4) // isLogin
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, UserEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setEmail(cursor.getString(offset + 1));
        entity.setPw(cursor.getString(offset + 2));
        entity.setNickname(cursor.getString(offset + 3));
        entity.setIsLogin(cursor.getInt(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(UserEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(UserEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}