package com.fit2081.assignment1.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.fit2081.assignment1.Event;
import com.fit2081.assignment1.EventCategory;

import java.util.List;

public class CategoryRepository {

    // private class variable to hold reference to DAO
    private AppDAO appDAO;
    // private class variable to temporary hold all the items retrieved and pass outside of this class
    private LiveData<List<EventCategory>> allCategoriesLiveData;

    // constructor to initialise the repository class
    CategoryRepository(Application application) {
        // get reference/instance of the database
        AppDatabase db = AppDatabase.getDatabase(application);

        // get reference to DAO, to perform CRUD operations
        appDAO = db.appDAO();

        // once the class is initialised get all the items in the form of LiveData
        allCategoriesLiveData = appDAO.getAllCategories();
    }

    /**
     * Repository method to get all cards
     * @return LiveData of type List<Item>
     */
    LiveData<List<EventCategory>> getAllCategories() {
        return allCategoriesLiveData;
    }

    LiveData<EventCategory> getCategoryById(String categoryId) {
        return appDAO.getCategoryById(categoryId);
    }
    /**
     * Repository method to insert one single item
     * @param category object containing details of new Item to be inserted
     */
    void insert(EventCategory category) {
        AppDatabase.databaseWriteExecutor.execute(() -> appDAO.addCategory(category));
    }

    void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(() -> appDAO.deleteAllCategories());
    }

    void update(EventCategory category) {
        AppDatabase.databaseWriteExecutor.execute(() -> appDAO.update(category));
    }
}