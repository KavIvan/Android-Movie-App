package com.kavajin_jelincic.popcornandchill.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import android.util.Log;

import com.kavajin_jelincic.popcornandchill.database.AppDatabase;
import com.kavajin_jelincic.popcornandchill.database.FavoriteEntry;

import java.util.List;



public class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<FavoriteEntry>> favorite;

    public MainViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        favorite = database.favoriteDao().loadAllFavorite();
    }

    public LiveData<List<FavoriteEntry>> getFavorite() {

        return favorite;
    }
}
