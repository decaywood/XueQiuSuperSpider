package org.decaywood.remote.slaveChooser;

import org.decaywood.GlobalSystemConfigLoader;
import org.decaywood.entity.Entry;

import java.util.Random;

/**
 * @author: decaywood
 * @date: 2015/12/13 21:12.
 */


/**
 * 随机远程服务选择器
 */
public class RandomSlaveChooser implements SlaveChooser {


    @Override
    public Entry<String, Integer> chooseSlave() {
        int size = GlobalSystemConfigLoader.getAddressSize();
        Random random = new Random();
        int index = (int) (random.nextDouble() * size);
        return GlobalSystemConfigLoader.getAddress(index);
    }



}
