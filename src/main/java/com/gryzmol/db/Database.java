package com.gryzmol.db;

import com.gryzmol.model.AwaitingBook;
import com.gryzmol.model.Book;
import com.gryzmol.model.PendingBook;
import com.gryzmol.model.ReadedBook;

public interface Database {
    void addNewBook(ReadedBook book);
    void addNewBook(AwaitingBook book);
    void addNewBook(PendingBook book);
    void getBookReadedData(
            String title,
            String Author,
            String ReleaseDate,
            String getPagesNumber,
            String rate,
            String review,
            String readDate
    );
    void getBookPendingData(
            String title,
            String Author,
            String ReleaseDate,
            String getPagesNumber,
            String readedStart

    );
    void getBookAwaitingData(
            String title,
            String Author,
            String ReleaseDate,
            String getPagesNumber

    );
    void deleteBook(Book book);
    void moveFromPendingToReaded(PendingBook book,String strRate,String review,String strReadDate);



}
