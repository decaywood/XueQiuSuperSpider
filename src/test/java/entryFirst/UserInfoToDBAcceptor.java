package entryFirst;

import org.decaywood.acceptor.AbstractAcceptor;
import org.decaywood.entity.Entry;
import org.decaywood.entity.Stock;
import org.decaywood.utils.DatabaseAccessor;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;


/**
 * @author: decaywood
 * @date: 2015/12/1 13:27
 */

/**
 * 示例类， 接收信息放入数据库
 */
public class UserInfoToDBAcceptor extends AbstractAcceptor<Entry<Stock, Integer>> {

    public UserInfoToDBAcceptor() throws RemoteException {}

    /**


     CREATE TABLE `xueqiuspider`.`stock_vip_followers` (
     `id` INT NOT NULL AUTO_INCREMENT,
     `stock_id` VARCHAR(45) NULL,
     `stock_name` VARCHAR(45) NULL,
     `vip_id` VARCHAR(45) NULL,
     `vip_count` INT NULL,
     PRIMARY KEY (`id`),
     UNIQUE INDEX `id_UNIQUE` (`id` ASC),
     UNIQUE INDEX `stock_id_UNIQUE` (`stock_id` ASC),
     UNIQUE INDEX `stock_name_UNIQUE` (`stock_name` ASC));

     */

    @Override
    protected void consumLogic(Entry<Stock, Integer> entry) throws Exception{
        Stock stock = entry.getKey();
        int k = entry.getValue();
        Connection connection = DatabaseAccessor.Holder.ACCESSOR.getConnection();
        StringBuilder builder = new StringBuilder();
        builder.append("insert into stock_vip_followers ")
                .append("(stock_id, stock_name, vip_count) ")
                .append("values (?, ?, ?)")
                .append("on duplicate key update vip_count=?");
        String sql = builder.toString();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, stock.getStockNo());
        statement.setString(2, stock.getStockName());
        statement.setInt(3, k);
        statement.setInt(4, k);
        statement.execute();
        DatabaseAccessor.Holder.ACCESSOR.returnConnection(connection);

    }


}
