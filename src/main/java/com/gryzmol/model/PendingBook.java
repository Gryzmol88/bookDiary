package com.gryzmol.model;

import java.util.Date;

public class PendingBook extends Book {
private Date readedStart;

    public PendingBook() {
    }

    public PendingBook(String title, String author, Date releaseDate, int pagesNumber, boolean isReaded, Date readedStart) {
        super(title, author, releaseDate, pagesNumber, isReaded);
        this.readedStart = readedStart;
    }

    public Date getReadedStart() {
        return readedStart;
    }

    public void setReadedStart(Date readedStart) {
        this.readedStart = readedStart;
    }
    @Override
    public String toString() {
        return super.toString()+
                "Data rozpoczęcia czytania: '" + readedStart + '\''
                ;
    }
}
