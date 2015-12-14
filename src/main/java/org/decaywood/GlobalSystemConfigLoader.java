package org.decaywood;

import org.decaywood.entity.Entry;
import org.decaywood.utils.FileLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author: decaywood
 * @date: 2015/12/11 10:17
 */


public abstract class GlobalSystemConfigLoader {

    /**
     *  只读，无需同步
     */
    private static final Map<String, String> RMIConfig = new HashMap<>();

    private static final List<Entry<String, Integer>> addresses = new ArrayList<>();

    private static final List<Predicate<String>> filters;

    static {
        filters = new ArrayList<>();
        filters.add(x -> x == null || x.trim().contains("##") || x.length() == 0);
        filters.add(x -> {
            if (x.contains("master_req_ip")) {
                String[] kv = x.split("=")[1].split(":");
                String address = kv[0].trim();
                Integer port = Integer.parseInt(kv[1].trim());
                addresses.add(new Entry<>(address, port));
                return true;
            } else return false;
        });
    }

    /**
     * 避免多线程情况重复加载
     */
    private static volatile boolean loaded = false;

    public static void loadConfig() {

        if(loaded) return;

        loaded = true;

        loadSystemConfig();
        loadRMIConfig();

    }

    public static Entry<String, Integer> getAddress(int index) {
        if(index < 0 || index >= addresses.size()) throw new ArrayIndexOutOfBoundsException();
        return addresses.get(index);
    }


    public static int getAddressSize() {
        return addresses.size();
    }


    /**
     * 加载系统配置
     */
    private static void loadSystemConfig() {

        File file = FileLoader.loadFile("config.sys");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            final String[] text = new String[1];
            while ((text[0] = reader.readLine()) != null) {
                if (filters.stream().noneMatch(x -> x.test(text[0]))) {
                    String[] kv = text[0].split("=");
                    String key = kv[0];
                    String value = kv[1];
                    System.setProperty(key.trim(), value.trim());
                }
            }
        } catch (IOException e) {
            System.out.println("GlobalSystemConfigLoader -> 配置文件 -- config.sys 格式错误!");
        }

    }


    /**
     * 加载RMI配置
     */
    private static void loadRMIConfig() {

        File file = FileLoader.loadFile("config.rmi");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            final String[] text = new String[1];
            while ((text[0] = reader.readLine()) != null) {
                if (filters.stream().noneMatch(x -> x.test(text[0]))) {
                    String[] kv = text[0].split("=");
                    String key = kv[0];
                    String value = kv[1];
                    RMIConfig.put(key.trim(), value.trim());
                }
            }
        } catch (IOException e) {
            System.out.println("GlobalSystemConfigLoader -> 配置文件 -- config.rmi 格式错误!");
        }
    }

    /**
     *
     * @param key RMI 属性名字
     * @return RMI 对应配置
     */
    public static String getRMIConfig(String key) {
        return RMIConfig.get(key);
    }

}
