package org.numamo.category.file.info.service.controller.dto;

import java.time.Instant;


public final class FileStorageUpdaterDto {

    private FileStorageUpdaterStateDto state;
    private Instant date;

    public FileStorageUpdaterStateDto getState() {
        return state;
    }

    public void setState(FileStorageUpdaterStateDto state) {
        this.state = state;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "FileStorageUpdaterDto{" +
                "state=" + state +
                ", date=" + date +
                '}';
    }
}
