package com.kavajin_jelincic.popcornandchill.data;

import android.provider.BaseColumns;

public class FavoriteContract {

    public static final class FavoritesEntry implements BaseColumns{
        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_MOVIEID = "movieId";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_USERRATING = "userrating";
        public static final String COLUMN_POSTER_PATH = "posterpath";
        public static final String COLUMN_PLOT_SYNOPSIS = "overview";
    }
}
