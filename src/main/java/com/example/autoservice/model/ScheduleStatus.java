package com.example.autoservice.model;

public enum ScheduleStatus {
    ACCEPTED("Принято"),
    IN_PROGRESS("В работе"),
    COMPLETED("Завершено");

    private final String displayName;

    ScheduleStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
