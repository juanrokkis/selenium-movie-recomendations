package com.vettica.model;

import java.awt.*;

public class Post {

    private Image image;
    private Double stars;
    private String movie;
    private String trailer;

    public Post(Image image, Double stars, String movie, String trailer) {
        this.image = image;
        this.stars = stars;
        this.movie = movie;
        this.trailer = trailer;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Double getStars() {
        return stars;
    }

    public void setStars(Double stars) {
        this.stars = stars;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }
}
