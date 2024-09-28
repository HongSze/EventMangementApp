package com.fit2081.assignment1;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "events")
public class Event {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "eventId")
    private String eventId;

    @ColumnInfo(name = "eventName")
    private String eventName;

    @ColumnInfo(name = "catId")
    private String catId;

    @ColumnInfo(name = "tixAvailable")
    private int tixAvailable;

    @ColumnInfo(name = "isActive")
    private boolean isActive;

    public Event(String eventId, String eventName, String catId, int tixAvailable, boolean isActive) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.catId = catId;
        this.tixAvailable = tixAvailable;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public int getTixAvailable() {
        return tixAvailable;
    }

    public void setTixAvailable(int tixAvailable) {
        this.tixAvailable = tixAvailable;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
