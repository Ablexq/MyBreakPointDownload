package com.xq.mybreakpointdownload.bean;

import java.io.Serializable;

/**
 * create by luoxiaoke on 2016/4/29 15:19.
 * use for 文件实体类
 */
public class FileInfo implements Serializable {
    private int id;
    private String url;
    private String fileName;
    private long length;
    private long finished;

    public FileInfo() {
    }

    public FileInfo(int id, String url, String fileName, long length, long finished) {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
        this.length = length;
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getFinished() {
        return finished;
    }

    public void setFinished(long finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FileInfo{");
        sb.append("id=").append(id);
        sb.append(", url='").append(url).append('\'');
        sb.append(", fileName='").append(fileName).append('\'');
        sb.append(", length=").append(length);
        sb.append(", finish=").append(finished);
        sb.append('}');
        return sb.toString();
    }
}
