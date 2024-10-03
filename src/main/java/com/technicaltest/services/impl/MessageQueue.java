package com.technicaltest.services.impl;

import java.util.LinkedList;
import java.util.Queue;

public class MessageQueue {
    private static Queue<String> messages=new LinkedList<>();

    public static Queue<String> getMessages() {
        return messages;
    }

}
