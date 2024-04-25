package com.example.moneymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.moneymanager.Model.Income;
import com.example.moneymanager.Model.IncomeDetail;
import com.example.moneymanager.Model.JarDetail;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "income_manage";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUser = "CREATE TABLE IF NOT EXISTS\"user\" (\n" +
                "\t\"id_user\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"username\"\tTEXT NOT NULL UNIQUE,\n" +
                "\t\"password\"\tTEXT NOT NULL,\n" +
                "\t\"email\"\tTEXT,\n" +
                "\t\"fullname\"\tTEXT\n" +
                ");";
        String createJar = "CREATE TABLE IF NOT EXISTS \"jar\" (\n" +
                "\t\"id_jar\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"jar_name\"\tTEXT\n" +
                ");";
        String creatJarDetail = "CREATE TABLE IF NOT EXISTS \"jar_detail\" (\n" +
                "\t\"id_jar_detail\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"id_user\"\tINTEGER NOT NULL,\n" +
                "\t\"id_jar\"\tINTEGER,\n" +
                "\t\"money\"\tMONEY DEFAULT 0,\n" +
                "\t\"date_update\"\tTEXT DEFAULT CURRENT_DATE,\n" +
                "\tFOREIGN KEY(\"id_user\") REFERENCES \"user\"(\"id_user\"),\n" +
                "\tFOREIGN KEY(\"id_jar\") REFERENCES \"jar\"(\"id_jar\")\n" +
                ");";
        String createSpending = "CREATE TABLE IF NOT EXISTS \"spending\" (\n" +
                "\t\"id_spending\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"id_jardetail\"\tINTEGER NOT NULL,\n" +
                "\t\"money\"\tMONEY DEFAULT 0,\n" +
                "\t\"description\"\tTEXT,\n" +
                "\t\"date\"\tDATE DEFAULT CURRENT_DATE,\n" +
                "\tFOREIGN KEY(\"id_jardetail\") REFERENCES \"jar_detail\"(\"id_jardetail\")\n" +
                ");";
        String createIncome = "CREATE TABLE IF NOT EXISTS income (\n" +
                "    id_income INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    total_money MONEY,\n" +
                "    description TEXT,\n" +
                "    date DATE\n" +
                ");";
        String createDetailIncome = "CREATE TABLE detail_income (\n" +
                "    id_jar_detail INTEGER,\n" +
                "    id_income INTEGER,\n" +
                "    co_cau TEXT,\n" +
                "    detail_money MONEY,\n" +
                "    PRIMARY KEY (id_jar_detail, id_income),\n" +
                "    FOREIGN KEY (id_jar_detail) REFERENCES jar_detail (id_jar_detail),\n" +
                "    FOREIGN KEY (id_income) REFERENCES income (id_income)\n" +
                ");";

        String sql1 = "INSERT INTO Jar (jar_name) VALUES ('Thiết Yếu');",
                sql2 = "INSERT INTO Jar (jar_name) VALUES ('Giáo Dục');",
                sql3 = "INSERT INTO Jar (jar_name) VALUES ('Tiết Kiệm');",
                sql4 = "INSERT INTO Jar (jar_name) VALUES ('Hưởng Thụ');",
                sql5 = "INSERT INTO Jar (jar_name) VALUES ('Đầu Tư');",
                sql6 = "INSERT INTO Jar (jar_name) VALUES ('Thiện Tâm');";
        db.execSQL(createUser);
        db.execSQL(createJar);
        db.execSQL(creatJarDetail);
        db.execSQL(createSpending);
        db.execSQL(createIncome);
        db.execSQL(createDetailIncome);
        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);
        db.execSQL(sql5);
        db.execSQL(sql6);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + "user");
        db.execSQL("DROP TABLE IF EXISTS " + "jar");
        db.execSQL("DROP TABLE IF EXISTS " + "jar_detail");
        db.execSQL("DROP TABLE IF EXISTS " + "spending");
        db.execSQL("DROP TABLE IF EXISTS " + "income");
        db.execSQL("DROP TABLE IF EXISTS " + "detail_income");
        // Create tables again
        onCreate(db);
    }

    public long insertData(String email, String password, String fullname, String username){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("username", username);
        contentValues.put("fullname", fullname);
        long result = MyDatabase.insert("user", null, contentValues);

        if (result == -1) {
            return result;
        } else {
            for(int i = 1; i<=6; i++){
                ContentValues contentValues1 = new ContentValues();
                contentValues1.put("id_user",result);
                contentValues1.put("id_jar",i);
                contentValues1.put("money",0);
                MyDatabase.insert("jar_detail",null,contentValues1);
            }
            return result;
        }
    }
    public JarDetail getMoneyInJarDetail(JarDetail jarDetail) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                "id_jar_detail",
                "id_user",
                "id_jar",
                "money",
                "date_update"
        };

        String selection = "id_jar = ? AND id_user = ?";
        String[] selectionArgs = {String.valueOf(jarDetail.getIdJar()), String.valueOf(jarDetail.getIdUser())};

        Cursor cursor = db.query(
                "jar_detail",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        JarDetail jarDetail2 = new JarDetail();
        // Duyệt qua các hàng của kết quả và thêm chúng vào danh sách
        if (cursor != null && cursor.moveToFirst()) {
            int idJarDetail = cursor.getInt(cursor.getColumnIndexOrThrow("id_jar_detail"));
            int idUser = cursor.getInt(cursor.getColumnIndexOrThrow("id_user"));
            int idJar = cursor.getInt(cursor.getColumnIndexOrThrow("id_jar"));
            long money = cursor.getLong(cursor.getColumnIndexOrThrow("money"));
            String date_update = cursor.getString(cursor.getColumnIndexOrThrow("date_update"));
            jarDetail2 =  new JarDetail(idJarDetail, idUser,idJar, money,date_update);
        }

        // Đóng con trỏ và kết nối cơ sở dữ liệu
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return jarDetail2;
    }

    public long addIncome(Income income){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("total_money", income.getTotal_money());
        values.put("description", income.getDescription());
        values.put("date", income.getDate());

        long id = db.insert("income", null, values);
        db.close(); // Closing database connection
        return id;
    }

    public void addDetailMoney(IncomeDetail incomeDetail){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id_jar", incomeDetail.getIdJarDetail());
        values.put("id_income", incomeDetail.getIdIncome());
        values.put("co_cau", incomeDetail.getCo_cau());
        values.put("detail_money", incomeDetail.getDetailMoney());

        db.insert("detail_income", null, values);
        db.close(); // Closing database connection
    }

    public void updateJarDetail(JarDetail jarDetail){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("money", jarDetail.getMoney());

        String selection = "id_jar = ? AND id_user = ?";
        String[] selectionArgs = {String.valueOf(jarDetail.getIdJar()), String.valueOf(jarDetail.getIdUser())};

        int rowsAffected = db.update("jar_detail", values, selection, selectionArgs);

        // Kiểm tra xem có bao nhiêu hàng đã được cập nhật
        if (rowsAffected > 0) {
            Log.d("Database", "Updated " + rowsAffected + " rows in detail_income table");
        } else {
            Log.d("Database", "No rows updated in detail_income table");
        }
        db.close(); // Đóng kết nối cơ sở dữ liệu
    }

    public Boolean checkEmail(String email){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from user where email = ?", new String[]{email});

        if(cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }
    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from user where username = ? and password = ?", new String[]{username, password});

        if (cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }
}