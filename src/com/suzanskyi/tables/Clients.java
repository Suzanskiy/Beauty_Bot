package com.suzanskyi.tables;

import com.suzanskyi.Client;
import com.suzanskyi.interfaces.TableOperations;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Clients extends BaseTable implements TableOperations {
    public Clients(int ID) {
        super(ID);
    }

    public Clients() {
        super();
    }

    @Override
    public int getID() {
        return super.getID();
    }

    @Override
    public void setID(int ID) {
        super.setID(ID);
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement(
                "CREATE TABLE clients\n" +
                        "(\n" +
                        "    id         int         not null auto_increment,\n" +
                        "    clientName varchar(50) not null,\n" +
                        "    telegramId varchar(30),\n" +
                        "    sch_date   date,\n" +
                        "    sch_time   time,\n" +
                        "    services   varchar(50),\n" +
                        "    botstate   int         not null default 0,\n" +
                        "\n" +
                        "primary key (id)\n" +
                        "\n" +
                        ");", "CREATING Clients table"
        );
    }

    public void insertNewClient(Update upd) throws SQLException {


        String username = upd.getMessage().getFrom().getFirstName() + " " + upd.getMessage().getFrom().getLastName();
        String gram_id = upd.getMessage().getFrom().getUserName();
        super.executeSqlStatement("INSERT clients( clientName, telegramId) VALUE ( '" + username + "', '" + gram_id + "'" + ");",
                "user " + username + " added"); //bot state 0

    }

    public void insertDate(String date, String telegramId) throws SQLException {
        super.executeSqlStatement("UPDATE clients\n" +
                        "    SET sch_date = '" + date + "'" +
                        "Where telegramId = '" + telegramId + "'",
                "Update client schedule date");
    }

    public void insertTime(String time, String telegramId) throws SQLException {
        System.out.print("Log insert time");
        super.executeSqlStatement("UPDATE clients\n" +
                        "SET sch_time = ' " + time + " '\n" +
                        "Where telegramId = '" + telegramId + "'",
                "Update client schedule time");
        ResultSet set = super.executeSqlStatement("Select id, sch_date, sch_time FROM clients where telegramId = '" + telegramId + "'", "delete exesting rows");
        String schDate = null;
        while (set.next()) {

            schDate = set.getString("sch_date");
            time = set.getString("sch_time");
        }
        super.executeSqlStatement("DELETE FROM dutyday WHERE date = '" + schDate + "' and time = '" + time + "' ", "delete");

        super.executeSqlStatement("Insert into dutyDay ( clientId, date, time )\n" +
                "Select id, sch_date, sch_time\n" +
                "       FROM clients", "updating in duty table");
    }

    public List<String> getMySchedule(String username) throws SQLException {
        List<String> temp = new LinkedList<>();
        ResultSet set = super.executeSqlStatement("Select clientName, sch_date, sch_time from clients where  telegramId = '" + username + "'", "User checks his shedule");


        String schDate = null;
        String time = null;

        while (set.next()) {

            schDate = set.getString("sch_date");
            time = set.getString("sch_time");
        }
        temp.add(schDate);
        temp.add(time);

        return temp;
    }

    public void cancelSchedule(Update upd) throws SQLException {
        List<String> list = getMySchedule(upd.getMessage().getFrom().getUserName());
        super.executeSqlStatement("Update dutyDay\n" +
                "Set clientId = null\n" +
                "Where date = ' " + list.get(0) + " ' and time = ' " + list.get(1) + " '", "cancel schedule");
    }

}
