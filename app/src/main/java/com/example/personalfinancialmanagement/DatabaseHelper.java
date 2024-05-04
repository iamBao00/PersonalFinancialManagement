package com.example.personalfinancialmanagement;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.personalfinancialmanagement.Model.Income;
import com.example.personalfinancialmanagement.Model.IncomeDetail;
import com.example.personalfinancialmanagement.Model.JarDetail;
import com.example.personalfinancialmanagement.Model.Notify;
import com.example.personalfinancialmanagement.Model.Spending;
import com.example.personalfinancialmanagement.Model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        String createNotify = "CREATE TABLE notify (\n" +
                "    id_notify INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    id_user INTEGER,\n" +
                "    title TEXT,\n" +
                "    description TEXT,\n" +
                "    status BIT DEFAULT FALSE,\n" +
                "    date DATE DEFAULT CURRENT_DATE,\n" +
                "    FOREIGN KEY (id_user) REFERENCES user (id_user)\n" +
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
        db.execSQL(createNotify);
        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);
        db.execSQL(sql5);
        db.execSQL(sql6);
    }

    public User getUserByID(int userID) {
        User user = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("user", new String[]{"id_user", "username", "password", "email", "fullname"},
                "id_user" + "=?", new String[]{String.valueOf(userID)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            user = new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
            cursor.close();
        }
        db.close();
        return user;
    }

    public boolean updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("email", user.getEmail());
        values.put("fullname", user.getFullname());

        // Updating row
        int rowsAffected = db.update("user", values, "id_user" + " = ?", new String[]{String.valueOf(user.getId_user())});
        db.close();

        return rowsAffected > 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + "user");
        db.execSQL("DROP TABLE IF EXISTS " + "jar");
        db.execSQL("DROP TABLE IF EXISTS " + "jar_detail");
        db.execSQL("DROP TABLE IF EXISTS " + "spending");
        db.execSQL("DROP TABLE IF EXISTS " + "income");
        db.execSQL("DROP TABLE IF EXISTS " + "detail_income");
        db.execSQL("DROP TABLE IF EXISTS " + "notify");

        // Create tables again
        onCreate(db);
    }

    public long insertData(String fullname ,String email, String username,String password){
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

    public List<IncomeDetail> getIncomeDetailByIdIncome(int id) {

        List<IncomeDetail> listCT = new ArrayList<>();
        String selectQuery = "";
        // Select All Query
        SQLiteDatabase db = this.getReadableDatabase();

        selectQuery = "SELECT  * FROM detail_income where detail_income.id_income = " + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                IncomeDetail CT = new IncomeDetail();
                CT.setIdJarDetail(Integer.parseInt(cursor.getString(0)));
                CT.setIdIncome(Integer.parseInt(cursor.getString(1)));
                CT.setCo_cau(Integer.parseInt(cursor.getString(2)));
                CT.setDetailMoney(Long.valueOf(cursor.getString(3)));
                // Adding contact to list
                listCT.add(CT);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listCT;
    }

    public ArrayList<String> getListNameOfJar(int idUser)
    {
        ArrayList<String> listJar = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Jar ORDER BY id_jar", null);
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            String jar_name = c.getString(1);
            listJar.add(jar_name);
            c.moveToNext();
        }
        db.close();
        return listJar;
    }

    public ArrayList<Integer> getMoneyOfJar(int idUser) {
        ArrayList<Integer> moneys = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM jar_detail WHERE id_user = ? ORDER BY id_jar", new String[]{String.valueOf(idUser)});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int money = c.getInt(3);
            moneys.add(money);
            c.moveToNext();
        }
        db.close();
        return moneys;
    }

    public Integer getIdJarDetail(JarDetail jarDetail) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {String.valueOf(jarDetail.getIdJar()), String.valueOf(jarDetail.getIdUser())};
        Cursor cursor = db.rawQuery("SELECT id_jar_detail FROM jar_detail WHERE id_jar = ? AND id_user = ?", selectionArgs);

        Integer idJarDetail = null; // Khởi tạo giá trị mặc định là null

        if (cursor != null && cursor.moveToFirst()) {
            // Lấy id_jar_detail từ hàng đầu tiên (nếu tồn tại)
            idJarDetail = cursor.getInt(cursor.getColumnIndexOrThrow("id_jar_detail"));
        }

        // Đóng con trỏ và cơ sở dữ liệu
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return idJarDetail;
    }

    public Integer getIdJarByIdJarDetail(Integer idJarDetail) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {String.valueOf(idJarDetail)};
        Cursor cursor = db.rawQuery("SELECT id_jar FROM jar_detail WHERE id_jar_detail = ?", selectionArgs);

        Integer idJar = -1;

        if (cursor != null && cursor.moveToFirst()) {
            // Lấy id_jar_detail từ hàng đầu tiên (nếu tồn tại)
            idJar = cursor.getInt(cursor.getColumnIndexOrThrow("id_jar"));
        }

        // Đóng con trỏ và cơ sở dữ liệu
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return idJar;
    }

    public JarDetail getJarDetail(JarDetail jarDetail) {
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

        JarDetail jarDetailResult = null;

        if (cursor != null && cursor.moveToFirst()) {
            // Lấy giá trị từ con trỏ và tạo đối tượng JarDetail
            int idJarDetail = cursor.getInt(cursor.getColumnIndexOrThrow("id_jar_detail"));
            int idUser = cursor.getInt(cursor.getColumnIndexOrThrow("id_user"));
            int idJar = cursor.getInt(cursor.getColumnIndexOrThrow("id_jar"));
            long money = cursor.getLong(cursor.getColumnIndexOrThrow("money"));
            String date_update = cursor.getString(cursor.getColumnIndexOrThrow("date_update"));
            jarDetailResult = new JarDetail(idJarDetail, idUser, idJar, money, date_update);
        }

        // Đóng con trỏ và kết nối cơ sở dữ liệu
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return jarDetailResult;
    }

    //get jarDetail include idJar, jar_name , money by idUser


    // idJarDetail = (idCurrentUserLogin, idJar), hàm này sẽ trả về tổng thu nhập của CurrentUserLogin ở hủ Jar(jdJar) => chạy 6 lần cộng 6 hủ để tính thu nhập
    public long getSumDetailMoneyInDetailIncomeByIdJarDetail(Integer idJarDetail){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                "detail_money"
        };
        String selection = "id_jar_detail = ?";
        String[] selectionArgs = {String.valueOf(idJarDetail)};
        Cursor cursor = db.query(
                "detail_income",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        long sum = 0;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                long detailMoney = cursor.getInt(cursor.getColumnIndexOrThrow("detail_money"));
                sum += detailMoney;
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return sum;
    }

    public long getTotalSpending(Integer idJarDetail) {
//        long totalSpending = 0;
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String selectQuery = "SELECT money FROM spending WHERE id_jardetail = ?";
//        String[] selectionArgs = {String.valueOf(idJarDetail)};
//        Cursor cursor = db.rawQuery(selectQuery, selectionArgs);
//        if (cursor.moveToFirst()) {
//            long detailMoney = cursor.getLong(cursor.getColumnIndexOrThrow("money"));
//            totalSpending += detailMoney;
//        }
//        cursor.close();
//        return totalSpending;

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                "money"
        };
        String selection = "id_jardetail = ?";
        String[] selectionArgs = {String.valueOf(idJarDetail)};
        Cursor cursor = db.query(
                "spending",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        long sum = 0;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                long detailMoney = cursor.getInt(cursor.getColumnIndexOrThrow("money"));
                sum += detailMoney;
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return sum;
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
        values.put("id_jar_detail", incomeDetail.getIdJarDetail());
        values.put("id_income", incomeDetail.getIdIncome());
        values.put("co_cau", incomeDetail.getCo_cau());
        values.put("detail_money", incomeDetail.getDetailMoney());

        db.insert("detail_income", null, values);
        db.close(); // Closing database connection
    }

    public List<Income> getAllIncomeByListIdIncome(List <Integer> listIdIncome) {
        List<Income> listIncome = new ArrayList<Income>();
        String selectQuery = "";
        // Select All Query
        SQLiteDatabase db = this.getReadableDatabase();

        for(Integer i:listIdIncome){
            selectQuery = "SELECT  * FROM income WHERE id_income = ?";
            String[] selectionArgs = {String.valueOf(i)};
            Cursor cursor = db.rawQuery(selectQuery, selectionArgs);
            if (cursor.moveToFirst()) {
                do {
                    Income income = new Income();
                    income.setIdIncome(Integer.parseInt(cursor.getString(0)));
                    income.setTotal_money(Long.valueOf(cursor.getString(1)));
                    income.setDescription(cursor.getString(2));
                    income.setDate(cursor.getString(3));
                    // Adding contact to list
                    listIncome.add(income);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return listIncome;
    }

    public List<Integer> getAllIdIncomeByListIdJarDetail(List<Integer> listIdJarDetail) {
        Set<Integer> listIdIncome = new HashSet<Integer>();
        String selectQuery = "";
        // Select All Query
        SQLiteDatabase db = this.getReadableDatabase();

        for(Integer i:listIdJarDetail){
            selectQuery = "SELECT  * FROM detail_income WHERE id_jar_detail = ?";
            String[] selectionArgs = {String.valueOf(i)};
            Cursor cursor = db.rawQuery(selectQuery, selectionArgs);
            if (cursor.moveToFirst()) {
                do {
                    // Adding contact to list
                    listIdIncome.add(Integer.parseInt(cursor.getString(1)));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return new ArrayList<Integer>(listIdIncome);
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

    public long addSpending(Spending spending){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id_jardetail", spending.getIdJarDetail());
        values.put("money", spending.getMoney());
        values.put("description", spending.getDescription());
        values.put("date", spending.getDate());

        long id = db.insert("spending", null, values);
        db.close();
        return id;
    }

    public ArrayList<Spending> getSpendingByUserId(int userId) {
        ArrayList<Spending> spendingList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                "id_spending",
                "id_jardetail",
                "money",
                "description",
                "date"
        };

        String selection = "id_jardetail IN (SELECT id_jar_detail FROM jar_detail WHERE id_user = ?)";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query(
                "spending",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Lấy giá trị từ con trỏ và tạo đối tượng Spending
                int idSpending = cursor.getInt(cursor.getColumnIndexOrThrow("id_spending"));
                int idJarDetail = cursor.getInt(cursor.getColumnIndexOrThrow("id_jardetail"));
                Long money = cursor.getLong(cursor.getColumnIndexOrThrow("money"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));

                Spending spending = new Spending(idSpending, idJarDetail, money, description, date);
                spendingList.add(spending);
            } while (cursor.moveToNext());
        }

        // Đóng con trỏ và kết nối cơ sở dữ liệu
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return spendingList;
    }

    public int getJarId(int userId, int jarDetailId) {
        int jarId = -1; // Giá trị mặc định nếu không tìm thấy

        // Lấy tham chiếu đến cơ sở dữ liệu để đọc
        SQLiteDatabase db = this.getReadableDatabase();

        // Câu lệnh truy vấn SQL SELECT
        String query = "SELECT id_jar FROM jar_detail WHERE id_user = ? AND id_jar_detail = ?";

        // Tham số cho câu lệnh SQL
        String[] selectionArgs = {String.valueOf(userId), String.valueOf(jarDetailId)};

        // Thực thi truy vấn
        Cursor cursor = db.rawQuery(query, selectionArgs);

        // Kiểm tra xem có kết quả không
        if (cursor != null && cursor.moveToFirst()) {
            // Lấy giá trị id_jar từ Cursor
            jarId = cursor.getInt(cursor.getColumnIndexOrThrow("id_jar"));

            // Đóng Cursor sau khi sử dụng
            cursor.close();
        }

        // Đóng cơ sở dữ liệu
        db.close();

        // Trả về id_jar
        return jarId;
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

    public int getIDUser(String username)
    {
        int idUser = -1 ; // default idUser = 1 when check login false
        SQLiteDatabase data = this.getReadableDatabase();
        Cursor cursor = null;

        cursor = data.rawQuery("SELECT id_user FROM user WHERE username = ?", new String[] {username});
        if(cursor!= null && cursor.moveToFirst()) {
            idUser = cursor.getInt(0);
        }


//        cursor.close();
        return idUser;
    }

    public User getUserInfo(int idUser)
    {
        User u = new User();
        SQLiteDatabase data = this.getReadableDatabase();
        Cursor cursor = null    ;
        cursor = data.rawQuery("SELECT* FROM user WHERE id_user = ?", new String[]{String.valueOf(idUser)});

        if(cursor!= null && cursor.moveToFirst())
        {
            String fullname,email, username, password;
            fullname =  cursor.getString(cursor.getColumnIndexOrThrow("fullname"));
            email =  cursor.getString(cursor.getColumnIndexOrThrow("email"));
            username =  cursor.getString(cursor.getColumnIndexOrThrow("username"));
            password =  cursor.getString(cursor.getColumnIndexOrThrow("password"));
            u.setFullname(fullname);
            u.setEmail(email);
            u.setUsername(username);
            u.setPassword(password);
        }
        cursor.close();

        return u;
    }

    public void addNotify(Notify notify) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("id_notify", notify.getId_notify());
        values.put("id_user", notify.getId_user());
        values.put("title", notify.getTitle());
        values.put("description", notify.getDescription());
//        values.put("status", notify.getStatus());
        values.put("date", notify.getDate());
        try {
            db.insertOrThrow("notify", null, values);
        } catch (SQLException e) {
            Log.e(TAG, "Error adding notify: " + e.getMessage());
        } finally {
            db.close();
        }
    }

    public List<Integer> getAllIdNotifyOfUser(int id_user)
    {
        List<Integer> idNotifyList = new ArrayList<>();
        SQLiteDatabase data = this.getReadableDatabase();
        Cursor c = data.rawQuery("SELECT id_notify FROM notify WHERE id_user = ?", new String[]{String.valueOf(id_user)});
        c.moveToFirst();
        do {
            int idNotify = c.getInt(0);
            idNotifyList.add(idNotify);

        }while (!c.isAfterLast() && c.moveToNext());

        c.close();
        return idNotifyList;
    }


    public Notify getNotify(int id_notify)
    {
        SQLiteDatabase data = this.getReadableDatabase() ;
        Notify notify = new Notify();
        Cursor c = data.rawQuery("SELECT * FROM notify WHERE id_notify = ?", new String[]{String.valueOf(id_notify)});
        c.moveToFirst();
        do {
            notify.setId_notify(c.getInt(c.getColumnIndexOrThrow("id_notify")));
            notify.setId_user(c.getInt(c.getColumnIndexOrThrow("id_user")));
            notify.setTitle(c.getString(c.getColumnIndexOrThrow("title")));
            notify.setDescription(c.getString(c.getColumnIndexOrThrow("description")));
            notify.setStatus(c.getInt(c.getColumnIndexOrThrow("status")));
            notify.setDate(c.getString(c.getColumnIndexOrThrow("date")));
        }while (!c.isAfterLast() && c.moveToNext());


        return notify;
    }

    public void setReadNotifyStatus(int id_notify)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", 1);
        db.update("notify", values, "id_notify = ?", new String[] { String.valueOf(id_notify) });
        db.close();
    }

    public void setUnreadNotifyStatus(int id_notify)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", 0);
        db.update("notify", values, "id_notify = ?", new String[] { String.valueOf(id_notify) });
        db.close();
    }
}