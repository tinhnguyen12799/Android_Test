package com.example.rta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;


import com.example.rta.Adapter.File2Adapter;
import com.example.rta.Database.DatabaseHandler;
import com.example.rta.Model.FileTable;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    File2Adapter fileAdapter ;
    ArrayList<FileTable> dsFile = new ArrayList<>();
    ListView lvFiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        loadData();
        lvFiles = findViewById(R.id.lvFile2);
        Bundle b = getIntent().getExtras();
        ArrayList<FileTable> filelist = (ArrayList<FileTable>) b.getSerializable("fileTables");
        fileAdapter = new File2Adapter(this,R.layout.row_item,filelist);
        lvFiles.setAdapter(fileAdapter);
    }

    private void loadData() {
        DatabaseHandler db = new DatabaseHandler(Main2Activity.this);
        dsFile = db.getData();
    }
}