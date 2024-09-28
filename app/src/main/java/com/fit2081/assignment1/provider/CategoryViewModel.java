package com.fit2081.assignment1.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fit2081.assignment1.Event;
import com.fit2081.assignment1.EventCategory;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    // reference to CardRepository
    private CategoryRepository repository;
    // private class variable to temporary hold all the items retrieved and pass outside of this class
    private LiveData<List<EventCategory>> allCategoriesLiveData;

    public CategoryViewModel(@NonNull Application application) {
        super(application);

        // get reference to the repository class
        repository = new CategoryRepository(application);

        // get all items by calling method defined in repository class
        allCategoriesLiveData = repository.getAllCategories();
    }

    /**
     * ViewModel method to get all cards
     * @return LiveData of type List<Item>
     */
    public LiveData<List<EventCategory>> getAllCategories() {
        return allCategoriesLiveData;
    }

    public LiveData<EventCategory> getCategoryById(String categoryId) {
        return repository.getCategoryById(categoryId);
    }
    /**
     * ViewModel method to insert one single item,
     * usually calling insert method defined in repository class
     * @param category object containing details of new Item to be inserted
     */
    public void insert(EventCategory category) {
        repository.insert(category);
    }
    public void deleteAll() {
        repository.deleteAll();
    }

    public void update(EventCategory category) {
        repository.update(category);
    }
}