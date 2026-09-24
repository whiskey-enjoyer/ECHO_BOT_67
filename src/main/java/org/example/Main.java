package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {
    public static void main(String[] args) {
        // Создаём "движок", который управляет ботом
        TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();

        try {
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
            String botToken = dotenv.get("BOT_TOKEN");
            // Регистрируем нашего бота. ВСТАВЬ СЮДА ТОТ ЖЕ ТОКЕН, ЧТО И В EchoBot!
            botsApplication.registerBot(botToken, new EchoBot());

            System.out.println("Бот успешно запущен и готов к работе!");

            // Держим программу "живой", чтобы бот продолжал принимать сообщения
            Thread.currentThread().join();
        } catch (TelegramApiException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}