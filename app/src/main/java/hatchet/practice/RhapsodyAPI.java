package hatchet.practice;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by hatchet on 10/7/14.
 */
public class RhapsodyAPI {

    public static final String apiKey = "FF3m3Ux0fES32FFvc08QMY1xRH6XGOgn";
    public static final String host = "http://api.rhapsody.com/v1";

    public static class Artist {
        public String id;
        public String name;

        @Override
        public String toString() {
            return name;
        }
    }

    public static class ArtistImage {
        public int width;
        public int height;
        public String url;
    }

    public static class ArtistWithImages {
        public final Artist artist;
        public final List<ArtistImage> image;

        public ArtistWithImages(Artist artist, List<ArtistImage> image) {
            this.artist = artist;
            this.image = image;
        }
    }

    public static class Genre {
        public String id;
    }

    public static class ArtistDetails {
        public String id;
        public String name;
        public Genre genre;
    }

    public static class GenreDetails {
        public String id;
        public String name;
        public String description;
    }

    public static interface RhapsodyService {
        @GET("/artists/top?apikey="+apiKey)
        public void getTopArtists(Callback<List<Artist>> c);

        @GET("/artists/{id}/images?apikey="+apiKey)
        public List<ArtistImage> getArtistImages(@Path("id") String id);

        @GET("/artists/{id}/images?apikey="+apiKey)
        public void getArtistImages(@Path("id") String id,
                                    Callback<List<ArtistImage>> c);

        @GET("/artists/{id}?apikey="+apiKey)
        public ArtistDetails getArtistDetails(@Path("id") String id);

        @GET("/artists/{id}?apikey="+apiKey)
        public void getArtistDetails(@Path("id") String id,
                                     Callback<ArtistDetails> c);

        @GET("/genres/{id}?apikey="+apiKey)
        public GenreDetails getGenreDetails(@Path("id") String id);

        @GET("/genres/{id}?apikey="+apiKey)
        public void getGenreDetails(@Path("id") String id,
                                    Callback<GenreDetails> c);
    }

    public static void getTopArtistsWithImages(
            final Callback<List<ArtistWithImages>> callback,
            final RhapsodyService service) {

        Callback<List<Artist>> topArtistsCallback = new Callback<List<Artist>>() {
            @Override
            public void success(List<Artist> artists, Response response) {
                GetArtistsImagesTask task = new GetArtistsImagesTask(
                        service, callback);
                task.execute(artists);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.i("RetrofitError", retrofitError.getMessage());
            }
        };

        service.getTopArtists(topArtistsCallback);
    }

    public static void getGenreDetails(String artistId,
                                       Callback<GenreDetails> callback,
                                       RhapsodyService service) {

        new GetGenreDetailsTask(service, callback).execute(artistId);
    }

    private static class GetArtistsImagesTask extends
            AsyncTask<List<Artist>, Void, List<ArtistWithImages>> {

        private RhapsodyService service;
        private Callback<List<ArtistWithImages>> callback;

        private GetArtistsImagesTask(RhapsodyService service,
                                     Callback<List<ArtistWithImages>> callback) {
            this.service = service;
            this.callback = callback;
        }

        @Override
        protected List<ArtistWithImages> doInBackground(List<Artist>... artistss) {
            List<Artist> artists = artistss[0];
            List<ArtistWithImages> artistsWithImage =
                    new ArrayList<ArtistWithImages>();

            for (Artist artist : artists) {
                List<ArtistImage> images = service.getArtistImages(artist.id);
                artistsWithImage.add(new ArtistWithImages(artist, images));
                if (isCancelled())
                    break;
            }

            return artistsWithImage;
        }

        @Override
        protected void onPostExecute(List<ArtistWithImages> artistWithImages) {
            callback.success(artistWithImages, null);
        }
    }

    private static class GetGenreDetailsTask extends
            AsyncTask<String, Void, GenreDetails> {

        private RhapsodyService service;
        private Callback<GenreDetails> callback;

        private GetGenreDetailsTask(RhapsodyService service,
                                     Callback<GenreDetails> callback) {
            this.service = service;
            this.callback = callback;
        }

        @Override
        protected GenreDetails doInBackground(String... ids) {
            String id = ids[0];
            ArtistDetails details = service.getArtistDetails(id);
            if (isCancelled())
                return null;
            return service.getGenreDetails(details.genre.id);
        }

        @Override
        protected void onPostExecute(GenreDetails genreDetails) {
            if (genreDetails == null) {
                RetrofitError err = RetrofitError.networkError("", new IOException("Task cancelled"));
                callback.failure(err);
            } else {
                callback.success(genreDetails, null);
            }
        }

    }

}
