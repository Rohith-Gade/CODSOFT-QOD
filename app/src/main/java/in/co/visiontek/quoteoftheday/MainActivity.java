package in.co.visiontek.quoteoftheday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button shareButton,favoriteButton;
    List<String> favoriteItems = new ArrayList<>(); // Move this declaration to the class level
    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] dailyTexts = {
                //here i used static for the  quotes to display the user .
                "Be yourself; everyone else is already taken.",
                "Be the change that you wish to see in the world.",
                "Live as if you were to die tomorrow. Learn as if you were to live forever.",
                "We accept the love we think we deserve.",
                "Without music, life would be a mistake.",
                "We are all in the gutter, but some of us are looking at the stars.",
                "I have not failed. I've just found 10,000 ways that won't work.",
                "It is never too late to be what you might have been.",
                "Everything you can imagine is real.",
                "You can never get a cup of tea large enough or a book long enough to suit me.",
                "Life isn't about finding yourself. Life is about creating yourself.",
                "To the well-organized mind, death is but the next great adventure.",
                "Do what you can, with what you have, where you are.",
                "Success is not final, failure is not fatal: it is the courage to continue that counts.",
                "It’s no use going back to yesterday, because I was a different person then.",
                "It's the possibility of having a dream come true that makes life interesting.",
                "A person's a person, no matter how small.",
                "Well-behaved women seldom make history.",
                "Nothing is impossible, the word itself says 'I'm possible !",
                "Do what you feel in your heart to be right – for you’ll be criticized anyway.",
                "Happiness is not something ready made. It comes from your own actions.",
                "Peace begins with a smile..",
                "May you live every day of your life.",
                "Whatever you are, be a good one.",
                "Two wrongs don't make a right, but they make a good excuse.",
                "If my life is going to mean anything, I have to live it myself.",
                "Isn't it nice to think that tomorrow is a new day with no mistakes in it yet?”",
                "Pain is inevitable. Suffering is optional.",
                "The mind is its own place, and in itself can make a heaven of hell, a hell of heaven.",
                "Always do what you are afraid to do.",
                "Hell is empty and all the devils are here.",
//end of the quote .
        };
        dbHelper = new DatabaseHelper(this);
        textView = findViewById(R.id.textView);
        shareButton = findViewById(R.id.shareButton);
        favoriteButton = findViewById(R.id.favoriteButton);
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        if (currentDay >= 1 && currentDay <= dailyTexts.length) {
            textView.setText(dailyTexts[currentDay - 1]);
        } else {
            textView.setText("Text for other days");
        }

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareText = textView.getText().toString();
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                startActivity(Intent.createChooser(shareIntent, "Share using"));
            }
        });



        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = textView.getText().toString();

                if (!isQuoteAlreadyFavorite(text)) {
                    addFavoriteQuoteToDatabase(text);
                    Toast.makeText(MainActivity.this, "Added to Favorites", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Already in Favorites", Toast.LENGTH_SHORT).show();
                }

                // Retrieve favorite items from the database
                Set<String> favoriteQuotes = getFavoriteItems();
                ArrayList<String> favoriteQuotesList = new ArrayList<>(favoriteQuotes);

                // Pass the ArrayList to MainActivity2
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putStringArrayListExtra("favoriteItems", favoriteQuotesList);
                startActivity(intent);
            }
        });




    }
    private boolean isQuoteAlreadyFavorite(String quote) {
        Set<String> favoriteQuotes = getFavoriteItems();
        return favoriteQuotes.contains(quote);
    }

    private void addFavoriteQuoteToDatabase(String quote) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_QUOTE, quote);

        db.insert(DatabaseHelper.TABLE_NAME, null, values);
        db.close();
    }
    private void saveFavoriteItems(Set<String> favoriteItems) {
        // Save the favorite items in SharedPreferences
        getSharedPreferences("MyFavorites", MODE_PRIVATE)
                .edit()
                .putStringSet("favoriteItems", favoriteItems)
                .apply();
    }

    private Set<String> getFavoriteItems() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Set<String> favoriteItems = new HashSet<>();

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

