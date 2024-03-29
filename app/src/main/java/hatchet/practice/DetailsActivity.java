package hatchet.practice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class DetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent i = getIntent();
        String id = i.getStringExtra(MainActivity.EXTRA_ARTIST_ID);
        String name = i.getStringExtra(MainActivity.EXTRA_ARTIST_NAME);
        String imageURL = i.getStringExtra(MainActivity.EXTRA_ARTIST_IMAGE_URL);

        DetailsFragment details = (DetailsFragment) getFragmentManager()
                .findFragmentById(R.id.artistDetails);

        details.updateDetails(id, name, imageURL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

}
