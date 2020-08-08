package com.sdm.ui;

import com.sdm.ui.commands.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ConsoleUIRunner {

    static List<ICommand> menuCommands = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            printMenu();
            int choice = Integer.parseInt(scanner.nextLine());
            menuCommands.get(choice - 1).execute();
        }
    }

    private static void printMenu() {
        System.out.println("Please choose your option (press the digit and then enter)");
        for (int i = 0; i < menuCommands.size() ; i++) {
            System.out.println(String.format("%d) %s", i+1, menuCommands.get(i).getDescription()));
        }
    }

    private static void initICommandList() {
        menuCommands.add(new ReadFileXml());
        menuCommands.add(new ShowVendorsInfo());
        menuCommands.add(new ShowProductsInfo());
        menuCommands.add(new MakePurchase());
        menuCommands.add(new ShowPurchaseHistory());
        menuCommands.add(new ExitSystem());
    }
}