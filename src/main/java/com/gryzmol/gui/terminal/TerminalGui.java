package com.gryzmol.gui.terminal;

import com.gryzmol.db.list.ListRepository;
import com.gryzmol.gui.Gui;
import com.gryzmol.model.AwaitingBook;
import com.gryzmol.model.Book;
import com.gryzmol.model.PendingBook;
import com.gryzmol.model.ReadedBook;
import com.gryzmol.upload.UploadExcelFile;

import javax.swing.text.StyledEditorKit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TerminalGui implements Gui {
    private ListRepository db;
    private final Scanner scanner = new Scanner(System.in);

    public TerminalGui() {
    }

    public TerminalGui(ListRepository db) {
        this.db = db;
    }

    public ListRepository getDb() {
        return db;
    }

    public void setDb(ListRepository db) {
        this.db = db;
    }

    public void showFindedBooks(List<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void showAllBooks() {
        showComment("Ksiażki przeczytane");
        for (ReadedBook book : db.getReadedBooks()) {
            System.out.println(book);
        }
        showComment("Ksiażki obecnie czytane");
        for (PendingBook book : db.getPendingBooks()) {
            System.out.println(book);
        }
        showComment("Ksiażki oczekujące na przeczytanie");
        for (AwaitingBook book : db.getAwaitingBooks()) {
            System.out.println(book);
        }
    }

    public void showMakeBookReaded() {
        System.out.println("Podaj tytuł książki lub autora, którego chcesz przenieść do książek przeczytanych");
        String lookingBook = scanner.nextLine();

        System.out.println("Szukanie książki: " + lookingBook);
        System.out.println("Znalezione ksiażki:");
        for (Book book : db.searchBook(lookingBook)) {
            System.out.println(book);
            System.out.println("Czy przenieść książke do przeczytanych: (T/N)");
            if (this.scanner.nextLine().contains("T")) {
                System.out.println("Podaj ocene ksiażki (1-6)");
                String rate = this.scanner.nextLine();
                System.out.println("Podaj recenzje książki");
                String review = this.scanner.nextLine();
                System.out.println("Podaj date przeczytania");
                String date = this.scanner.nextLine();
                // db.moveFromAwaitingToReaded(rate,review,date);
            }

        }
    }

    public String showStartProgram() {
        System.out.println("Wybierz opcję co byś chciał zrobić:");
        System.out.println("1.Dodaj nową książke.");
        System.out.println("2.Usuń książke.");
        System.out.println("3.Pokaż wszystkie ksiazki");
        System.out.println("4.Przeczytałem ksiażke (Z ksiażke obecnie czytanych)");
        System.out.println("5. Zaczałem czytać (Z książek oczekujących do obecnie czytanych)");
        System.out.println("0. Wyjście.");
        return this.scanner.nextLine();
    }

    public void wrongChoose() {
        System.out.println(" ");
        System.out.println("######################################");
        System.out.println("##Błędny wybór. Wybierz jeszcze raz.##");
        System.out.println("######################################");
        System.out.println(" ");
    }

    public void showAddNewBookMenu() {
        boolean runAddBookMenu = true;
        while (runAddBookMenu) {
            System.out.println("Wybierz opcję dodawania książki:");
            System.out.println("1.Dodaj ksiażke przeczytaną.");
            System.out.println("2.Dodaj książke obecnie czytaną.");
            System.out.println("3.Dodaj książke planowaną do przeczytania.");
            System.out.println("4.Dodawanie książek z pliku Excel.");
            System.out.println("0. Wyjście.");
            String lookingBook = scanner.nextLine();
            switch (lookingBook) {
                case "1":
                    System.out.println("Dodawanie książek przeczytanych.");
                    showAddReadedBook();
                    break;
                case "2":
                    System.out.println("Dodawanie książek obecnie czytanych.");
                    showAddBookPending();
                    break;
                case "3":
                    System.out.println("Dodawanie książek oczekujących.");
                    showAddAwaitingBook();
                    break;
                case "4":
                    System.out.println("Dodawanie książek z pliku Excel.");
                    showAddBooksFromExcel();
                    break;
                case "0":
                    runAddBookMenu = false;
                    break;
                default:
                    this.wrongChoose();
            }
        }
    }

    private void showAddReadedBook() {
            System.out.println("Podaj tytuł.");
            String title = scanner.nextLine();
            System.out.println("Podaj autora.");
            String author = scanner.nextLine();
            System.out.println("Podaj date wydania (w formacie dd/MM/yyyy)");
            String dateRelase = scanner.nextLine();
            System.out.println("Podaj liczbe stron.");
            String pageNumber = scanner.nextLine();
            System.out.println("Podaj ocene (1-6).");
            String rate = scanner.nextLine();
            System.out.println("Dodaj krótki opis.");
            String review = scanner.nextLine();
            System.out.println("Podaj datę przeczytania.(w formacie dd/MM/yyyy)");
            String readDate = scanner.nextLine();
            db.getBookReadedData(title, author, dateRelase, pageNumber, rate, review, readDate);
            showComment("Dodano książke");


    }

    private void showAddAwaitingBook() {
        System.out.println("Podaj tytuł.");
        String title = scanner.nextLine();
        System.out.println("Podaj autora.");
        String author = scanner.nextLine();
        System.out.println("Podaj date wydania (w formacie dd/MM/yyyy)");
        String dateRelase = scanner.nextLine();
        System.out.println("Podaj liczbe stron.");
        String pageNumber = scanner.nextLine();
        db.getBookAwaitingData(title, author, dateRelase, pageNumber);
        showComment("Dodano książke");

    }

    private void showAddBookPending() {
        System.out.println("Podaj tytuł.");
        String title = scanner.nextLine();
        System.out.println("Podaj autora.");
        String author = scanner.nextLine();
        System.out.println("Podaj date wydania (w formacie dd/MM/yyyy)");
        String dateRelase = scanner.nextLine();
        System.out.println("Podaj liczbe stron.");
        String pageNumber = scanner.nextLine();
        System.out.println("Podaj date rozpoczęcia czytania (w formacie dd/MM/yyyy)");
        String readedStart = scanner.nextLine();
        db.getBookPendingData(title, author, dateRelase, pageNumber, readedStart);
        showComment("Dodano książke");
    }

    public void showDeleteBook() {
        System.out.println("Podaj tytuł lub autora");
        String titleOrAuthor = scanner.nextLine();
        List<Book> books = db.searchBook(titleOrAuthor);
        System.out.println("Ilość znalezionych książek: ");
        System.out.println(books.size());
        for (Book book : books) {
            System.out.println("Usunać książke (T/N) : ");
            System.out.println(book);
            String delete = scanner.nextLine();
            if (delete.equals("T")) {
                db.deleteBook(book);
                showComment("usunięto książke");
            }

        }
    }

    private void showComment(String tekst) {
        System.out.println(" ");
        System.out.println("#".repeat(tekst.length() + 4));
        System.out.print("##");
        System.out.print(tekst.toUpperCase());
        System.out.println("##");
        System.out.println("#".repeat(tekst.length() + 4));
        System.out.println(" ");
    }

    public void showMovedPendingToReaded() {
        ///REFAKTORYZACJA WYMAGANA

        List<PendingBook> pendingBooks = db.getPendingBooks();

        PendingBook ChooseBook = null;
        if (pendingBooks.isEmpty()) {
            showComment("Brak książek do przeniesienia.");
        } else {
            String rate = "";
            String review = "";
            String readDate = "";
            for (PendingBook book : pendingBooks) {
                System.out.println(book);
                System.out.println("Przenieść ksiażke (T/N) : ");
                String moved = scanner.nextLine();
                if (moved.equals("T")) {
                    ChooseBook = book;
                    System.out.println("Podaj ocene (1-6).");
                    rate = scanner.nextLine();
                    System.out.println("Dodaj krótki opis.");
                    review = scanner.nextLine();
                    System.out.println("Podaj datę przeczytania.(w formacie dd/MM/yyyy)");
                    readDate = scanner.nextLine();
                    break;
                }
            }
            if (ChooseBook != null) {
                db.moveFromPendingToReaded(ChooseBook, rate, review, readDate);
                showComment("Przeniesiono ksiażke do przeczytanych");
            } else {
                showComment("Nie wybrano książki do przeniesienia");
            }

        }
    }

    public void showMovedAwaitngToPending() {
        List<AwaitingBook> awitingBooks = db.getAwaitingBooks();

        AwaitingBook ChooseBook = null;
        if (awitingBooks.isEmpty()) {
            showComment("Brak książek do przeniesienia.");
        } else {
            String rate = "";
            String review = "";
            String readDate = "";
            for (AwaitingBook book : awitingBooks) {
                System.out.println(book);
                System.out.println("Przenieść ksiażke (T/N) : ");
                String moved = scanner.nextLine();
                if (moved.equals("T")) {
                    ChooseBook = book;
                    System.out.println("Podaj datę rozpoczęcia czytania(w formacie dd/MM/yyyy)");
                    readDate = scanner.nextLine();
                    break;
                }
            }
            if (ChooseBook != null) {
                db.moveFromAwaitingToPending(ChooseBook,readDate);
                showComment("Przeniesiono ksiażke do przeczytanych");
            } else {
                showComment("Nie wybrano książki do przeniesienia");
            }
        }





    }
    private void showAddBooksFromExcel(){
        System.out.println("Podaj ścieżke do pliku excel");
        String excelFilePath = scanner.nextLine();
        UploadExcelFile excel = new UploadExcelFile(excelFilePath,this.db);
        excel.uploadDataFromExcel();
        showComment("Dodano książki z pliku Excel");
    }

}