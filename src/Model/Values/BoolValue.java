package Model.Values;

import Model.Types.BoolType;
import Model.Types.Type;

public class BoolValue implements Value {

    private boolean value;

    public BoolValue(boolean value){
        this.value = value;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    public boolean getValue(){
        return this.value;
    }

    @Override
    public String toString(){
        return Boolean.toString(this.value);
    }

    @Override
    public Value copy(){
        return new BoolValue(this.value);
    }

}
