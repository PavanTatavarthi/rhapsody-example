package hatchet.practice;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by hatchet on 10/7/14.
 */
public class ArtistsFragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        setupList();
    }

    private void setupList() {
        RhapsodyAPI.RhapsodyService rhapsodyService =
                MyApplication.getInstance().getRhapsodyService();

        final List<RhapsodyAPI.ArtistWithImages> topArtists =
                new ArrayList<RhapsodyAPI.ArtistWithImages>();

        final ArtistAdapter adapter = new ArtistAdapter(
                getActivity(), android.R.layout.simple_list_item_1,
                topArtists);

        Callback<List<RhapsodyAPI.ArtistWithImages>> callback =
                new Callback<List<RhapsodyAPI.ArtistWithImages>>() {
            @Override
            public void success(List<RhapsodyAPI.ArtistWithImages> artistsWithImages, Response response) {
                topArtists.addAll(artistsWithImages);
                adapter.notifyDataSetInvalidated();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.i("RetrofitError", retrofitError.getMessage());
            }
        };

        RhapsodyAPI.getTopArtistsWithImages(callback, rhapsodyService);

        setListAdapter(adapter);
    }

    public String getArtistId(int i) {
        RhapsodyAPI.ArtistWithImages a =
                (RhapsodyAPI.ArtistWithImages) getListView().getItemAtPosition(i);
        return a.artist.id;
    }

    public String getArtistName(int i) {
        RhapsodyAPI.ArtistWithImages a =
                (RhapsodyAPI.ArtistWithImages) getListView().getItemAtPosition(i);
        return a.artist.name;
    }

    public String getArtistLargeImageURL(int i) {
        RhapsodyAPI.ArtistWithImages a =
                (RhapsodyAPI.ArtistWithImages) getListView().getItemAtPosition(i);
        return a.image.get(0).url;
    }

}
