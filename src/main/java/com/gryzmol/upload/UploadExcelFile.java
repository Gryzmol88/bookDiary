package com.gryzmol.upload;

import com.gryzmol.db.list.ListRepository;
import com.gryzmol.model.AwaitingBook;
import com.gryzmol.model.PendingBook;
import com.gryzmol.model.ReadedBook;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Stream;

public class UploadExcelFile {
    private final String fileLocation ;
    private ListRepository db;

    public UploadExcelFile(String fileLocation, ListRepository db) {
        this.fileLocation = fileLocation;
        this.db = db;
    }


    private void readExcelFile()
            throws IOException {
        try (FileInputStream file = new FileInputStream(fileLocation); ReadableWorkbook wb = new ReadableWorkbook(file)) {
            Stream<Sheet> sheets = wb.getSheets();
            sheets.forEach(this::readExcelSheet);
        }
    }


    private void readExcelSheet(Sheet sheet){
        try (Stream<Row> rows = sheet.openStream()) {
            rows.forEach(r -> {
                if (r.getRowNum() != 1) {
                    if (sheet.getName().equals("przeczytane_książki")){
                            db.addNewBook(readedBookFromExcel(r));
                    }
                    if (sheet.getName().equals("czytane_książki")){
                        db.addNewBook(pendingBookFromExcel(r));
                    }
                    if (sheet.getName().equals("do_przeczytania")){
                       db.addNewBook(AwaitingBookFromExcel(r));
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private ReadedBook readedBookFromExcel(Row row) {
        String title = row.getCell(0).asString();
        String author = row.getCell(1).asString();
        Date realseDate = Date.from(row.getCell(2).asDate().toInstant(ZoneOffset.UTC));
        int pageNumber = row.getCell(3).asNumber().intValue();
        boolean isReaded = true;
        double rate = row.getCell(4).asNumber().doubleValue();
        String review = row.getCell(5).asString();
        Date readDate = Date.from(row.getCell(6).asDate().toInstant(ZoneOffset.UTC));

        return new ReadedBook(title, author, realseDate, pageNumber, isReaded, rate, review, readDate);
    }
    private PendingBook pendingBookFromExcel(Row row) {
        String title = row.getCell(0).asString();
        String author = row.getCell(1).asString();
        Date realseDate = Date.from(row.getCell(2).asDate().toInstant(ZoneOffset.UTC));
        int pageNumber = row.getCell(3).asNumber().intValue();
        boolean isReaded = false;
        Date readStartDate = Date.from(row.getCell(4).asDate().toInstant(ZoneOffset.UTC));

        return new PendingBook(title, author, realseDate, pageNumber, isReaded, readStartDate);
    }

    private AwaitingBook AwaitingBookFromExcel(Row row) {
        String title = row.getCell(0).asString();
        String author = row.getCell(1).asString();
        Date realseDate = Date.from(row.getCell(2).asDate().toInstant(ZoneOffset.UTC));
        int pageNumber = row.getCell(3).asNumber().intValue();

        return new AwaitingBook(title, author, realseDate, pageNumber);
    }


    public void uploadDataFromExcel(){
       // UploadExcelFile excel = new UploadExcelFile(excelFilePath, this.db);
        try {
            this.readExcelFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}



