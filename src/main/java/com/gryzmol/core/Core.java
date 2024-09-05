package com.gryzmol.core;

import com.gryzmol.db.list.ListRepository;
import com.gryzmol.gui.terminal.TerminalGui;
import org.apache.poi.util.Internal;

public class Core {
    private final ListRepository db = new ListRepository();
    private final TerminalGui tg = new TerminalGui(this.db);

    public Core() {
    }

    public void mainRun(){
        boolean run = true;
        while (run){

            switch(tg.showStartProgram()){
                case "1":
                    tg.showAddNewBookMenu();
                    break;
                case "2":
                    tg.showDeleteBook();
                    break;
                case "3":
                    tg.showAllBooks();
                    break;
                case "4":
                    tg.showMovedPendingToReaded();
                    break;
                case "5":
                    tg.showMovedAwaitngToPending();
                    break;
                case "0":
                    run = false;
                    break;
                default:
                    tg.wrongChoose();
                    break;
            }
        }
    }

}

