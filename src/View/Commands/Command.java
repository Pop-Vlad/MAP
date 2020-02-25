package View.Commands;

public abstract class Command {

    private String key;
    private String description;

    public Command(String initKey, String initDescription) {
        this.key = initKey;
        this.description = initDescription;
    }

    public abstract void execute();

    public String getKey(){
        return key;
    }

    public String getDescription(){
        return description;
    }

}

