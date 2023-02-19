package com.example.rta.Adapter;


import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class FileAdapter extends ArrayAdapter {
    ArrayList<String> instanceList = new ArrayList<>();
    private ArrayList<String> dsFile;
    private Context context ;
    private int resource;
    public int max = 100;

    public FileAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
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
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.row_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = convertView.findViewById(R.id.tvFileName);
            viewHolder.btnImport = convertView.findViewById(R.id.btnImport);
            viewHolder.progressBar = convertView.findViewById(R.id.progress_horizontal);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();

        }

        String file = dsFile.get(position);
        viewHolder.tvName.setText("File Name:" + file);
        viewHolder.progressBar.setProgress(100);

        viewHolder.btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String outDir= Environment.getExternalStorageDirectory().getAbsolutePath() + "/" ;
                File outFile = new File(outDir,file);

                if (!outFile.exists()) {
                    outFile.mkdir();
                }
                InputStream in = null;
                OutputStream out = null;
                try {
                    in = getContext().getAssets().open("data/" + file);
                    out = new FileOutputStream(outFile);
                    copyFile(in, out);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Thread backgroundThread  = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        parserXMLtoData(file,outFile.getAbsolutePath());
                    }
                });
                backgroundThread.start();
            }
        });
        return convertView;
    }

    public class ViewHolder {
        TextView tvName;
        Button btnImport;
        ProgressBar progressBar;
    }

    public void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    private void parserXMLtoData(String path, String pathNew) {
        XmlPullParserFactory instance;
        XmlPullParser parser;
        InputStream inputStream;
        DatabaseHandler  db = new DatabaseHandler(context);

        try {
            inputStream = getContext().getAssets().open("data/" + path);
            instance = XmlPullParserFactory.newInstance();
            parser = instance.newPullParser();
            parser.setInput(inputStream,null);

            int event = parser.getEventType();
            String tag ;
            String text = " ";
            while(event != XmlPullParser.END_DOCUMENT){
                tag = parser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:

                        break;
                    case XmlPullParser.TEXT:
                        text=parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (tag.equals("instanceID")) {
                            instanceList.add(text);
                            FileTable fileTable = new FileTable(0,text,pathNew);
                            db.insertFile(fileTable);
//                            System.out.println(text);
                        }
                        break;
                }
                event = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
