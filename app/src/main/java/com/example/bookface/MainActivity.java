package com.example.bookface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ArrayList<BookFace> mBookFaces;
    BookFaceAdapter mBookFaceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);

        mBookFaces = new ArrayList<>();

        mBookFaceAdapter = new BookFaceAdapter(this, R.layout.bookface_layout, mBookFaces);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setAdapter(mBookFaceAdapter);
        mRecyclerView.setLayoutManager(layoutManager);

        loadCeriwits();
    }

    private void loadCeriwits() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Ceriwit");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    mBookFaces.clear();

                    for (int i = 0; i < objects.size(); i++) {
                        ParseObject parseObject = objects.get(i);

                        try {
                            ParseUser user = parseObject.getParseUser("user");
                            user.fetchIfNeeded();
                            String username = user.getUsername();
                            String message = parseObject.getString("message");

                            mBookFaces.add(new BookFace(parseObject.getObjectId(), username, message));
                        } catch (Exception x) {
                            x.printStackTrace();
                        }
                    }

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBookFaceAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        if (ParseUser.getCurrentUser() == null) {
            menuInflater.inflate(R.menu.menu_main_notloggedin, menu);
        } else {
            menuInflater.inflate(R.menu.menu_main_loggedin, menu);
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemLogin) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.itemRegister) {
            Intent intent = new Intent(this,  RegisterActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.itemLogout) {
            ParseUser.logOut();

            finish();
            startActivity(getIntent());
        } else if (item.getItemId() == R.id.itemAddComment) {
            Intent intent = new Intent(this,  PostActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
