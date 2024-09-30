package ru.effective_mobile.client.impl;

import ru.effective_mobile.client.TCPEchoClient;

import java.io.IOException;

public class TCPEchoClient1 implements TCPEchoClient {

    public static void main(String[] args) throws IOException {
        TCPEchoClient.run();
    }
}