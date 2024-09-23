package com.gryzmol.db.list;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gryzmol.db.Database;
import com.gryzmol.model.AwaitingBook;
import com.gryzmol.model.Book;
import com.gryzmol.model.PendingBook;
import com.gryzmol.model.ReadedBook;


import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class ListRepository implements Database {

    private List<AwaitingBook> awaitingBooks = new ArrayList<>();
    private List<PendingBook> pendingBooks = new ArrayList<>();
    private List<ReadedBook> readedBooks = new ArrayList<>();
    private final String filePath = getFilePath();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ListRepository() {
            loadDate();

    }


    public List<AwaitingBook> getAwaitingBooks() {
        return awaitingBooks;
    }

    public void setAwaitingBooks(List<AwaitingBook> awaitingBooks) {
        this.awaitingBooks = awaitingBooks;
    }

    public List<PendingBook> getPendingBooks() {
        return pendingBooks;
    }

    public void setPendingBooks(List<PendingBook> pendingBooks) {
        this.pendingBooks = pendingBooks;
    }

    public List<ReadedBook> getReadedBooks() {
        return readedBooks;
    }

    public void setReadedBooks(List<ReadedBook> readedBooks) {
        this.readedBooks = readedBooks;
    }

    private void saveDate() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();

        try {
           objectNode.set("readedBooks", objectMapper.convertValue(this.readedBooks, JsonNode.class));
           objectNode.set("pendingBooks", objectMapper.convertValue(this.pendingBooks, JsonNode.class));
           objectNode.set("awaitingBooks", objectMapper.convertValue(this.awaitingBooks, JsonNode.class));

            ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
            writer.writeValue(new File(filePath), objectNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String getFilePath(){
        String filePath = System.getProperty("user.dir") +"/src/main/resources/data.json";
        return filePath;
    }

    public void addNewBook(ReadedBook book) {
        if(! isExistInDb(book)){
            this.readedBooks.add(book);
            saveDate();
        }

    }

    public void addNewBook(AwaitingBook book) {
        if(! isExistInDb(book)) {
            awaitingBooks.add(book);
            saveDate();
        }

    }

    public void addNewBook(PendingBook book) {
        if(! isExistInDb(book)) {
            this.pendingBooks.add(book);
            saveDate();
        }
    }


    private void loadDate() {
        File f = new File(filePath);
        if(f.exists()) {

        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
            String result;
                result = this.objectMapper.readTree(jsonString).path("awaitingBooks").toString();
                this.awaitingBooks = new ArrayList<>(Arrays.asList(this.objectMapper.readValue(result, AwaitingBook[].class)));

                result = this.objectMapper.readTree(jsonString).path("pendingBooks").toString();
                this.pendingBooks = new ArrayList<>(Arrays.asList(this.objectMapper.readValue(result, PendingBook[].class)));

                result = this.objectMapper.readTree(jsonString).path("readedBooks").toString();
                this.readedBooks = new ArrayList<>(Arrays.asList(this.objectMapper.readValue(result, ReadedBook[].class)));

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    }

    public List<Book> searchBook(String titleOrAuthor) {
        List<Book> books = new ArrayList<>();
        for (AwaitingBook book : this.getAwaitingBooks()) {
            if (book.getTitle().equalsIgnoreCase(titleOrAuthor) ||
                    book.getAuthor().equalsIgnoreCase(titleOrAuthor)) {
                books.add(book);
            }
                }
        for (PendingBook book : this.pendingBooks) {
            if (book.getTitle().equalsIgnoreCase(titleOrAuthor) ||
                    book.getAuthor().equalsIgnoreCase(titleOrAuthor)) {
                books.add(book);
        }}

            for (ReadedBook book : this.readedBooks) {
                if (book.getTitle().equalsIgnoreCase(titleOrAuthor) ||
                        book.getAuthor().equalsIgnoreCase(titleOrAuthor)) {
                    books.add(book);
                }
            }
        return books;
    }



    public void getBookReadedData(
            String title,
            String Author,
            String ReleaseDate,
            String getPagesNumber,
            String rate,
            String review,
            String readDate
    ) {

        //this.awaitingBooks.remove(book);
        Date date1;
        Date date2;
        int intPageNumber = Integer.parseInt(getPagesNumber);
        double doubleRate = Double.parseDouble(rate);
        date1 = makeAsData(ReleaseDate);
        date2 = makeAsData(readDate);

        ReadedBook readedBook = new ReadedBook(
                title,
                Author,
                date1,
                intPageNumber,
                true,
                doubleRate,
                review,
                date2
        );
        addNewBook(readedBook);

    }

    public void getBookPendingData(
            String title,
            String Author,
            String ReleaseDate,
            String getPagesNumber,
            String readedStart

    ) {

        int intPageNumber = Integer.parseInt(getPagesNumber);
        Date date1 = makeAsData(ReleaseDate);
        Date date2 = makeAsData(readedStart);
        PendingBook pendingBook = new PendingBook(
                title,
                Author,
                date1,
                intPageNumber,
                false,
                date2

        );
        addNewBook(pendingBook);
    }

    public void getBookAwaitingData(
            String title,
            String Author,
            String ReleaseDate,
            String getPagesNumber

    ) {

        int intPageNumber = Integer.parseInt(getPagesNumber);
        Date date1 = makeAsData(ReleaseDate);
        AwaitingBook awaitingBook = new AwaitingBook(
                title,
                Author,
                date1,
                intPageNumber

        );

        addNewBook(awaitingBook);
    }

    private Date makeAsData(String dataString) {
//        FIXME - PRzesynąć try-catch i zapętlić w razie błędu
        Date date1;
        try {
            if (dataString.contains(".")) {
                date1 = new SimpleDateFormat("dd.MM.yyyy").parse(dataString);
            } else if (dataString.contains("-")) {
                date1 = new SimpleDateFormat("dd-MM-yyyy").parse(dataString);
            }else{
                date1 = new SimpleDateFormat("dd/MM/yyyy").parse(dataString);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e + " Zły format danych. Wpisz dane w formacie dd/MM/yyyy");

        }
        return date1;
    }

    public void deleteBook(Book book) {
        if (book instanceof AwaitingBook) {
            AwaitingBook awaitingBook = (AwaitingBook) book;
            this.awaitingBooks.remove(awaitingBook);
        }
        if (book instanceof ReadedBook) {
            ReadedBook readedbook = (ReadedBook) book;
            this.readedBooks.remove(readedbook);
        }
        if (book instanceof PendingBook) {
            PendingBook pendingBook = (PendingBook) book;
            this.readedBooks.remove(pendingBook);
        }

        this.saveDate();
    }

    public void moveFromPendingToReaded(PendingBook book,String strRate,String review,String strReadDate){
        this.pendingBooks.remove(book);
        double rate = Double.parseDouble(strRate);
        Date readDate = makeAsData(strReadDate);

        ReadedBook readedBook = new ReadedBook(
                book.getTitle(),
                book.getAuthor(),
                book.getReleaseDate(),
                book.getPagesNumber(),
                true,
                rate,
                review,
                readDate
        );
        addNewBook(readedBook);

    }

    public void moveFromAwaitingToPending(AwaitingBook book,String readedStart){
        this.awaitingBooks.remove(book);
        Date readDate = makeAsData(readedStart);

        PendingBook pendingBook = new PendingBook(
                book.getTitle(),
                book.getAuthor(),
                book.getReleaseDate(),
                book.getPagesNumber(),
                false,
                readDate
        );
        addNewBook(pendingBook);

    }


    private boolean isExistInDb(AwaitingBook book){
        boolean isInDb = false;
        for (AwaitingBook bookFromDb : this.getAwaitingBooks()){
            if (bookFromDb.equals(book)){
                isInDb =true;
                break;
            }
        }
        return isInDb;
    }

    private boolean isExistInDb(ReadedBook book){
        boolean isInDb = false;
        for (ReadedBook bookFromDb : this.getReadedBooks()){
            if (bookFromDb.equals(book)){
                isInDb =true;
                break;
            }
        }
        return isInDb;
    }

    private boolean isExistInDb(PendingBook book){
        boolean isInDb = false;
        for (PendingBook bookFromDb : this.getPendingBooks()){
            if (bookFromDb.equals(book)){
                isInDb =true;
                break;
            }
        }
        return isInDb;
    }



}





