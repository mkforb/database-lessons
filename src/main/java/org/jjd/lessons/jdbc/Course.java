package org.jjd.lessons.jdbc;

/**
 * Created by User on 31.05.2021.
 */
public class Course {
    private int id;
    private String title;
    private int duration;
    private double price;

    public Course(String title, int duration, double price) {
        this.title = title;
        this.duration = duration;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", price=" + price +
                '}';
    }
}
