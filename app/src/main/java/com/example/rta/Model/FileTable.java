package com.example.rta.Model;

import java.io.Serializable;

public class FileTable implements Serializable {
    private  int id;
    private String instanceName;
    private String path;

    public FileTable() {
    }

    public FileTable(int id, String instanceName, String path) {
        this.instanceName = instanceName;
        this.path = path;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
