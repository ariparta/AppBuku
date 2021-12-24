package id.ari.appbuku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    float textSize = 0;
    String pilih = "";
    TextView textNama;
    EditText nama, sinopsis, pemeran, tanggal, genre;
    TextView txtSeekBar;
    SeekBar seekBar;
    RadioGroup pilihan;
    RadioButton buku, komik, novel;
    CheckBox horror, romance, action, fiction, comedy, mystery;
    DatePickerDialog datePickerDialog;
    DBHelper dbHelper;
    Button submit;
    private boolean isEditMode = false;
    private String id, nama_buku, genre_buku, tanggal_buku, sinopsis_buku, rating, nama_penulis, kategori_buku;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nama = findViewById(R.id.nama);
        textNama = findViewById(R.id.textnama);
        sinopsis = findViewById(R.id.sinopsis);
        pemeran = findViewById(R.id.pemeran);
        txtSeekBar = findViewById(R.id.ratingvalue);
        horror = findViewById(R.id.horror);
        romance = findViewById(R.id.romance);
        action = findViewById(R.id.action);
        fiction = findViewById(R.id.fiction);
        comedy = findViewById(R.id.comedy);
        mystery = findViewById(R.id.mystery);
        seekBar = findViewById(R.id.seekBar);
        tanggal = findViewById(R.id.tanggal);
        txtSeekBar.setText(Float.toString(textSize));
        submit = findViewById(R.id.submit);
        pilihan = findViewById(R.id.pilihan);
        novel = findViewById(R.id.Novel);
        buku = findViewById(R.id.buku);
        komik = findViewById(R.id.komik);
        dbHelper = new DBHelper(this);
        TextView tvback = (TextView)findViewById(R.id.tvback);

        tvback.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ListBukuActivity.class)));
        Intent intent = getIntent();
        isEditMode = intent.getBooleanExtra("isEditMode", false);
        if (isEditMode){
            //update data

            //get data
            id = intent.getStringExtra("id");
            nama_buku = intent.getStringExtra("nama");
            genre_buku = intent.getStringExtra("genre");
            nama_penulis = intent.getStringExtra("penulis");
            sinopsis_buku = intent.getStringExtra("sinopsis");
            tanggal_buku = intent.getStringExtra("tanggal");
            rating = intent.getStringExtra("rating");
            kategori_buku = intent.getStringExtra("kategori");

            //set data ke view
            nama.setText(nama_buku);
            pemeran.setText(nama_penulis);
            sinopsis.setText(sinopsis_buku);
            tanggal.setText(tanggal_buku);

            //set radio button checked sesuai value dari resep
            if ("Buku".equals(kategori_buku)){
                buku.setChecked(true);
            }else if ("Series".equals(kategori_buku)){
                komik.setChecked(true);
            }else if ("Novel".equals(kategori_buku)){
                novel.setChecked(true);
            }else {
                buku.setChecked(false);
                komik.setChecked(false);
                novel.setChecked(false);
            }

        }
        else {

        }
        seekBar.setProgress(0);
        seekBar.setMax(20);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                float progressD = (float) progressValue/2;
                txtSeekBar.setText(Float.toString(progressD));
            }
        });

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                tanggal.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });


        pilihan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id){
                    case R.id.buku:
                        pilih = "Buku";
                        textNama.setText("Nama Buku");
                        nama.setHint("Masukkan Nama Buku ");
                        sinopsis.setHint("Masukkan Sinopsis ");
                        break;
                    case R.id.komik:
                        pilih = "Series";
                        textNama.setText("Nama Komik");
                        nama.setHint("Masukkan Nama Komik ");
                        sinopsis.setHint("Masukkan Sinopsis ");
                        break;
                    case R.id.Novel:
                        pilih = "Novel";
                        textNama.setText("Nama Novel");
                        nama.setHint("Masukkan Nama Novel ");
                        sinopsis.setHint("Masukkan Sinopsis ");
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View button) {

                    showDialog();
            }
        });;
    }


    private void showDialog(){
        String genre = "";
        if(horror.isChecked())
            genre += "Horror ";
        if(romance.isChecked())
            genre += "Romance ";
        if(action.isChecked())
            genre += "Action ";
        if(fiction.isChecked())
            genre += "Fiction ";
        if(comedy.isChecked())
            genre += "Comedy ";
        if(mystery.isChecked())
            genre += "Mystery ";

        if(nama.getText().toString().isEmpty() || sinopsis.getText().toString().isEmpty() ||
                pilih.equals("") || pemeran.getText().toString().isEmpty() || tanggal.getText().toString().isEmpty() || genre.equals("")) {
            Toast.makeText(getApplicationContext(),"Data Belum Lengkap!",Toast.LENGTH_SHORT).show();
        }
        else {
            if (isEditMode){ //update data
                dbHelper.update(""+id,
                        ""+nama_buku,
                        ""+pilih,
                        ""+genre_buku,
                        ""+nama_penulis,
                        ""+sinopsis_buku,
                        ""+rating,
                        ""+tanggal_buku
                );
                Intent intent = new Intent(MainActivity.this, LogActivity.class);
                Toast.makeText(this,"Data berhasil Di Update :)", Toast.LENGTH_SHORT).show();
                               intent.putExtra("id_buku", id);
                startActivity(intent);
            }
            else { //insert data baru ke tabel
                Intent intent = new Intent(MainActivity.this, ListBukuActivity.class);
//                intent.putExtra("kategori", pilih);
//                intent.putExtra("nama", nama.getText().toString());
//                intent.putExtra("genre", genre);
//                intent.putExtra("sinopsis", sinopsis.getText().toString());
//                intent.putExtra("tanggal", tanggal.getText().toString());
//                intent.putExtra("penulis", pemeran.getText().toString());
//                intent.putExtra("rating", txtSeekBar.getText().toString());
//                startActivity(intent);
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                   this);
                ContentValues values = new ContentValues();
                values.put(DBHelper.row1_nama, nama.getText().toString());
                values.put(DBHelper.row2_kategori, pilih);
                values.put(DBHelper.row3_genre, genre);
                values.put(DBHelper.row4_penulis, pemeran.getText().toString());
                values.put(DBHelper.row4_sinopsis, sinopsis.getText().toString());
                values.put(DBHelper.row5_rating,txtSeekBar.getText().toString());
                values.put(DBHelper.row6_tanggal, tanggal.getText().toString());
                dbHelper.insertDataBuku(values);

                Toast.makeText(this,"Data berhasil ditambahkan :)", Toast.LENGTH_SHORT).show();

                startActivity(intent);
        }
        }

    }
}