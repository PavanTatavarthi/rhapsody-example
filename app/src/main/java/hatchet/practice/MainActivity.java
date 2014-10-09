package hatchet.practice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;


public class MainActivity extends Activity {

    public static final String EXTRA_ARTIST_ID = "hatchet.practice.ARTIST_ID";
    public static final String EXTRA_ARTIST_NAME = "hatchet.practice.ARTIST_NAME";
    public static final String EXTRA_ARTIST_IMAGE_URL = "hatchet.practice.ARTIST_IMAGE_URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                Log.e("TAG", "Error", throwable);
            }
        });

        final ArtistsFragment artists = (ArtistsFragment) getFragmentManager().
                findFragmentById(R.id.artists);

        final DetailsFragment details = (DetailsFragment) getFragmentManager().
                findFragmentById(R.id.artistDetails);

        if (findViewById(R.id.detailsFrame) != null) {
            artists.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    details.updateDetails(artists.getArtistId(i),
                            artists.getArtistName(i),
                            artists.getArtistLargeImageURL(i));
                }
            });
        } else {
            artists.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra(EXTRA_ARTIST_ID, artists.getArtistId(i));
                intent.putExtra(EXTRA_ARTIST_NAME, artists.getArtistName(i));
                intent.putExtra(EXTRA_ARTIST_IMAGE_URL, artists.getArtistLargeImageURL(i));
                startActivity(intent);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
