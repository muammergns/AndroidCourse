package com.gns.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {


    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String COLUMN_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String COLUMN_CUSTOMER_AGE = "CUSTOMER_AGE";
    public static final String COLUMN_ACTIVE_CUSTOMER = "ACTIVE_CUSTOMER";
    public static final String COLUMN_CUSTOMER_NAMEA = "CUSTOMER_NAMEA";
    public static final String COLUMN_CUSTOMER_AGEB = "CUSTOMER_AGEB";
    public static final String COLUMN_ACTIVE_CUSTOMERC ="ACTIVE_CUSTOMERC";
    public static final String COLUMN_ID = "ID";

    public SQLiteHelper(@Nullable Context context, String name) {
        super(context, name, null, 4);
    }

    @Override//burası sadece database dosyası oluşurken çalışıyor o yüzden güncellemeleri buraya eklemeyi unutma
    public void onCreate(SQLiteDatabase db) {//güncellemeye create table kısmını eklemeyi unutma ve if not exist eklemeyide unutma
        String createTable= "CREATE TABLE IF NOT EXISTS " + CUSTOMER_TABLE + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY , "
                + COLUMN_CUSTOMER_NAME + " TEXT , "
                + COLUMN_CUSTOMER_AGE + " INT , "
                + COLUMN_ACTIVE_CUSTOMER + " BOOL , "
                + COLUMN_CUSTOMER_NAMEA + " TEXT , "
                + COLUMN_CUSTOMER_AGEB + " INT , "
                + COLUMN_ACTIVE_CUSTOMERC + " BOOL)";
        db.execSQL(createTable);//primary key belirlerken autoincrement kullanmak iyi değil. eğer özel id ler atamak istemiyorsanız gereksiz. özel id atayacaksan int oluştur kendin ekle
    }

    private static final String update2 = "ALTER TABLE " + CUSTOMER_TABLE + " ADD COLUMN " + COLUMN_CUSTOMER_NAMEA + " TEXT;";
    private static final String update3 = "ALTER TABLE " + CUSTOMER_TABLE + " ADD COLUMN " + COLUMN_CUSTOMER_AGEB + " INT;";
    private static final String update4 = "ALTER TABLE " + CUSTOMER_TABLE + " ADD COLUMN " + COLUMN_ACTIVE_CUSTOMERC + " BOOL;";

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion<2){
            db.execSQL(update2);//string de yeni oluşturduklarımız null olarak döndü yani "" değil direkt olarak null döndü
        }
        if (oldVersion<3){
            db.execSQL(update3);//int de yeni oluşanlar 0 olarak döndü
        }
        if (oldVersion<4){
            db.execSQL(update4);//boolean da yeni oluşanlar false olarak döndü
            configureUpdate4(db);// varsayılan değerleri bir defaya mahsus çalışacak bu method ile oluşturma anında düzenleyebilirsin
                                // her güncelleme için böyle bir method oluşturman gerekir
        }
    }

    private void configureUpdate4(SQLiteDatabase db){
        List<CustomerModal> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM "+CUSTOMER_TABLE;
        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst()){
            do {
                int customerID = cursor.getInt(0);
                String customerName = cursor.getString(1);
                int customerAge = cursor.getInt(2);
                boolean customerActive = cursor.getInt(3) == 1;
                String customerNamea = cursor.getString(4);
                int customerAgeb = cursor.getInt(5);
                boolean customerActivec = cursor.getInt(6) == 1;
                CustomerModal modal = new CustomerModal(customerID,customerName,customerAge,customerActive,customerNamea,customerAgeb,customerActivec);
                returnList.add(modal);
                System.out.println(modal.toString());
            }while (cursor.moveToNext());
            for (CustomerModal customerModal : returnList){
                ContentValues cv = new ContentValues();

                cv.put(COLUMN_CUSTOMER_NAME,customerModal.getName());
                cv.put(COLUMN_CUSTOMER_AGE,customerModal.getAge());
                cv.put(COLUMN_ACTIVE_CUSTOMER,customerModal.isActive());
                cv.put(COLUMN_CUSTOMER_NAMEA,customerModal.getNamea());
                cv.put(COLUMN_CUSTOMER_AGEB,customerModal.getAgeb());
                cv.put(COLUMN_ACTIVE_CUSTOMERC,customerModal.isActive());//burada eskisini yenisi ile değiştirdik diğerlerine de uygulayabilirdik

                String where="ID = ?";

                db.update(CUSTOMER_TABLE,cv,where,new  String[]{String.valueOf(customerModal.getId())});
            }

        }else {
            System.out.println("no cursor database");
        }
        cursor.close();
    }

    public boolean addOne(CustomerModal customerModal){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CUSTOMER_NAME,customerModal.getName());
        cv.put(COLUMN_CUSTOMER_AGE,customerModal.getAge());
        cv.put(COLUMN_ACTIVE_CUSTOMER,customerModal.isActive());
        cv.put(COLUMN_CUSTOMER_NAMEA,customerModal.getNamea());
        cv.put(COLUMN_CUSTOMER_AGEB,customerModal.getAgeb());
        cv.put(COLUMN_ACTIVE_CUSTOMERC,customerModal.isActivec());

        long insert = db.insert(CUSTOMER_TABLE,null,cv);

        return insert!=-1;
    }

    public boolean deleteOne(CustomerModal customerModal){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + CUSTOMER_TABLE + " WHERE " + COLUMN_ID + " = " + customerModal.getId();

        Cursor cursor = db.rawQuery(queryString, null);
        return cursor.moveToFirst();
    }

    public void updateOne(CustomerModal oldcustomerModal ,CustomerModal customerModal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CUSTOMER_NAME,customerModal.getName());
        cv.put(COLUMN_CUSTOMER_AGE,customerModal.getAge());
        cv.put(COLUMN_ACTIVE_CUSTOMER,customerModal.isActive());
        cv.put(COLUMN_CUSTOMER_NAMEA,customerModal.getNamea());
        cv.put(COLUMN_CUSTOMER_AGEB,customerModal.getAgeb());
        cv.put(COLUMN_ACTIVE_CUSTOMERC,customerModal.isActivec());

        String where="ID = ?";

        db.update(CUSTOMER_TABLE,cv,where,new  String[]{String.valueOf(oldcustomerModal.getId())});
        db.close();
    }

    public List<CustomerModal> getEveryone(){
        SQLiteDatabase db = this.getReadableDatabase();

        List<CustomerModal> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM "+CUSTOMER_TABLE;
        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst()){
            do {
                int customerID = cursor.getInt(0);
                String customerName = cursor.getString(1);
                int customerAge = cursor.getInt(2);
                boolean customerActive = cursor.getInt(3) == 1;
                String customerNamea = cursor.getString(4);
                int customerAgeb = cursor.getInt(5);
                boolean customerActivec = cursor.getInt(6) == 1;
                returnList.add(new CustomerModal(customerID,customerName,customerAge,customerActive,customerNamea,customerAgeb,customerActivec));
            }while (cursor.moveToNext());

        }else {
            System.out.println("no cursor database");
        }
        cursor.close();

        db.close();

        return returnList;
    }
}
