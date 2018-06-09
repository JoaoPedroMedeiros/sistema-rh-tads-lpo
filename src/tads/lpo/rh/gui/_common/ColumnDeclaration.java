package tads.lpo.rh.gui._common;

import javafx.util.Callback;

public class ColumnDeclaration<T, P> {

    private final String name;

    private final Callback<T, P> valueAtStrategy;

    public ColumnDeclaration(String name, Callback<T, P> valueAtStrategy) {
        this.name = name;
        this.valueAtStrategy = valueAtStrategy;
    }

    public String getName() {
        return name;
    }

    public Callback<T, P> getValueAtStrategy() {
        return valueAtStrategy;
    }

    @Override
    public String toString() {
        return getName();
    }
}