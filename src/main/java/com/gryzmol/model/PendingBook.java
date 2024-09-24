package com.gryzmol.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        return super.toString()+
                ", Data rozpoczęcia czytania: '" + dtf.format(readedStart)  + '\''
                ;
    }
}
