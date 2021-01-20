package com.kavajin_jelincic.popcornandchill.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kavajin_jelincic.popcornandchill.database.AppDatabase;
import com.kavajin_jelincic.popcornandchill.database.FavoriteEntry;

public class AddFavoriteViewModel extends ViewModel {

    private LiveData<FavoriteEntry> favorite;

    public AddFavoriteViewModel(AppDatabase database, int favoriteId) {
        favorite = database.favoriteDao().loadFavoriteById(favoriteId);
    }

    public LiveData<FavoriteEntry> getFavorite() {
        return favorite;
    }
}
