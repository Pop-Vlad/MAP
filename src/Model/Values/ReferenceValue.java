package Model.Values;

import Model.Types.ReferenceType;
import Model.Types.Type;

public class ReferenceValue implements Value {

    private Type locationType;
    int address;

    public ReferenceValue(Type type, int address){
        this.locationType = type;
        this.address = address;
    }

    public Type getLocationType() {
        return locationType;
    }

    public int getAddress(){
        return this.address;
    }

    @Override
    public Type getType() {
        return new ReferenceType(this.locationType);
    }

    @Override
    public String toString(){
        return "(" + this.address + "," + this.locationType.toString() + ")";
    }

    @Override
    public Value copy(){
        return new ReferenceValue(this.locationType, this.address);
    }

}
