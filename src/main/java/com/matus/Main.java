package com.matus;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.Timer;

public class Main {

    public static void main(String[] args) {
        Timer time = new Timer();
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            Bot b=new Bot();
            telegramBotsApi.registerBot(b);
            time.schedule(b.sendMsg("527920507", "пробная отправка сообщения"), 0, 1000);

        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}
