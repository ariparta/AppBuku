package id.ari.appbuku;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
public class LogActivity extends AppCompatActivity {

    TextView kategori, nama, genre, sinopsis, tanggal, pemeran, rating, textnama;
    private String id;
    private DBHelper databaseHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        kategori = findViewById(R.id.kategori);
        textnama = findViewById(R.id.textnama);
        nama = findViewById(R.id.nama);
        genre = findViewById(R.id.genre);
        sinopsis = findViewById(R.id.sinopsis);
        tanggal = findViewById(R.id.tanggal);
        pemeran = findViewById(R.id.pemeran);
        rating = findViewById(R.id.rating);


        kategori.setText("Data " +getIntent().getExtras().getString("kategori"));
        textnama.setText("Nama "+getIntent().getExtras().getString("kategori"));
        nama.setText(getIntent().getExtras().getString("nama"));
        genre.setText(getIntent().getExtras().getString("genre"));
        sinopsis.setText(getIntent().getExtras().getString("sinopsis"));
        tanggal.setText(getIntent().getExtras().getString("tanggal"));
        pemeran.setText(getIntent().getExtras().getString("penulis"));
        rating.setText(getIntent().getExtras().getString("rating"));

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        databaseHelper = new DBHelper(this);

        showDetailResep();

    }
    private void showDetailResep() {
        String QUERY = "SELECT * FROM table_buku WHERE id = "+id;

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(QUERY, null);

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") String id = ""+ cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String judul_buku = ""+ cursor.getString(cursor.getColumnIndex("nama_buku"));
                @SuppressLint("Range") String kategori_buku = ""+ cursor.getString(cursor.getColumnIndex("kategori"));
                @SuppressLint("Range") String genre_buku = ""+ cursor.getString(cursor.getColumnIndex("genre_buku"));
                @SuppressLint("Range") String sinopsis_buku = ""+ cursor.getString(cursor.getColumnIndex("sinopsis_buku"));
                @SuppressLint("Range") String penulis_buku = ""+ cursor.getString(cursor.getColumnIndex("penulis_buku"));
                @SuppressLint("Range") String rating_buku = ""+ cursor.getString(cursor.getColumnIndex("rating_buku"));
                @SuppressLint("Range") String tanggal_buku = ""+ cursor.getString(cursor.getColumnIndex("tanggal"));

                nama.setText(judul_buku);
                textnama.setText(kategori_buku);
                kategori.setText(kategori_buku);
                genre.setText(genre_buku);
                sinopsis.setText(sinopsis_buku);
                tanggal.setText(tanggal_buku);
                pemeran.setText(penulis_buku);
                rating.setText(rating_buku);

            }while (cursor.moveToNext());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, ListBukuActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Application On Start", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "Application On Stop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "Application On Restart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Application On Resume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Application On Pause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Selamat Tinggal", Toast.LENGTH_SHORT).show();
    }
}

