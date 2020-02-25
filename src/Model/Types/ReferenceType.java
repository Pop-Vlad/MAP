package Model.Types;

import Model.Values.ReferenceValue;
import Model.Values.Value;

public class ReferenceType implements Type {

    private Type inner;

    public ReferenceType(Type innerType){
        this.inner = innerType;
    }

    public Type getInner(){
        return this.inner;
    }

    @Override
    public boolean equals(Object another){
        if(another instanceof ReferenceType) {
            Type anotherInner = ((ReferenceType) another).getInner();
            return this.inner.equals(anotherInner);
        }
        else
            return false;
    }

    @Override
    public Value defaultValue() {
        return new ReferenceValue(this.inner, 0);
    }

    @Override
    public String toString(){
        return "Ref " + this.inner.toString();
    }

    @Override
    public Type copy(){
        return new ReferenceType(this.inner.copy());
    }

}
