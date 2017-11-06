package cn.xjn.java.domain;

//版主
public class Admin {
    //编号
    private int id;
    //姓名
    private String name;
    //版块名
    private String title;
    public Admin(){}
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}

