package com.example.mainproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.PopupMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiscussionRoom extends AppCompatActivity {

    Button createPostButton, regionButton, positionButton, sortButton, searchButton;
    ImageButton menuButton;
    private DBManager2 dbManager;

    private ListView listView;

    private SimpleCursorAdapter adapter;
    TextView usernameTextView, postTextView, regionTextView, positionTextView;
    String selectedRegion = "All";
    String selectedPosition = "All";
    EditText searchText;

    final String[] from = new String[] {
            DatabaseHelper2.USERNAME,
            DatabaseHelper2.REGION,
            DatabaseHelper2.POSITION,
            DatabaseHelper2.POST
    };

    final int[] to = new int[] {
            R.id.usernameText,
            R.id.regionText,
            R.id.positionText,
            R.id.postText
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_room);

        createPostButton = (Button) findViewById(R.id.createPostButton);
        regionButton = (Button) findViewById(R.id.regionButton);
        positionButton = (Button) findViewById(R.id.positionButton);

        sortButton = (Button) findViewById(R.id.sortButton);

        menuButton = (ImageButton) findViewById(R.id.menuButton);

        searchText = (EditText) findViewById(R.id.searchText);
        searchButton = (Button) findViewById(R.id.searchButton);

        dbManager = new DBManager2(DiscussionRoom.this);
        dbManager.open();

        Cursor cursor = dbManager.fetch();

        listView = (ListView) findViewById(R.id.list_view);

        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(DiscussionRoom.this, R.layout.activity_view_record, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        String user = getIntent().getStringExtra("username");

        // Contents of the Posts.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                usernameTextView = (TextView) findViewById(R.id.userName);
                postTextView = (TextView) findViewById(R.id.postText);
                regionTextView = (TextView) findViewById(R.id.regionText);
                positionTextView = (TextView) findViewById(R.id.positionText);

                String usernameText = usernameTextView.getText().toString();
                String postText = postTextView.getText().toString();
                String regionText = regionTextView.getText().toString();
                String positionText = positionTextView.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(), DiscussionRoom.class);
                modify_intent.putExtra("usernameText", usernameText);
                modify_intent.putExtra("regionText", regionText);
                modify_intent.putExtra("positionText", positionText);
                modify_intent.putExtra("postText", postText);
                startActivity(modify_intent);
            }
        });

        // Remove the OnItemClickListener
        listView.setOnItemClickListener(null);

        // Create Post.
        createPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(DiscussionRoom.this, CreatePost.class);
                passIntent(myIntent, user);
                startActivity(myIntent);
            }
        });

        // Logout.
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(DiscussionRoom.this, Login.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent);
            }
        });

        // Select Region.
        regionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegionPopupMenu(view);
            }
        });

        // Select Position.
        positionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPositionPopupMenu(view);
            }
        });

        // Sort.
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSortPopupMenu(view);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameToSearch = searchText.getText().toString();
                searchListView(usernameToSearch);
            }
        });
    }

    private void updateListView(String selectedRegion, String selectedPosition) {
        Cursor cursor;

        if ("All".equals(selectedRegion) && "All".equals(selectedPosition)) {
            cursor = dbManager.fetch(); // Fetch all data
        } else if (!"All".equals(selectedRegion)){
            cursor = dbManager.fetchByRegion(selectedRegion); // Fetch data based on selected region
        } else {
            cursor = dbManager.fetchByPosition(selectedPosition); // Fetch data based on selected position
        }

        adapter.changeCursor(cursor); // Update the cursor in the adapter
    }

    // Show Popup Menu for Region.
    void showRegionPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(DiscussionRoom.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.region_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Handle menu item clicks
                int itemId = menuItem.getItemId();

                if (itemId == R.id.regionAll) {
                    // Handle option 1
                    regionButton.setText("All");
                    selectedRegion = "All";
                } else if (itemId == R.id.regionAmerica) {
                    // Handle option 2
                    regionButton.setText("America");
                    selectedRegion = "America";
                } else if (itemId == R.id.regionEurope) {
                    // Handle option 3
                    regionButton.setText("Europe");
                    selectedRegion = "Europe";
                } else if (itemId == R.id.regionAfrica) {
                    // Handle option 4
                    regionButton.setText("Africa");
                    selectedRegion = "Africa";
                } else if (itemId == R.id.regionAsia) {
                    // Handle option 5
                    regionButton.setText("Asia");
                    selectedRegion = "Asia";
                }

                positionButton.setText("Position");
                sortButton.setText("Sort");
                selectedPosition = "All";
                updateListView(selectedRegion, selectedPosition);
                return true;
            }
        });

        popupMenu.show();
    }

    // Show Popup Menu for Position.
    void showPositionPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(DiscussionRoom.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.position_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Handle menu item clicks
                int itemId = menuItem.getItemId();

                if (itemId == R.id.positionAll) {
                    // Handle option 1
                    positionButton.setText("All");
                    selectedPosition = "All";
                } else if (itemId == R.id.positionAttacker) {
                    // Handle option 1
                    positionButton.setText("Attacker");
                    selectedPosition = "Attacker";
                } else if (itemId == R.id.positionDefender) {
                    // Handle option 2
                    positionButton.setText("Defender");
                    selectedPosition = "Defender";
                } else if (itemId == R.id.positionSupport) {
                    // Handle option 3
                    positionButton.setText("Support");
                    selectedPosition = "Support";
                } else if (itemId == R.id.positionGoalkeeper) {
                    // Handle option 4
                    positionButton.setText("Goalkeeper");
                    selectedPosition = "Goalkeeper";
                }

                regionButton.setText("Region");
                sortButton.setText("Sort");
                selectedRegion = "All";
                updateListView(selectedRegion, selectedPosition);
                return true;
            }
        });

        popupMenu.show();
    }

    void sortListView(boolean ascending) {
        Cursor cursor;

        if (ascending) {
            // Sort all data by username
            cursor = dbManager.fetchSortedByUsername("Ascending");
        } else {
            cursor = dbManager.fetchSortedByUsername("Descending");
        }

        adapter.changeCursor(cursor); // Update the cursor in the adapter
    }

    // Show Popup Menu for Sort.
    void showSortPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(DiscussionRoom.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.sort_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Handle menu item clicks
                int itemId = menuItem.getItemId();

                if (itemId == R.id.sortAscend) {
                    // Handle option 1
                    sortButton.setText("Ascend");
                    sortListView(true);
                } else if (itemId == R.id.sortDescend) {
                    // Handle option 2
                    sortButton.setText("Descend");
                    sortListView(false);
                }

                regionButton.setText("Region");
                selectedRegion = "All";
                positionButton.setText("Position");
                selectedPosition = "All";
                return true;
            }
        });

        popupMenu.show();
    }

    private void searchListView(String usernameToSearch) {
        Cursor cursor;

        if (usernameToSearch.isEmpty()) {
            // If the search text is empty, show all data
            updateListView(selectedRegion, selectedPosition);
        } else {
            // Fetch data by username
            cursor = dbManager.fetchByUsername(usernameToSearch);
            adapter.changeCursor(cursor);
        }

        regionButton.setText("Region");
        selectedRegion = "All";
        positionButton.setText("Position");
        selectedPosition = "All";
        sortButton.setText("Sort");
    }

    // Pass additional intent.
    public Intent passIntent(Intent myIntent, String user) {
        myIntent.putExtra("username", user);
        return myIntent;
    }


}