package org.decaywood.entity;

import org.decaywood.utils.EmptyObject;

/**
 * @author: decaywood
 * @date: 2015/11/25 21:46.
 */
public class Cube implements DeepCopy<Cube> {

    private final String cubeID;
    private final String name;
    private final String symbol;

    private CubeTrend cubeTrend = EmptyObject.emptyCubeTrend;

    public Cube(String cubeID, String name, String symbol) {
        this.cubeID = cubeID;
        this.name = name;
        this.symbol = symbol;
    }

    public void setCubeTrend(CubeTrend cubeTrend) {
        this.cubeTrend = cubeTrend;
    }


    @Override
    public Cube copy() {
        Cube cube = new Cube(cubeID, name, symbol);
        cube.setCubeTrend(cubeTrend);
        return cube;
    }


}
