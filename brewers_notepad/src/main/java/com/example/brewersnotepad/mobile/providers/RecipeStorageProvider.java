package com.example.brewersnotepad.mobile.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by DragooNArT-PC on 5/15/2016.
 */
public class RecipeStorageProvider extends ContentProvider {


    static final String PROVIDER_NAME = "com.example.brewersnotepad.RecipeData";
    static final String URL = "content://" + PROVIDER_NAME;
    public static final Uri CONTENT_URI = Uri.parse(URL);


    public static final String AUTHORITY = "de.example.brewersnotepad.dragoonart.contentprovider";
    public final static  String FIELD_RECIPE_ID = "recipeId";
    public final static String FIELD_RECIPE_NAME = "recipeName";

    public final static String FIELD_RECIPE_DATA = "recipeData";
    public static final String UPDATE_PATH = "update";
    private static final int UPDATE_NAME = 1;
    private static final int UPDATE_DATA = 2;

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(PROVIDER_NAME, UPDATE_PATH, UPDATE_NAME);
        sURIMatcher.addURI(PROVIDER_NAME, UPDATE_PATH, UPDATE_DATA);
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] columns, String selection, String[] selectionArgs, String s1) {
        return db.query(RECIPES_TABLE_NAME,columns,selection,selectionArgs,null,null,null);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        /**
         * Add a new student record
         */
        long rowID = db.insert(	RECIPES_TABLE_NAME, null, contentValues);

        /**
         * If record is added successfully
         */

        if (rowID > 0)
        {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int count;
        String id = uri.getLastPathSegment(); //gets the id

        count = db.delete( RECIPES_TABLE_NAME, FIELD_RECIPE_ID +  " = '" + selection + "'",null);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;


    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        int rowsUpdated;
        switch (uriType) {
            case UPDATE_NAME:
                rowsUpdated = db.update(RECIPES_TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs);
                break;
//            case UPDATE_DATA:
//                String id = uri.getLastPathSegment();
//                if (TextUtils.isEmpty(selection)) {
//                    rowsUpdated = db.update(RECIPES_TABLE_NAME,
//                            values,
//                            TodoTable.COLUMN_ID + "=" + id,
//                            null);
//                } else {
//                    rowsUpdated = db.update(TodoTable.TABLE_TODO,
//                            values,
//                            TodoTable.COLUMN_ID + "=" + id
//                                    + " and "
//                                    + selection,
//                            selectionArgs);
//                }
//                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;

    }

    /**
     * Database specific constant declarations
     */
    private SQLiteDatabase db;
    static final String DATABASE_NAME = "RecipeDb";
    static final String RECIPES_TABLE_NAME = "RecipesTable";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + RECIPES_TABLE_NAME +
                    " ("+FIELD_RECIPE_ID+" TEXT PRIMARY KEY, " +
                     FIELD_RECIPE_NAME+ " TEXT NOT NULL, " +
                    FIELD_RECIPE_DATA +" TEXT NOT NULL);";

    /**
     * Helper class that actually creates and manages
     * the provider's underlying data repository.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +  RECIPES_TABLE_NAME);
            onCreate(db);
        }

    }

    private DatabaseHelper database;
    @Override
    public boolean onCreate() {
        Context context = getContext();
        database = new DatabaseHelper(context);

        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */
        db = database.getWritableDatabase();
        return (db == null)? false:true;
    }

}

