package com.gryzmol;


import com.gryzmol.core.Core;


public class App {
    public static void main(String[] args) {
        Core app = new Core();
        app.mainRun();



    }
}
/*TODO
   - sprawdzić czy dodawane książki w trakcie działania programu zabisują się do db
   - przesuwanie książek musi sprawdzać czy dana książka juz nie jistnieje w zbiorze (sprawdzenie jest tylko w metodzie addNewBook
   - sprawdzenie czy podane sa liczby
   - obsługa błędu przy niepoprawnych plikach excel w dodawaniu
   - Przesunąć try-cacth sprawdzenia daty w miejscu wywołania funkcji

//w

 */
