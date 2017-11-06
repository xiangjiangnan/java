package cn.xjn.java.domain;

/**
 * @author xiang
 */
//IP归属地，存储一些常见的Ip以及对应的归属地
public class Address {
    //编号
    private int id;
    //IP
    private String ip;
    //归属地
    private String location;
    public Address(){}
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
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}
