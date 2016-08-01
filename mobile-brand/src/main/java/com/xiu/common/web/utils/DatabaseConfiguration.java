package com.xiu.common.web.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.apache.commons.configuration.MapConfiguration;
import org.apache.commons.configuration.PropertyConverter;
import org.apache.commons.logging.LogFactory;

/**
 *************************************************************** 
 * <p>
 * 
 * @DESCRIPTION : 参考Apache Common Configuration中的DatabaseConfigurationuode实现 主要解决DatabaseConfiguration每次都要查询db带来的性能问题;
 *              这个实现不支持Property的修改
 * @AUTHOR : xiu@xiu.com
 * @DATE :2012-5-5 上午10:23:34
 *       </p>
 **************************************************************** 
 */
public class DatabaseConfiguration extends MapConfiguration {
    /** The datasource to connect to the database. */
    private DataSource datasource;

    /** The name of the table containing the configurations. */
    private String table;

    /** The column containing the name of the configuration. */
    private String nameColumn;

    /** The column containing the keys. */
    private String keyColumn;

    /** The column containing the values. */
    private String valueColumn;

    /** The name of the configuration. */
    private String name;

    /**
     * Build a configuration from a table containing multiple configurations. No commits are performed by the new
     * configuration instance.
     * 
     * @param datasource
     *            the datasource to connect to the database
     * @param table
     *            the name of the table containing the configurations
     * @param nameColumn
     *            the column containing the name of the configuration
     * @param keyColumn
     *            the column containing the keys of the configuration
     * @param valueColumn
     *            the column containing the values of the configuration
     * @param name
     *            the name of the configuration
     */
    public DatabaseConfiguration(DataSource datasource, String table, String nameColumn, String keyColumn,
            String valueColumn, String name) {
        super(new ConcurrentHashMap<String, Object>());

        this.datasource = datasource;
        this.table = table;
        this.nameColumn = nameColumn;
        this.keyColumn = keyColumn;
        this.valueColumn = valueColumn;
        this.name = name;

        setLogger(LogFactory.getLog(getClass()));

        loadConfigurationFromDatabase();
    }

    /**
     * Adds a property to this configuration. Because this configuration is read-only, this operation is not allowed and
     * will cause an exception.
     * 
     * @param key
     *            the key of the property to be added
     * @param value
     *            the property value
     */
    @Override
    protected void addPropertyDirect(String key, Object value) {
        throw new UnsupportedOperationException("DatabaseConfiguration is read-only!");
    }

    /**
     * Removes a property from this configuration. Because this configuration is read-only, this operation is not
     * allowed and will cause an exception.
     * 
     * @param key
     *            the key of the property to be removed
     */
    @Override
    public void clearProperty(String key) {
        throw new UnsupportedOperationException("DatabaseConfiguration is read-only!");
    }

    /**
     * Removes all properties from this configuration. Because this configuration is read-only, this operation is not
     * allowed and will cause an exception.
     */
    @Override
    public void clear() {
        throw new UnsupportedOperationException("DatabaseConfiguration is read-only!");
    }

    protected void loadConfigurationFromDatabase() {
        HashMap<String, Object> newDatas = new HashMap<String, Object>();

        // build the query
        StringBuilder query = new StringBuilder("SELECT * FROM ");
        query.append(table).append(" WHERE 1=1");
        if (nameColumn != null) {
            query.append(" AND " + nameColumn + "=?");
        }

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getDatasource().getConnection();

            // bind the parameters
            pstmt = conn.prepareStatement(query.toString());
            if (nameColumn != null) {
                pstmt.setString(1, name);
            }

            ResultSet rs = pstmt.executeQuery();

            String keyData = null;
            Object valueData = null;
            while (rs.next()) {
                keyData = (String) rs.getObject(keyColumn);
                if (null == keyData) {
                    continue;
                }

                valueData = parseValueData(rs.getObject(valueColumn));
                if (valueData == null) {
                    continue;
                }

                newDatas.put(keyData, valueData);
            }
        } catch (SQLException e) {
        	System.out.println(getClass().getName()+" table appconfigs 数据初始化查询失败"+e.getMessage());
            fireError(EVENT_READ_PROPERTY, null, null, e);
        } finally {
            close(conn, pstmt);
        }

        getMap().putAll(newDatas);

        return;
    }

    private Object parseValueData(Object value) {
        List<Object> valueDatas = new ArrayList<Object>();
        if (isDelimiterParsingDisabled()) {
            valueDatas.add(value);
        } else {
            // Split value if it contains the list delimiter
            Iterator<?> it = PropertyConverter.toIterator(value, getListDelimiter());
            while (it.hasNext()) {
                valueDatas.add(it.next());
            }
        }

        Object result = null;
        if (!valueDatas.isEmpty()) {
            result = (valueDatas.size() > 1) ? valueDatas : valueDatas.get(0);
        }
        return result;
    }

    private DataSource getDatasource() {
        return datasource;
    }

    private void close(Connection conn, Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            getLogger().error("An error occured on closing the statement", e);
        }

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            getLogger().error("An error occured on closing the connection", e);
        }
    }
}
