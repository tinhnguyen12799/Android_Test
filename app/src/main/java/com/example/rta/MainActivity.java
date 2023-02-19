package com.example.rta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rta.Adapter.FileAdapter;
import com.example.rta.Database.DatabaseHandler;
import com.example.rta.Model.FileTable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FileAdapter fileAdapter ;
    ArrayList<String> dsFile = new ArrayList<>();
    ListView lvFiles;
    Button btnShow;
    DatabaseHandler database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        DatabaseHandler database = new DatabaseHandler(MainActivity.this);
        database.clearFile();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        btnShow = findViewById(R.id.btnDetail);
        lvFiles = findViewById(R.id.lvFile);
        AssetManager assetManager = getAssets();
        String[] files;

        try {
            files = assetManager.list("data");
            dsFile = new ArrayList<>(Arrays.asList(files));
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileAdapter = new FileAdapter(this,R.layout.row_item,dsFile);
        lvFiles.setAdapter(fileAdapter);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                ArrayList<FileTable> ds = database.getData();
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("fileTables",ds);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}