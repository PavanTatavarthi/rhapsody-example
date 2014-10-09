package hatchet.practice;

import android.app.Application;

import retrofit.RestAdapter;

/**
 * Created by hatchet on 10/7/14.
 */
public class MyApplication extends Application {

    private static MyApplication instance;
    private RhapsodyAPI.RhapsodyService rhapsodyService;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(RhapsodyAPI.host)
                .build();

        rhapsodyService = adapter.create(RhapsodyAPI.RhapsodyService.class);
    }

    public RhapsodyAPI.RhapsodyService getRhapsodyService() {
        return rhapsodyService;
    }

}
