package com.example.mainproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

public class CreatePost extends AppCompatActivity {

    Button postButton, regionButton2, positionButton2;
    EditText createPost;
    DBManager2 dbManager;
    String region = "All";
    String position = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        createPost = (EditText) findViewById(R.id.createPost);

        postButton = (Button) findViewById(R.id.postButton);
        regionButton2 = (Button) findViewById(R.id.regionButton2);
        positionButton2 = (Button) findViewById(R.id.positionButton2);

        dbManager = new DBManager2(CreatePost.this);
        dbManager.open();

        // This is to post the contents in the Discussion Room.
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CreatePost.this, "Post successful", Toast.LENGTH_SHORT).show();
                String username = getIntent().getStringExtra("username");
                String post = createPost.getText().toString();
                Log.i("HERE", username);
                dbManager.insert(username, region, position, post);

                Intent myIntent = new Intent(CreatePost.this, DiscussionRoom.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                passIntent(myIntent, username);
                startActivity(myIntent);
            }
        });

        // Select Region.
        regionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegionPopupMenu(view);
            }
        });

        // Select Position.
        positionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPositionPopupMenu(view);
            }
        });
    }

    // Pop-up menu for Region.
    void showRegionPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(CreatePost.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.region_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Handle menu item clicks
                int itemId = menuItem.getItemId();

                if (itemId == R.id.regionAll) {
                    // Handle option 1
                    regionButton2.setText("All");
                    region = "All";
                } else if (itemId == R.id.regionAmerica) {
                    // Handle option 2
                    regionButton2.setText("America");
                    region = "America";
                } else if (itemId == R.id.regionEurope) {
                    // Handle option 3
                    regionButton2.setText("Europe");
                    region = "Europe";
                } else if (itemId == R.id.regionAfrica) {
                    // Handle option 4
                    regionButton2.setText("Africa");
                    region = "Africa";
                } else if (itemId == R.id.regionAsia) {
                    // Handle option 5
                    regionButton2.setText("Asia");
                    region = "Asia";
                }
                return true;
            }
        });

        popupMenu.show();
    }

    // Pop-up Menu for position.
    void showPositionPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(CreatePost.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.position_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Handle menu item clicks
                int itemId = menuItem.getItemId();

                if (itemId == R.id.positionAll) {
                    // Handle option 1
                    positionButton2.setText("All");
                    position = "All";
                } else if (itemId == R.id.positionAttacker) {
                    // Handle option 2
                    positionButton2.setText("Attacker");
                    position = "Attacker";
                } else if (itemId == R.id.positionDefender) {
                    // Handle option 3
                    positionButton2.setText("Defender");
                    position = "Defender";
                } else if (itemId == R.id.positionSupport) {
                    // Handle option 4
                    positionButton2.setText("Support");
                    position = "Support";
                } else if (itemId == R.id.positionGoalkeeper) {
                    // Handle option 5
                    positionButton2.setText("Goalkeeper");
                    position = "Goalkeeper";
                }
                return true;
            }
        });

        popupMenu.show();
    }

    // Pass additional intent.
    public Intent passIntent(Intent myIntent, String user) {
        myIntent.putExtra("username", user);
        return myIntent;
    }

}