package com.suzanskyi.tables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.Class.forName;

public class BeautySalonDatabase {
    Clients clients;
    DutyDay dayTable;


    public static final String DB_URL = "jdbc:mysql://localhost/salon?serverTimezone=UTC&autoReconnect=true";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, "root", "");
    }

    public BeautySalonDatabase() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        //  Class.forName("com.mysql.cj.jdbc.Driver");
        clients = new Clients();
        dayTable = new DutyDay();
    }

    public void createTables() throws SQLException {
        clients.createTable();
        dayTable.createTable(); dayTable.createSkeleton("2019-11-11");
    }
}
