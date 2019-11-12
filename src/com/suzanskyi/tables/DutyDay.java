package com.suzanskyi.tables;

import com.suzanskyi.interfaces.TableOperations;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DutyDay extends BaseTable implements TableOperations {

    public DutyDay() throws SQLException {

    }

    @Override
    public void close() throws IOException {
        super.close();
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement("CREATE TABLE dutyDay (\n" +
                "    id  int not null auto_increment,\n" +
                "    date date ,\n" +
                "    time time,\n" +
                "    clientId int unique ,\n" +
                "    primary key (id),\n" +
                "    foreign key (clientId) REFERENCES clients (id)\n" +
                "\n" +
                ")", "table duty day created");
    }
    public void createSkeleton(String date) throws SQLException {
        for (int i= 9; i<=18; i=i+2 )
        super.executeSqlStatement("insert into dutyDay (date, time) values ('"+date+"', '"+i+":00:00')", "inject time + "+ i  );
        System.out.println("Done");
    }
    public List<String> getEmptyTime () throws SQLException {
        List<String > res = new ArrayList<>();
        ResultSet set = super.executeSqlStatement("Select time FROM dutyday WHERE clientid <=0 OR clientId is NULL ", "getEmptyTime()");
        while(set.next())
        {
            res.add(set.getString("time").substring(0, 5));
        }
        return res;
    }
 }
