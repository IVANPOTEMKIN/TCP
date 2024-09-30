package ru.effective_mobile.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

public interface TCPEchoClient {

    String HOST = "localhost";
    int PORT = 8080;
    Logger LOG = Logger.getLogger(TCPEchoClient.class.getName());

    static void run() throws IOException {
        try (Socket client = new Socket(HOST, PORT);
             BufferedReader inputUser = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
             PrintWriter output = new PrintWriter(client.getOutputStream(), true)) {

            LOG.info("Клиент подключен к серверу");
            LOG.info("Введите 'exit' для выхода");

            String line;
            while ((line = inputUser.readLine()) != null) {
                if (line.equalsIgnoreCase("exit")) {
                    LOG.info("Клиент отключен от сервера");
                    break;
                }
                output.println(line);
                System.out.println(input.readLine());
            }
        }
    }
}