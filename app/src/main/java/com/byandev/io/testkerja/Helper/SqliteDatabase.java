package com.byandev.io.testkerja.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import com.byandev.io.testkerja.Model.RespData;


public class SqliteDatabase extends SQLiteOpenHelper {

    private	static final int DATABASE_VERSION =	5;
    private	static final String	DATABASE_NAME = "test";
    private	static final String TABLE_CONTACTS = "test_kerja";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NO_CONTAINER = "container";
    private static final String COLUMN_SIZE = "size";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_SLOT = "slot";
    private static final String COLUMN_ROWS = "rows";
    private static final String COLUMN_TIER = "tier";

    public SqliteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String	CREATE_CONTACTS_TABLE = "CREATE	TABLE " + TABLE_CONTACTS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_NO_CONTAINER + " TEXT,"
            + COLUMN_SIZE + " TEXT,"
            + COLUMN_TYPE + " TEXT,"
            + COLUMN_SLOT +" TEXT,"
            + COLUMN_ROWS + " TEXT,"
            + COLUMN_TIER + " TEXT"
            +")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public ArrayList<RespData> listContacts(){
        String sql = "select * from " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<RespData> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String no_container = cursor.getString(1);
                String size = cursor.getString(2);
                String type = cursor.getString(3);
                String slot = cursor.getString(4);
                String rows = cursor.getString(5);
                String tier = cursor.getString(6);
                storeContacts.add(new RespData(id, no_container, size, type, slot, rows, tier));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }

    public void addContacts(RespData contacts){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NO_CONTAINER, contacts.getNo_container());
        values.put(COLUMN_SIZE, contacts.getSize());
        values.put(COLUMN_TYPE, contacts.getType());
        values.put(COLUMN_SLOT, contacts.getSlots());
        values.put(COLUMN_ROWS, contacts.getRows());
        values.put(COLUMN_TIER, contacts.getTier());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_CONTACTS, null, values);
    }

    public void updateContacts(RespData contacts){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NO_CONTAINER, contacts.getNo_container());
        values.put(COLUMN_SIZE, contacts.getSize());
        values.put(COLUMN_TYPE, contacts.getType());
        values.put(COLUMN_SLOT, contacts.getSlots());
        values.put(COLUMN_ROWS, contacts.getRows());
        values.put(COLUMN_TIER, contacts.getTier());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_CONTACTS, values, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(contacts.getId())});
    }

    public RespData findContacts(String name){
        String query = "Select * FROM "	+ TABLE_CONTACTS + " WHERE " + COLUMN_NO_CONTAINER + " = " + "name";
        SQLiteDatabase db = this.getWritableDatabase();
        RespData contacts = null;
        Cursor cursor = db.rawQuery(query,	null);
        if	(cursor.moveToFirst()){
            int id = Integer.parseInt(cursor.getString(0));
            String no_container = cursor.getString(1);
            String size = cursor.getString(2);
            String type = cursor.getString(3);
            String slot = cursor.getString(4);
            String rows = cursor.getString(5);
            String tier = cursor.getString(6);
            contacts = new RespData(id, no_container, size, type, slot, rows, tier);
        }
        cursor.close();
        return contacts;
    }

    public void deleteContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(id)});
    }
}
