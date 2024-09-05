package com.gryzmol.model;

import java.util.Date;

public class ReadedBook extends Book {
    private double rate;
    private String review;
    private Date readDate;

   public ReadedBook() {
   }

    public ReadedBook(String title, String author, Date releaseDate, int pagesNumber, boolean isReaded, double rate, String review, Date readDate) {
        super(title, author, releaseDate, pagesNumber, isReaded);
        this.rate = rate;
        this.review = review;
        this.readDate = readDate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Date getReadDate() {
        return readDate;
    }

    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }

    @Override
    public String toString() {
        return super.toString()+
                "Ocena: '" + rate + '\'' +
                ", Recenzja: '" + rate + '\'' +
                ", Data przeczytania: " + readDate+
                review
                ;
    }

}
