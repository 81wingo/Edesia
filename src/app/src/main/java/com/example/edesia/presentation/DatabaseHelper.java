package com.example.edesia.presentation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.ArrayList;
import java.util.List;


public class
DatabaseHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "User.db";
        private static final String TABLE_NAME = "UserTable";
        private static final int DATABASE_Version = 1;
        private static final String USER_ID = "ID";
        private static final String USER_NAME = "Username";
        private static final String NAME = "Name";
        private static final String EMAIL = "Email";
        private static final String PASSWORD = "Password";
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("+USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +USER_NAME+" VARCHAR(255),"+EMAIL+" VARCHAR(255),"
                +NAME+" VARCHAR(255),"+ PASSWORD +" VARCHAR(225));";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;



        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
        }

        public void onCreate(SQLiteDatabase db) {

                db.execSQL("Create table UserTable(Username text primary key, Password text, Name text, Email text)");
                db.execSQL("Create table PlannedMeals(Username text, Month text, Day integer, Breakfast integer, Lunch integer, Dinner integer, CONSTRAINT fk_Username FOREIGN KEY (Username) REFERENCES UserTable(Username))");
                db.execSQL("Create table FavoriteRecipes(Username text, RecipeID integer, CONSTRAINT fk_Username FOREIGN KEY (Username) REFERENCES UserTable(Username))");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("drop table if exists UserTable");
        }

    public boolean insertUserData(String name, String username, String email, String pass){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Username", username);
        contentValues.put("Password", pass);
        contentValues.put("Name", name);
        contentValues.put("Email", email);

        long id = db.insert("UserTable", null , contentValues);
        if(id == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean insertPlannedMeal(String user, String month, int day, String mealTime, int RecipeID){

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("Username", user);
            contentValues.put("Month", month);
            contentValues.put("Day", day);

            if(mealTime.equals("Breakfast")) {
                contentValues.put("Breakfast", RecipeID);
            }
            if(mealTime.equals("Lunch")) {
                contentValues.put("Lunch", RecipeID);
            }
            if(mealTime.equals("Dinner")) {
            contentValues.put("Dinner", RecipeID);

            }

            long id = db.insert("PlannedMeals", null, contentValues);
            if(id == -1){
                return false;
            }else{
                return true;
        }
    }

    public boolean insertRandom(String user, String month, int day, int Breakfast, int Lunch, int Dinner){
            SQLiteDatabase db = this.getReadableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("Username", user);
            contentValues.put("Month", month);
            contentValues.put("Day", day);
            contentValues.put("Breakfast", Breakfast);
            contentValues.put("Lunch", Lunch);
            contentValues.put("Dinner", Dinner);

        long id = db.insert("PlannedMeals", null, contentValues);
        if(id == -1){
            return false;
        }else{
            return true;
        }

    }

    public boolean insertFavoriteRecipe(String user, int RecipeID){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Username", user);
        contentValues.put("RecipeID", RecipeID);

        long id = db.insert("FavoriteRecipes", null, contentValues);
        if(id == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean checkUsername(String userName){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("Select * from UserTable where Username=?", new String[]{userName});
            if(cursor.getCount() > 0){
                return false;
            }else{
                return true;
            }
    }

    public boolean checkLogin(String user, String pass){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from UserTable where Username=? and Password=?", new String[]{user,pass});

            if(cursor.getCount() > 0){
                return true;
            }else{
                return false;
            }
    }

    public List<String> getIdFavorites()
    {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //all column names
        String[] sqlSelect = {"RecipeID"};
        String tableName = "FavoriteRecipes";  //table name

        qb.setTables( tableName );
        Cursor cursor = qb.query(db, sqlSelect,null,null,null,null,null);
        List<String>result = new ArrayList<>(  );
        if (cursor.moveToFirst())
        {
            do{
                result.add( cursor.getString( cursor.getColumnIndex( "RecipeID" ) ));
            }while (cursor.moveToNext());
        }
        return result;
    }

    public String getData() {

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"Name", "Username", "Email", "Password"};
        Cursor cursor = db.query("UserTable",columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            String name =cursor.getString(cursor.getColumnIndex("Name"));
            String username =cursor.getString(cursor.getColumnIndex("Username"));
            String email =cursor.getString(cursor.getColumnIndex("Email"));
            String  password =cursor.getString(cursor.getColumnIndex("Password"));
            buffer.append(name + "   " + username + "   " + email + "   " + password +" \n");
        }
        return buffer.toString();
    }

    public int updateUsername(String old , String newName) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME,newName);
        String[] findUser= {old};
        int count =db.update(TABLE_NAME,contentValues, USER_NAME+" = ?",findUser );
        return count;
    }

    public  int delete(String User) {

        SQLiteDatabase db = this.getWritableDatabase();
        String[] findUser ={User};

        int count =db.delete(TABLE_NAME , USER_NAME+" = ?",findUser);
        return  count;
    }
}