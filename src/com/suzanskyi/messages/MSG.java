package com.suzanskyi.messages;

import com.suzanskyi.Emoji;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public final class MSG {


    public static SendMessage HelloMessage(Long chatId) {
        String text = "Привет!) С помощью этого бота ты можешь быстро записаться на маникюр " + Emoji.Nails;

        SendMessage msg = new SendMessage();
        msg.setText(text);
        msg.setChatId(chatId);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Create a keyboard row
        KeyboardRow row = new KeyboardRow();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        row.add("Записаться"+ Emoji.HandWrite);
        // Add the first row to the keyboard
        keyboard.add(row);
        row = new KeyboardRow(); // Create another keyboard row
        row.add("Отменить запись"+Emoji.Sad);

        row.add("Связаться"+Emoji.Letter);
        // Set each button for the second line
        row.add("О нас"+Emoji.Nails);

        // Add the second row to the keyboard
        keyboard.add(row);
        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);

        msg.setReplyMarkup(keyboardMarkup);

        return msg;
    }
    public static SendMessage Succes (Long Chatid)
    {
        String text = "Поздравляю! Ты успешно записалась " + Emoji.Nails;
        SendMessage msg = new SendMessage();
        msg.setText(text);
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Create a keyboard row
        KeyboardRow row = new KeyboardRow();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        row.add("Моя запись");
        // Add the first row to the keyboard
        keyboard.add(row);
        row = new KeyboardRow(); // Create another keyboard row

        row.add("Связаться"+Emoji.Letter);
        // Set each button for the second line
        row.add("О нас"+Emoji.Nails);

        // Add the second row to the keyboard
        keyboard.add(row);
        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);
msg .setChatId(Chatid);
        msg.setReplyMarkup(keyboardMarkup);
        return msg;
    }
    public static SendMessage SimpleMessage( Long chatId, String date, String time, String name)
    {


        SendMessage msg = new SendMessage();
        msg.setText(name + ", я жду тебя на маникюр " + date + " в " + time);
        msg.setChatId(chatId);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("Записаться"+ Emoji.HandWrite);
        keyboard.add(row);
        row = new KeyboardRow(); // Create another keyboard row
        row.add("Отменить запись"+Emoji.Sad);

        row.add("Связаться"+Emoji.Letter);
        row.add("О нас"+Emoji.Nails);

        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);

        msg.setReplyMarkup(keyboardMarkup);

        return msg;
    }

    public static SendMessage textMessage(Long chatid) {
        SendMessage msg = new SendMessage();
        String text = "Ты уже записана, нажми  Моя запись для проверки бронирования";
        msg.setText(text);
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Create a keyboard row
        KeyboardRow row = new KeyboardRow();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        row.add("Моя запись");
        // Add the first row to the keyboard
        keyboard.add(row);
        row = new KeyboardRow(); // Create another keyboard row

        // Set each button for the second line


        // Add the second row to the keyboard
        keyboard.add(row);
        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);
        msg .setChatId(chatid);
        msg.setReplyMarkup(keyboardMarkup);
        return msg;

    }
}

