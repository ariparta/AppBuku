package id.ari.appbuku;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DBAdapter extends RecyclerView.Adapter<DBAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Buku> arrayList;
    DBHelper databaseHelper;

    //constructor
    public DBAdapter(Context context, ArrayList<Buku> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

        databaseHelper = new DBHelper(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namabuku, kategori, genre, namapenulis;
        Button editBtn, deleteBtn;

        public ViewHolder(@NonNull View view) {
            super(view);

            namabuku = view.findViewById(R.id.nama_buku);
            genre = view.findViewById(R.id.genre_buku);
            namapenulis = view.findViewById(R.id.nama_penulis);
            kategori = view.findViewById(R.id.kategori_buku);
            editBtn = view.findViewById(R.id.editBtn);
            deleteBtn = view.findViewById(R.id.deleteBtn);
        }
    }

    @NonNull
    @Override
    public DBAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //infalate layout
        View view = LayoutInflater.from(context).inflate(R.layout.list_buku, parent, false);
        return new ViewHolder(view);
    }

    //get data, set data, handle view click in method
    @Override
    public void onBindViewHolder(@NonNull DBAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        //get data
        Buku Buku = arrayList.get(position);
        String id = Buku.getId();
        String nama = Buku.getNama_buku();
        String kategori = Buku.getKategori();
        String genre = Buku.getGenre_buku();
        String penulis = Buku.getPenulis_buku();
        String rating = Buku.getRating_buku();
        String tanggal = Buku.getTanggal();
        String sinopsis = Buku.getSinopsis_buku();


        //set data ke views
        holder.namabuku.setText(nama);
        holder.kategori.setText(kategori);
        holder.namapenulis.setText(penulis);
        holder.genre.setText(genre);




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pass record id to next activity to show details of that records
                Intent intent = new Intent(context, LogActivity.class);
                intent.putExtra("id", id);
                context.startActivity(intent);

            }
        });

        //handle tombol more untuk edit, delete
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("nama", nama);
                intent.putExtra("kategori", kategori);
                intent.putExtra("sinopsis", sinopsis);
                intent.putExtra("tanggal",tanggal);
                intent.putExtra("penulis", penulis);
                intent.putExtra("rating", rating);
                intent.putExtra("genre", genre);
                intent.putExtra("isEditMode", true);
                context.startActivity(intent);
            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.delete(id);
                ((ListBukuActivity)context).onResume();
            }
        });

    }

//    public void showMoreDialog(String position, String id, String nama, String sinopsis, String tanggal, String nama_penulis, String rating, String genre, String kategori){
//        String[] options = {"Edit", "Delete"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //tombol edit diklik
//                if (which==0){
//                    //tombol edit diklik
//                    Intent intent = new Intent(context, MainActivity.class);
//                    intent.putExtra("id", id);
//                    intent.putExtra("nama", nama);
//                    intent.putExtra("kategori", kategori);
//                    intent.putExtra("sinopsis", sinopsis);
//                    intent.putExtra("tanggal",tanggal);
//                    intent.putExtra("penulis", nama_penulis);
//                    intent.putExtra("rating", rating);
//                    intent.putExtra("genre", genre);
//                    intent.putExtra("isEditMode", true);
//                    context.startActivity(intent);
//                }
//                //tombol delete diklik
//                else if (which==1){
//                    databaseHelper.delete(id);
//                    ((ListBukuActivity)context).onResume();
//                }
//            }
//        });
//        builder.create().show();
//    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }



}

