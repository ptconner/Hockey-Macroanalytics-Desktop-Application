package ui;

import exceptions.NamingException;

import java.io.FileNotFoundException;

//public class Main {
//    public static void main(String[] args) throws NumberException, NamingException {
//        try {
//            new SportsAnalyticsAppCUI();
//        } catch (FileNotFoundException e) {
//            System.out.println("ERROR: file not found.");
//        }
//    }
//}


public class Main {
    public static void main(String[] args) throws NamingException {
        new SportsAnalyticsAppGUI();
    }
}
