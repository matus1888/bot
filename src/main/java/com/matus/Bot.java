package com.matus;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.Date;

public class Bot extends TelegramLongPollingBot {
    public String chId;
    public String balance = "not info";

    public Bot() {
    }

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        sendMsg(update.getMessage().getChatId().toString(), decide(message));
    }

    private String decide(String message) {
        String otvet = "Farm- get my balance on ETH-wallet"+"\n"+
                        "All- get price at eth, btc and bnb coins"+"\n"+
                        "Horo- get horoscope fo my";
        if ("BTC".equals(message)) {
            return getCryptoPrice("https://api.coinpaprika.com/v1/tickers/btc-bitcoin");
        } else if ("ETH".equals(message)) {
            return getCryptoPrice("https://api.coinpaprika.com/v1/tickers/eth-ethereum");
        } else if ("BNB".equals(message)) {
            return getCryptoPrice("https://api.coinpaprika.com/v1/tickers/bnb-binance-coin");
        } else if ("all".equals(message) || "All".equals(message)) {
            return getCryptoPrice("https://api.coinpaprika.com/v1/tickers/btc-bitcoin") + "\n" +
                    getCryptoPrice("https://api.coinpaprika.com/v1/tickers/eth-ethereum") + "\n" +
                    getCryptoPrice("https://api.coinpaprika.com/v1/tickers/bnb-binance-coin");
        } else if ("farm".equals(message) || "Farm".equals(message)) {
            return balance();
        }else if("Horo".equals(message)||"Horo1".equals(message)){
            return horo(message);
        }
        return otvet;
    }

    private String getCryptoPrice(String s2) {
        String otvet;
        HttpClient n = new HttpClient(s2);
        JSONObject jO = new JSONObject(n.response);
        String s = new JSONObject(jO.get("quotes").toString()).get("USD").toString();
        Double price = new JSONObject(s).getDouble("price");
        //System.out.println(s);
        return otvet = jO.get("name") + "=" + price.toString();
    }

    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        chId = chatId;
        System.out.println(chatId);
        sendMessage.setText(s + "  " + new Date().toString());
        try {
            execute(sendMessage);
            System.out.println(s + "  " + new Date().toString());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "MatusCoinBot";
    }

    @Override
    public String getBotToken() {
        return "1346148453:AAE-mwsnrP9FPEAQpEXbqCoSnm-bcogJjng";
    }

    public static String balance() {
        Document doc1 = null;
        try {
            doc1 = Jsoup.connect("https://www.etherchain.org/account/5D76AD34fEa641A588919fDBdfF5130641CE3DF8").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc1.getElementsByAttributeValue("class", "my-0").get(0).text();
    }
    public static String horo(String s){
        String url;
        if("Horo".equals(s)){
            url="https://horo.mail.ru/prediction/virgo/today/";
        }else{url="https://horo.mail.ru/prediction/virgo/tomorrow/";}
        Document d2= null;
        try {
            d2 = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s1 = d2.getElementsByAttributeValue("class", "article__text").get(0).text();
        return s1;

    }
}

