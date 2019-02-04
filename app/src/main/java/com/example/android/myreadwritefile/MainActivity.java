package com.example.android.myreadwritefile;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnNew, btnOpen, btnSave;
    EditText editContent, editTitle;
    File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign view by id
        btnNew = findViewById(R.id.button_new);
        btnOpen = findViewById(R.id.button_open);
        btnSave = findViewById(R.id.button_save);
        editContent = findViewById(R.id.edit_file);
        editTitle = findViewById(R.id.edit_title);

        // Set click listener untuk button
        btnNew.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        path = getFilesDir();
    }

    // Method tsb berguna utk membuat berkas baru
    public void newFile(){
        // Set isi dari edit text jadi kosong
        editTitle.setText("");
        editContent.setText("");

        // Set Toast message
        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show();
    }

    // Tampilkan berkas yg dipilih
    private void loadData(String title){
        FileModel fileModel = FileHelper.readFromFile(this, title); // Ini adalah contoh pemanggilan method static, yaitu kita tidak butuh menginisiasi object untuk dapat akses
        // Kita mengambil isi dari file trus masukkan ke FileModel, lalu digunakan untuk isi editTitle dan editContent
        editTitle.setText(fileModel.getFilename());
        editContent.setText(fileModel.getData());
        Toast.makeText(this, "Loading " + fileModel.getFilename() + " data", Toast.LENGTH_SHORT).show();
    }

    // Method tsb berguna untuk membuka sebuah berkas
    private void openFile(){
        showList();
    }

    // Method tsb berguna untuk memilih file mana yg mau dipilih
    private void showList(){
        ArrayList<String> arrayList = new ArrayList<>();
        // Ini adalah code untuk menampilkan data internal storage
        Collections.addAll(arrayList, path.list()); // Method path.list akan menampilkan semua berkas/file yang ada

        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

        // Membuat AlertDialog yang menampilkan daftar semua berkas
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file yang diinginkan");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            // Method tsb terjadi ketika kita pilih sala satu berkas yg ada, sehingga kita memanggil loadData method
            @Override
            public void onClick(DialogInterface dialog, int item) {
                loadData(items[item].toString());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    // Method tsb berguna untuk menyimpan berkas
    public void saveFile(){
        if(editTitle.getText().toString().isEmpty()){
            Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else if(editContent.getText().toString().isEmpty()) {
            Toast.makeText(this, "Kontent harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else { // Kondisi tsb dijalankan jika editTitle dan editContent tidak kosong
            String title = editTitle.getText().toString();
            String text = editContent.getText().toString();
            FileModel fileModel = new FileModel();
            fileModel.setFilename(title);
            fileModel.setData(text);
            FileHelper.writeToFile(fileModel, this); // Method ini digunakan untuk menyimpan data ke file
            Toast.makeText(this, "Saving " + fileModel.getFilename() + " file", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.button_new:
                newFile(); // Panggil newFile method
                break;
            case R.id.button_open:
                openFile(); // Panggil openFile method
                break;
            case R.id.button_save:
                saveFile(); // Panggil saveFile method
                break;
        }
    }
}
