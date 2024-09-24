package com.gryzmol.model;

import java.util.Date;

public class AwaitingBook extends Book {
    public AwaitingBook() {
    }

    public AwaitingBook(String title, String author, Date releaseDate, int pagesNumber) {
        super(title, author, releaseDate, pagesNumber);
        setReaded(false);

    }
    @Override
    public String toString() {
        return super.toString();
    }


}
