package com.suzanskyi;

import com.suzanskyi.tables.BeautySalonDatabase;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

public class Main {

    public static
    void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        ApiContextInitializer.init();
        try {
            BeautySalonDatabase beautySalonDatabase = new BeautySalonDatabase();
            beautySalonDatabase.createTables();
        } catch (SQLSyntaxErrorException e)
        {
            System.out.println("Tables already created or "+ e.getMessage());
        }
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(new ScheduleBot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();

        }
    }
}
