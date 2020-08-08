package com.sdm.ui.commands;

public class ExitSystem implements ICommand {
    @Override
    public String getDescription() {
        return "Exit System";
    }

    @Override
    public void execute() {
        System.out.println("Thanks for using my Super duper market \n" +
                "Goodbye");
        System.exit(0);
    }
}
