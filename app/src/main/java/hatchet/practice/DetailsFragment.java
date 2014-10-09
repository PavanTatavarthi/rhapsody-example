package hatchet.practice;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by hatchet on 10/8/14.
 */
public class DetailsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_fragment, container);
    }

    public void updateDetails(String artistId,
                              final String artistName, final String imageURL) {
        RhapsodyAPI.RhapsodyService service =
                MyApplication.getInstance().getRhapsodyService();

        Callback<RhapsodyAPI.GenreDetails> callback = new Callback<RhapsodyAPI.GenreDetails>() {
            @Override
            public void success(RhapsodyAPI.GenreDetails genreDetails, Response response) {
                updateGenreDetails(artistName, imageURL, genreDetails);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
            }
        };

        RhapsodyAPI.getGenreDetails(artistId, callback, service);
    }

    private void updateGenreDetails(String artistName, String imageURL,
                                    RhapsodyAPI.GenreDetails genreDetails) {
        Activity activity = DetailsFragment.this.getActivity();

        if (activity == null)
            return;

        TextView title = (TextView) activity.findViewById(R.id.artistTitle);
        TextView genreTitle = (TextView) activity.findViewById(R.id.genreTitle);
        TextView genreDesc = (TextView) activity.findViewById(R.id.genreDescription);
        ImageView image = (ImageView) activity.findViewById(R.id.artistImage);

        title.setText(artistName);
        genreTitle.setText("Genre: " + genreDetails.name);
        genreDesc.setText(Html.fromHtml(genreDetails.description));

        Picasso.with(activity.getApplicationContext()).load(imageURL).into(image);
    }

}
