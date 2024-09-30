package ru.effective_mobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class TCPEchoServer {

    private static final int PORT = 8080;
    private static final Logger LOG = Logger.getLogger(TCPEchoServer.class.getName());

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(PORT);
            ExecutorService service = Executors.newFixedThreadPool(10);
            LOG.info("Сервер работает на порту: " + PORT);
            if (!server.isClosed()) {
                for (int i = 0; i < 10; i++) {
                    service.submit(new Thread(() -> {
                        try {
                            while (true) {
                                Socket client = server.accept();
                                String host = client.getInetAddress().getHostAddress();
                                long id = Thread.currentThread().getId();
                                LOG.info(id + " Клиент подключен: " + host);

                                BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                                PrintWriter output = new PrintWriter(client.getOutputStream(), true);

                                String line;
                                while ((line = input.readLine()) != null) {
                                    System.out.println(id + " Клиент: " + line);
                                    output.println("Сервер: " + line.toUpperCase());
                                }

                                output.flush();
                                output.close();
                                input.close();
                                client.close();
                                LOG.info(id + " Клиент отключен");
                            }
                        } catch (IOException e) {
                            LOG.warning(e.getMessage());
                        }
                    }));
                }
            }
        } catch (IOException e) {
            LOG.warning(e.getMessage());
        }
    }
}