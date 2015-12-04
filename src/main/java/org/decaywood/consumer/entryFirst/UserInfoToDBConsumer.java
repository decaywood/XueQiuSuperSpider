package org.decaywood.consumer.entryFirst;

import org.decaywood.consumer.AbstractConsumer;
import org.decaywood.entity.Entry;
import org.decaywood.entity.Stock;
import org.decaywood.utils.DatabaseAccessor;

import java.sql.Connection;
import java.sql.PreparedStatement;


/**
 * @author: decaywood
 * @date: 2015/12/1 13:27
 */
public class UserInfoToDBConsumer extends AbstractConsumer<Entry<Stock, Integer>> {



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
