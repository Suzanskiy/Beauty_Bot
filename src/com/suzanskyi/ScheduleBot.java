package com.suzanskyi;

import com.google.inject.internal.cglib.proxy.$Callback;
import com.suzanskyi.messages.MSG;
import com.suzanskyi.tables.Clients;
import com.suzanskyi.tables.DutyDay;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

public class ScheduleBot extends TelegramLongPollingBot {

    Clients clients;
    DutyDay dutyDay;
    HashSet<Client> hashClients;

    public ScheduleBot() throws SQLException {
        clients = new Clients();
        dutyDay = new DutyDay();
        hashClients = new HashSet<>();
/*        SimpleDateFormat format = new SimpleDateFormat("B");
         System.out.println(LocalDate.now().format(DateTimeFormatter.ofPattern(String.valueOf(format))));*/

        // System.out.println(todayDate);
    }


    @Override
    public String getBotToken() {
        return "925357163:AAFMXqJfwDZ2RY1Tx3rt9FfhpBMBTqoEtAo";
    }


    @Override
    public String getBotUsername() {
        return "beautybot";
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {


        for (Update upd : updates) {

            if (upd.hasMessage()) {
                long chatId = upd.getMessage().getChatId();

                if (upd.getMessage().getText().equals("/start")) {

                    //todo FirstSTART interacting with bot
                    try {
                        execute(MSG.HelloMessage(chatId));
                        clients.insertNewClient(upd); //bot state
                    } catch (TelegramApiException | SQLException e) {
                        e.printStackTrace();
                    }
                    //todo check if user already registered
                    //todo client registration


                } else if (upd.getMessage().getText().equals("Записаться✍️")) {


                    int daysInMonth = Calendar.getInstance().getActualMaximum(Calendar.DATE);
                    int todayDate = Calendar.getInstance().get(Calendar.DATE);

                    InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                    List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                    List<InlineKeyboardButton> rowInline = new ArrayList<>();

                    int count = 0;
                    LocalDate date = LocalDate.now();
                    String Month = date.format(DateTimeFormatter.ofPattern(".MM"));
                    for (int i = todayDate; i <= daysInMonth; i++) {


                        rowInline.add(new InlineKeyboardButton().setText(i + Month).setCallbackData(date.plusDays(count).
                                format(DateTimeFormatter.ofPattern("YYYY-MM-dd"))));

                        count++;
                        if (count % 4 == 0) {
                            rowsInline.add(rowInline);
                            rowInline = new ArrayList<>();
                        }

                    }
                    rowsInline.add(rowInline);
                    markupInline.setKeyboard(rowsInline);
                    SendMessage message = new SendMessage()
                            .setText("Выбери свободную дату:").setChatId(chatId)
                            .setParseMode("Markdown")
                            .setReplyMarkup(markupInline);
                    try {
                        execute(message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                    ////////////////////////////////


                } else if (upd.getMessage().getText().equals("Моя запись")) {
                    String usr = upd.getMessage().getFrom().getUserName();
                    ResultSet set = null;
                    String username, schDate = null, time = null;
                    List<String> temp = new ArrayList<>();

                    try {
                        temp = clients.getMySchedule(usr);
                        System.out.print(Arrays.toString(temp.toArray()));

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    try {
                        clients.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        execute(MSG.SimpleMessage(chatId, temp.get(0), temp.get(1).substring(0, 5), upd.getMessage().getFrom().getFirstName()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else if (upd.getMessage().getText().equals("Отменить запись\uD83D\uDE14️")) {
                    // TODO прощальный текст
                    try {
                        clients.cancelSchedule(upd);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else if (upd.hasCallbackQuery()) {

                String data = upd.getCallbackQuery().getData();
                String tgId = upd.getCallbackQuery().getFrom().getUserName();
                Long chatid = Long.valueOf(upd.getCallbackQuery().getFrom().getId());

                System.out.print(data + tgId + chatid);
                if (data.length() == 10) // принял дату //todo исправить костыль
                {
                    try {
                        clients.insertDate(data, tgId);
                        clients.close();
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }

                    //////////////////////////////////
                    System.out.println("Time Selector");
                    List<String> temp = new ArrayList<>();

                    InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                    List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                    List<InlineKeyboardButton> rowInline = new ArrayList<>();
                    try {
                        temp = dutyDay.getEmptyTime(data);
                        dutyDay.close();
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }
//todo сюда ебани
                    int count = 0;
                    for (String button : temp
                    ) {
                        rowInline.add(new InlineKeyboardButton().setText(button).setCallbackData(String.valueOf(button)));

                    }
                    rowsInline.add(rowInline);
                    markupInline.setKeyboard(rowsInline);

                    SendMessage message = new SendMessage()
                            .setText("Вот свободное время ").setChatId(chatid)
                            .setParseMode("Markdown")
                            .setReplyMarkup(markupInline);
                    try {
                        execute(message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    /////////////////////////////////
                } else if (data.length() == 5) { // принял время
                    try {
                        clients.insertTime(data, tgId);
                        clients.close();
                        execute(MSG.Succes(chatid));

                    } catch (SQLIntegrityConstraintViolationException e) {
                        try {
                            execute(MSG.textMessage(chatid));
                        } catch (TelegramApiException ex) {
                            ex.printStackTrace();
                        }

                    } catch (SQLException | TelegramApiException | IOException e) {
                        e.printStackTrace();
                    }


                }


            }
        }
    }

}
