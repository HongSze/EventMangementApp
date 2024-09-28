package com.fit2081.assignment1.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.fit2081.assignment1.Event;

import java.util.List;
import androidx.lifecycle.Observer;
public class EventRepository {

    // private class variable to hold reference to DAO
    private AppDAO appDAO;
    // private class variable to temporary hold all the items retrieved and pass outside of this class
    private LiveData<List<Event>> allEventsLiveData;

    // constructor to initialise the repository class
    EventRepository(Application application) {
        // get reference/instance of the database
        AppDatabase db = AppDatabase.getDatabase(application);

        // get reference to DAO, to perform CRUD operations
        appDAO = db.appDAO();

        // once the class is initialised get all the items in the form of LiveData
        allEventsLiveData = appDAO.getAllEvents();
    }

    /**
     * Repository method to get all cards
     * @return LiveData of type List<Item>
     */
    LiveData<List<Event>> getAllEvents() {
        return allEventsLiveData;
    }
    LiveData<Event> getLastEvent() {
        return appDAO.getLastEvent();
    }
    /**
     * Repository method to insert one single item
     * @param event object containing details of new Item to be inserted
     */
    void insert(Event event) {
        AppDatabase.databaseWriteExecutor.execute(() -> appDAO.addEvent(event));
    }

    void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(() -> appDAO.deleteAllEvents());
    }

    public void deleteEventById(String eventId) {
        AppDatabase.databaseWriteExecutor.execute(() -> appDAO.deleteEventById(eventId));
    }

//    void deleteLastEvent() {
//        LiveData<Event> lastEventLiveData = appDAO.getLastEvent();
//        lastEventLiveData.observeForever(new Observer<Event>() {
//            @Override
//            public void onChanged(Event event) {
//                if (event != null) {
//                    deleteEventById(event.getEventId());
//                    lastEventLiveData.removeObserver(this);
//                }
//            }
//        });
//    }
    void undo() {
        AppDatabase.databaseWriteExecutor.execute(() -> appDAO.undo()); // Use the new query
    }
    void deleteEvent(Event event) {
        AppDatabase.databaseWriteExecutor.execute(() -> appDAO.deleteEvent(event));
    }
}