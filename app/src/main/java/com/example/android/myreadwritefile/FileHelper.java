package com.example.android.myreadwritefile;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileHelper {
    private static final String TAG = FileHelper.class.getName();

    // Method tsb guna untuk menyimpan masukkan dari user
    static void writeToFile(FileModel fileModel, Context context){
        try{ // Proses tsb berguna untuk menyimpan data bertipe String ke dalam sebuah berkas pada internal storage
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileModel.getFilename(), Context.MODE_PRIVATE)); // Menginisiasi OutputStreamWriter, yaitu menulis data ke dalam berkas dalam bentuk stream
            outputStreamWriter.write(fileModel.getData()); // Menulis data setelah berkas dibuka
            outputStreamWriter.close(); // Tutup berkas
        } catch (IOException e){
            Log.e(TAG, "File write failed : ", e);
        }
    }

    // Method tsb guna untuk membaca masukkan tsb
    static FileModel readFromFile(Context context, String filename){

        FileModel fileModel = new FileModel();

        try{
            InputStream inputStream = context.openFileInput(filename);

            if(inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream); // Menginisiasi InputStreamReader, yaitu data pada berkas akan dibaca menggunakan stream
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader); // Merepresentasikan data pada tiap baris dalam berkas
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                // Proses tsb dilakukan untuk menambah data ke dalam StringBuilder hingga datanya terbaca semua/ tidak ada data yang ingin dibaca
                while ((receiveString = bufferedReader.readLine()) != null){
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                fileModel.setData(stringBuilder.toString());
                fileModel.setFilename(filename);
            }
        } catch (FileNotFoundException e){
            Log.e(TAG, "File not found : ", e);
        } catch (IOException e){
            Log.e(TAG, "Can not read file : ", e);
        }

        return fileModel;
    }
}
