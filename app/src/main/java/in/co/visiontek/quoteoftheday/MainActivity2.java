package in.co.visiontek.quoteoftheday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    RecyclerView recyclerView;
    FavoritesAdapter favoritesAdapter;
    DatabaseHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);

        ArrayList<String> favoriteItems = getFavoriteItemsFromDatabase();

        favoritesAdapter = new FavoritesAdapter(this, favoriteItems);
        recyclerView.setAdapter(favoritesAdapter);
    }

    private ArrayList<String> getFavoriteItemsFromDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<String> favoriteItems = new ArrayList<>();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NAME,
                new String[]{DatabaseHelper.COLUMN_QUOTE},
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String quote = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUOTE));
            favoriteItems.add(quote);
        }

        cursor.close();
        db.close();

        return favoriteItems;
    }
}
