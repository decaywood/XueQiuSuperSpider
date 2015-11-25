package org.decaywood.entity;

/**
 * @author: decaywood
 * @date: 2015/11/25 21:46.
 */
public class Cube implements DeepCopy<Cube> {

    private final String cubeID;
    private final String name;
    private final String symbol;

    public Cube(String cubeID, String name, String symbol) {
        this.cubeID = cubeID;
        this.name = name;
        this.symbol = symbol;
    }


    @Override
    public Cube copy() {
        Cube cube = new Cube(cubeID, name, symbol);
        return cube;
    }
}
