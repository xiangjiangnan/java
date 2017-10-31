package com.xjn.web.domain;

//候选人详细信息,注意此时没有定义vid
public class Content{
    private int id;
    private String description;
    private int age;
    private Vote vote;
    public Content(){}
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public Vote getVote() {
        return vote;
    }
    public void setVote(Vote vote) {
        this.vote = vote;
    }
}


