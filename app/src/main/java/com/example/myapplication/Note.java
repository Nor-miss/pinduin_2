package com.example.myapplication;

public class Note {
    private int id;
    private String title;
    private String content;

    // Конструкторы
    public Note() {}

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}