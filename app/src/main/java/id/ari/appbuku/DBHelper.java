/*
 * Made With Love
 * Author @Moh Husni Mubaraq
 * Not for Commercial Purpose
 */

package id.ari.appbuku;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String database_name = "db_login";
    public static final String table_name = "table_login";
    public static final String table_name2 = "table_buku";
    public static final String row_id1 = "id";
    public static final String row1_nama = "nama_buku";
    public static final String row2_kategori = "kategori";
    public static final String row3_genre = "genre_buku";
    public static final String row4_sinopsis = "sinopsis_buku";
    public static final String row4_penulis = "penulis_buku";
    public static final String row5_rating = "rating_buku";
    public static final String row6_tanggal = "tanggal";
    public static final String row_id = "_id";
    public static final String row_username = "Username";
    public static final String row_password = "Password";

    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, database_name, null, 2);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table_name + "(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + row_username + " TEXT," + row_password + " TEXT)";
        String query2 = "CREATE TABLE " + table_name2 + "(" + row_id1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + row1_nama + " TEXT," +row2_kategori + " TEXT," +row3_genre +" TEXT," + row4_penulis +" TEXT," +row5_rating + " TEXT," + row4_sinopsis + " TEXT," + row6_tanggal + " TEXT)";
        db.execSQL(query);
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        db.execSQL("DROP TABLE IF EXISTS " + table_name2);
    }

    //Insert Data
    public void insertData(ContentValues values){
        db.insert(table_name, null, values);
    }


    public boolean checkUser(String username, String password){
        String[] columns = {row_id};
        SQLiteDatabase db = getReadableDatabase();
        String selection = row_username + "=?" + " and " + row_password + "=?";
        String[] selectionArgs = {username,password};
        Cursor cursor = db.query(table_name, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count>0)
            return true;
        else
            return false;
    }
    public void insertDataBuku(ContentValues values){
        db.insert(table_name2, null, values);
    }


    public ArrayList<Buku> ambilDataBuku() {
        ArrayList<Buku> arrayList = new ArrayList<>();

        String QUERY = "SELECT * FROM table_buku";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(QUERY, null);

        if (cursor.moveToFirst()){
            do {
                Buku buku = new Buku();
                buku.setId(cursor.getString(0));
                buku.setNama_buku(cursor.getString(1));
                buku.setKategori(cursor.getString(2));
                buku.setGenre_buku(cursor.getString(3));
                buku.setPenulis_buku(cursor.getString(4));
                buku.setRating_buku(cursor.getString(5));
                buku.setSinopsis_buku(cursor.getString(6));
                buku.setTanggal(cursor.getString(7));
                arrayList.add(buku);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }
    public void update(String row_id1, String row1_nama, String row2_kategori, String row3_genre, String row4_penulis, String row4_sinopsis, String row5_rating, String row6_tanggal){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("nama_buku",row1_nama);
        values.put("kategori", row2_kategori);
        values.put("genre", row3_genre);
        values.put("penulis", row4_penulis);
        values.put("sinopsis", row4_sinopsis);
        values.put("rating", row5_rating);
        values.put("tanggal", row6_tanggal);

        database.update("table_buku", values, "id = ?", new String[]{row_id1});

        database.close();


    }


    public void delete(String row_id1){
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete("table_buku", "id = ?", new String[]{row_id1});
        database.close();

    }
}

