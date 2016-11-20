package com.example;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class DaoGenerate {

    public static void main(String args[]) {

        //首先得到一个数据库环境类
        Schema schema = new Schema(1, "com.app");
        Entity UserEntity = schema.addEntity("UserEntity");
        UserEntity.addIdProperty().columnName("_id").autoincrement();
        UserEntity.addStringProperty("email").columnName("_email").notNull().unique();
        UserEntity.addStringProperty("pw").columnName("_pw").notNull();
        UserEntity.addStringProperty("nickname").columnName("_nickname").notNull();
        UserEntity.addIntProperty("isLogin").columnName("_isLogin").notNull();

        try {
            DaoGenerator generator = new DaoGenerator();
            generator.generateAll(schema,
                    "/D:/AndroidStudio/LuooApp/app/src/main/java-gen");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
