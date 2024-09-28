package com.fit2081.assignment1.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fit2081.assignment1.Event;
import com.fit2081.assignment1.EventCategory;

import java.util.List;

@Dao
public interface AppDAO {
    // EventCategory DAO methods
    @Query("SELECT * FROM event_categories")
    LiveData<List<EventCategory>> getAllCategories();

    @Insert
    void addCategory(EventCategory category);

    @Query("SELECT * FROM event_categories WHERE categoryID = :categoryId")
    LiveData<EventCategory> getCategoryById(String categoryId);

    @Query("DELETE FROM event_categories")
    void deleteAllCategories();

    @Update
    void update(EventCategory category);

    // Event DAO methods
    @Query("SELECT * FROM events")
    LiveData<List<Event>> getAllEvents();

    @Insert
    void addEvent(Event event);

    @Query("DELETE FROM events")
    void deleteAllEvents();

    @Query("DELETE FROM events WHERE eventId = :eventId")
    void deleteEventById(String eventId);

    @Query("SELECT * FROM events ORDER BY eventId DESC LIMIT 1")
    LiveData<Event> getLastEvent(); // Added method to get the last event
    @Query("DELETE FROM events WHERE id = (SELECT MAX(id) FROM events)")
    void undo(); // Add this new query
    @Delete
    void deleteEvent(Event event);
}