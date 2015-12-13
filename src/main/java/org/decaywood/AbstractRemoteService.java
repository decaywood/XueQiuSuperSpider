package org.decaywood;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.decaywood.entity.Entry;
import org.decaywood.remote.slaveChooser.RandomSlaveChooser;
import org.decaywood.remote.slaveChooser.SlaveChooser;
import org.decaywood.timeWaitingStrategy.DefaultTimeWaitingStrategy;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.HttpRequestHelper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author: decaywood
 * @date: 2015/12/4 13:35
 */
public abstract class AbstractRemoteService extends UnicastRemoteObject {

    static {
        /**
         * 加载全局配置
         */
        GlobalSystemConfigLoader.loadConfig();
    }

    protected boolean RMIOnly = false;

    protected boolean rmiMaster = false;

    protected String webSite;

    protected TimeWaitingStrategy strategy;

    protected ObjectMapper mapper;

    private SlaveChooser slaveChooser = new RandomSlaveChooser();

    public void setSlaveChooser(SlaveChooser slaveChooser) {
        this.slaveChooser = slaveChooser;
    }

    public AbstractRemoteService(TimeWaitingStrategy strategy, String webSite) throws RemoteException {
        super();
        this.webSite = webSite;
        this.strategy = strategy == null ? new DefaultTimeWaitingStrategy() : strategy;
        this.mapper = new ObjectMapper();

    }

    public void setRMIOnly(boolean RMIOnly) {
        this.RMIOnly = RMIOnly;
    }

    public boolean isRMIOnly() {
        return RMIOnly;
    }

    protected String request(URL url) throws IOException {
        return new HttpRequestHelper(webSite).request(url);
    }



    protected Object getRMIProxy() throws RemoteException, NotBoundException, MalformedURLException {

        Entry<String, Integer> entry = slaveChooser.chooseSlave();

        String slaveIP = entry.getKey();
        int port = entry.getValue();

        System.out.println(getInvokeURL(slaveIP, port));
        return Naming.lookup(getInvokeURL(slaveIP, port));
    }

    public void asRMIMaster() {
        this.rmiMaster = true;
    }


    public void asRMISlave() {
        try {

            //创建并导出接受指定port请求的本地主机上的Registry实例。
            String[] address = GlobalSystemConfigLoader.getRMIConfig("slave_rcv_ip").split(":");
            String slaveIP = address[0].trim();
            int port = Integer.parseInt(address[1].trim());

            System.setProperty("java.rmi.server.hostname", slaveIP);

            LocateRegistry.createRegistry(port);
            /**
             *  Naming 类提供在对象注册表中存储和获得远程对远程对象引用的方法
             *  Naming 类的每个方法都可将某个名称作为其一个参数
             *  该名称是使用以下形式的 URL 格式（没有 scheme 组件）的 java.lang.String:
             *  rmi://host:port/name
             *  host：注册表所在的主机（远程或本地)，省略则默认为本地主机
             *  port：是注册表接受调用的端口号，省略则默认为1099，RMI注册表registry使用的著名端口
             *  name：是未经注册表解释的简单字符串
             */

            Naming.rebind(getInvokeURL(slaveIP, port), this);
            System.out.println("RMI Slave 启动成功" + " URL: " + getInvokeURL(slaveIP, port));

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("RMI Slave 创建失败");
        }

    }


    private String getInvokeURL(String slaveIP, int port) {
        StringBuilder builder = new StringBuilder();
        builder.append("rmi://").append(slaveIP).append(":").append(port).append("/")
                .append(this.getClass().getSimpleName());
        return builder.toString();
    }

}
