package com.xjn.web.domain;

import java.sql.Timestamp;

public class Info {
    private int id;
    private String ip;
    private java.sql.Timestamp votetime;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public Timestamp getVotetime() {
        return votetime;
    }
    public void setVotetime(Timestamp votetime) {
        this.votetime = votetime;
    }
}
