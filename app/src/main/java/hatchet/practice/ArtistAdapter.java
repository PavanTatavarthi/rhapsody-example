package hatchet.practice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hatchet on 10/7/14.
 */
public class ArtistAdapter extends ArrayAdapter<RhapsodyAPI.ArtistWithImages> {

    private List<RhapsodyAPI.ArtistWithImages> artistsWithImages;
    private LayoutInflater inflater;

    public ArtistAdapter(Context context, int resource,
                         List<RhapsodyAPI.ArtistWithImages> objects) {
        super(context, resource, objects);

        this.artistsWithImages = objects;
        this.inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null)
            view = inflater.inflate(R.layout.artist_layout, parent, false);

        ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        TextView artistName = (TextView) view.findViewById(R.id.artistName);

        RhapsodyAPI.ArtistWithImages artistWithImage =
                artistsWithImages.get(position);

        String url = artistWithImage.image.get(1).url;

        Picasso.with(parent.getContext()).load(url).into(thumbnail);
        artistName.setText(artistWithImage.artist.name);

        return view;
    }
}
