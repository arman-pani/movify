package helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import models.MovieCardModel;

public class BookmarkDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bookmark_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Bookmarks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";

    private static final String COLUMN_POSTER_URL = "poster_url";
    private static final String COLUMN_RELEASE_DATE = "release_date";

    public BookmarkDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_POSTER_URL + " TEXT, " +
                COLUMN_RELEASE_DATE + " TEXT)" ;

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void removeBookmark(int movieId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(movieId)});
        db.close();
    }

    public void insertBookmark(MovieCardModel movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, movie.getId());
        values.put(COLUMN_TITLE, movie.getTitle());
        values.put(COLUMN_POSTER_URL, movie.getPosterUrl());
        values.put(COLUMN_RELEASE_DATE, movie.getReleaseDate());
        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public List<MovieCardModel> getAllBookmarks(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        List<MovieCardModel> bookmarks = new ArrayList<>();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
                String posterUrl = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POSTER_URL));
                String releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RELEASE_DATE));
                bookmarks.add(new MovieCardModel(id, posterUrl, title, releaseDate));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookmarks;
    }

    public boolean isBookmarked(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT 1 FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        boolean exists = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return exists;
    }


}
