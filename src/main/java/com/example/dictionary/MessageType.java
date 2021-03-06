package com.example.dictionary;

public enum MessageType {
    SUCCESS(0),
    ERROR(1);

    final private static String[] styleClass = new String[] { "success", "error" };

    final private int value;

    /**
     * enum -> int magic. c# has this built in...
     */
    MessageType(int value) {
        this.value = value;
    }

    public String getStyleClass() {
        return styleClass[value];
    }
}