package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class EchoBot implements LongPollingSingleThreadUpdateConsumer {

    Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    String botToken = dotenv.get("BOT_TOKEN");
    //ТОКЕН ВМЕСТО ТЕКСТА В КАВЫЧКАХ
    private final TelegramClient telegramClient = new OkHttpTelegramClient(botToken);

    @Override
    public void consume(Update update) {
        // Проверяем: пришло ли сообщение и есть ли в нём текст
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Достаём текст и ID чата, куда надо ответить
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            // Готовим ответное сообщение с тем же текстом
            SendMessage message = SendMessage.builder()
                    .chatId(chatId)
                    .text(messageText)
                    .build();

            try {
                telegramClient.execute(message); // Отправляем обратно в Telegram
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}