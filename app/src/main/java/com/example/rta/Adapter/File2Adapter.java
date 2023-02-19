package com.example.rta.Adapter;


import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rta.Database.DatabaseHandler;
import com.example.rta.Model.FileTable;
import com.example.rta.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class File2Adapter extends ArrayAdapter {
    private ArrayList<FileTable> dsFile;
    private Context context ;
    private int resource;

    public File2Adapter(@NonNull Context context, int resource, @NonNull ArrayList<FileTable> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.dsFile = objects;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return super.getPosition(item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.row_item2,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = convertView.findViewById(R.id.tvFileName2);
            viewHolder.tvPath = convertView.findViewById(R.id.tvPath);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();

        }

        FileTable file = dsFile.get(position);
        viewHolder.tvName.setText("InstanceID: "+file.getInstanceName());
        viewHolder.tvPath.setText("Path: "+file.getPath());
        return convertView;
    }

    public class ViewHolder {
        TextView tvName;
        TextView tvPath;
    }

}
