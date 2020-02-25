package Model.Types;

import Model.Values.Value;

public interface Type {

    public boolean equals(Object another);
    public Value defaultValue();
    public Type copy();

}
