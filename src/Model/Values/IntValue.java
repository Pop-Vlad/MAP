package Model.Values;

import Model.Types.IntType;
import Model.Types.Type;

public class IntValue implements Value {

    private int value;

    public IntValue(int value){
        this.value = value;
    }

    @Override
    public Type getType(){
        return new IntType();
    }

    public int getValue(){
        return this.value;
    }

    @Override
    public String toString(){
        return Integer.toString(this.value);
    }

    @Override
    public Value copy(){
        return new IntValue(this.value);
    }

}