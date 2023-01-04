package com.example.testgson;

import static android.provider.SyncStateContract.Columns.DATA;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private Button addbtn;
    private Button savebtn;
    private EditText title;
    private EditText author;
    private EditText pages;
    private Switch switch1;

//Book[] books = new Book[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
         savebtn = findViewById(R.id.savebtn);
         addbtn = findViewById(R.id.addbtn);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String titles = String.valueOf(title.getText());
                String authors = String.valueOf(author.getText());
                int pagess = Integer.parseInt(String.valueOf(pages.getText()));


            Book[] books = new Book[5];

                books[0] = new Book(titles, authors, pagess);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = prefs.edit();
                Gson gson = new Gson();
                String booksString = gson.toJson(books);

                editor.putString(DATA, booksString);
                editor.commit();
                Toast.makeText(MainActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
            }
        });
        savebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = prefs.edit();
                Gson gson = new Gson();
                String str = prefs.getString(DATA, "");

                if (!str.equals("")) {
                    Book[] books = gson.fromJson(str, Book[].class);

                    Toast.makeText(MainActivity.this, "number of books" + books.length, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}