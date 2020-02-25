package View.Commands;

import Controller.Controller;
import Model.Exceptions.MyException;

public class RunProgramCommand extends Command{

    private Controller controller;

    public RunProgramCommand(String key, String description, Controller initController){
        super(key, description);
        this.controller = initController;
    }

    @Override
    public void execute() {
        try{
            controller.allStep();
        }
        catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }

}
