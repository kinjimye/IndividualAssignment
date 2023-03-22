package my.edu.utar.assignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "scores.db";
    private static final int DATABASE_VERSION = 4;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create score table
        db.execSQL("CREATE TABLE scores (_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, score INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Upgrade from version 1 to version 2
            db.execSQL("ALTER TABLE scores ADD COLUMN date TEXT");
        }

        if (oldVersion < 3) {
            // Upgrade from version 2 to version 3
            db.execSQL("ALTER TABLE scores ADD COLUMN level INTEGER");
        }

        if (oldVersion < 4) {
            // Upgrade from version 3 to version 4
            db.execSQL("DROP TABLE IF EXISTS scores");
            onCreate(db);
        }
    }

}
