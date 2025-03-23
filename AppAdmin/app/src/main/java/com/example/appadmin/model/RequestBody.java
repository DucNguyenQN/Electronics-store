package com.example.appadmin.model;

public class RequestBody {
    private Message message;

    public RequestBody(String token, String title, String body) {
        this.message = new Message(token, title, body);
    }

    private static class Message {
        private String token;
        private Notification notification;

        public Message(String token, String title, String body) {
            this.token = token;
            this.notification = new Notification(title, body);
        }
    }

    private static class Notification {
        private String title;
        private String body;

        public Notification(String title, String body) {
            this.title = title;
            this.body = body;
        }
    }
}
