package ru.mirari.infra.json

/**
 * @author Dmitry Kurinskiy
 * @since 19.10.11 18:28
 */
public enum AlertLevel {
    WARNING(false, "warning"),
    ERROR(false, "error"),
    SUCCESS(true, "success"),
    INFO(true, "info");

    private boolean ok
    private String name

    AlertLevel(boolean isOk, String name) {
        ok = isOk
        this.name = name
    }

    boolean isOk() {
        ok
    }

    String toString() {
        name
    }
}