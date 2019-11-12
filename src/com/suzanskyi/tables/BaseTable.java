package com.suzanskyi.tables;

import com.mysql.cj.protocol.Resultset;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseTable implements Closeable {
    private int ID;
    private Connection connection;  // JDBC-соединение для работы с таблицей
    String tableName;

    public BaseTable(int ID) {
        this.ID = ID;
    }

    public BaseTable() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ResultSet executeSqlStatement(String sql, String description) throws SQLException {
        ResultSet resultSet;
        reopenConnection(); // переоткрываем (если оно неактивно) соединение с СУБД
        Statement statement = connection.createStatement();  // Создаем statement для выполнения sql-команд
        statement.execute(sql); // Выполняем statement - sql команду
        ResultSet tempStatement = statement.getResultSet();
        //  statement.close();      // Закрываем statement для фиксации изменений в СУБД
        if (description != null)
            System.out.println(description);

        //  System.out.println("connection closed");

        return tempStatement;

    }

    private void reopenConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = BeautySalonDatabase.getConnection();
        }
    }
}
