package com.gryzmol.model;

import java.util.Date;

public class Book {
    private String title;
    private String author;
    private Date releaseDate;
    private int pagesNumber;
    private boolean isReaded;

    public Book() {
    }

    public Book(String title, String author, Date releaseDate, int pagesNumber, boolean isReaded) {
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.pagesNumber = pagesNumber;
        this.isReaded = isReaded;
    }

    public Book(String title, String author, Date releaseDate, int pagesNumber) {
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.pagesNumber = pagesNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getPagesNumber() {
        return pagesNumber;
    }

    public void setPagesNumber(int pagesNumber) {
        this.pagesNumber = pagesNumber;
    }

    public boolean isReaded() {
        return isReaded;
    }

    public void setReaded(boolean readed) {
        isReaded = readed;
    }

//    @Override
//    public String toString() {
//        return "Book{" +
//                "title='" + title + '\'' +
//                ", author='" + author + '\'' +
//                ", releaseDate=" + releaseDate +
//                ", pagesNumber=" + pagesNumber +
//                ", isReaded=" + isReaded +
//                '}';
//    }
@Override
    public String toString() {
        return "Tytuł: '" + title + '\'' +
                ", Autor: '" + author + '\'' +
                ", Data wydania: " + releaseDate +
                ", Liczba stron: " + pagesNumber
                ;
    }

    public boolean equals(Book book) {
    if (this.title.equals(book.title) && this.author.equals(book.author)) {
        return true;
    } else {
        return false;
    }
}

}
