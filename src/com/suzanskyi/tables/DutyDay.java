package com.suzanskyi.tables;

import com.suzanskyi.interfaces.TableOperations;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
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

    public void createSkeleton() throws SQLException {
        LocalDate localDate = LocalDate.now();
        String value = localDate.format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));
        int daysInMonth = Calendar.getInstance().getActualMaximum(Calendar.DATE);
        int todayDate = Calendar.getInstance().get(Calendar.DATE);
        int count = 0;
        for (int i = todayDate; i <= daysInMonth; i++) {

            for (int j = 9; j <= 18; j = j + 2) {
                super.executeSqlStatement("insert into dutyDay (date, time) values ('" + localDate.plusDays(count).
                        format(DateTimeFormatter.ofPattern("YYYY-MM-dd")) + "', '" + j + ":00:00')", "inject time + " + j);
            }
        count++;

        }
        System.out.println("Done");
    }

    public List<String> getEmptyTime(String date) throws SQLException {
        List<String> res = new ArrayList<>();
        ResultSet set = super.executeSqlStatement("Select time FROM dutyday WHERE clientId is NULL AND date = '" + date + "' ", "getEmptyTime()");
        while (set.next()) {
            res.add(set.getString("time").substring(0, 5));
        }
        return res;
    }
}
