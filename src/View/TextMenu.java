package View;

import View.Commands.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {

    private Map<String, Command> commands;

    public TextMenu(){
        commands = new HashMap<String, Command>();
    }

    public void addCommand(Command newCommand){
        commands.put(newCommand.getKey(), newCommand);
    }

    private void printMenu(){
        System.out.println("");
        for(Command element : commands.values()){
            String line = element.getKey() + " : " + element.getDescription();
            System.out.println(line);
        }
    }

    public void run(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            printMenu();
            System.out.print("Give the option: ");
            String key = scanner.nextLine();
            Command toRun = commands.get(key);
            if(toRun == null){
                System.out.println("Invalid option");
                System.out.println("");
                continue;
            }
            System.out.println("");
            toRun.execute();
        }
    }

}
