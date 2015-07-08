package app.streamer.spotify.nanodegree.josedlujan.com.spotifystreamer;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.streamer.spotify.nanodegree.josedlujan.com.spotifystreamer.Adapters.TopTrackSearch;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;

/**
 * Created by jose on 06/07/15.
 */
public class TopTrackActivityFragment extends Fragment {

    SearchTopTracks myCurrentTask;
    List<Track> myTopTrackList;
    ListView listView;

    String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_toptrack, container, false);

        listView = (ListView)rootView.findViewById(R.id.listtoptrack);
        id =  getActivity().getIntent().getStringExtra("id");
        searchTopTracks();
        return rootView;

    }

    public void onResume(){
        super.onResume();

        getFragmentManager().findFragmentByTag("fragment_toptrack");
    }

    public void onPause(){
        super.onPause();
        getFragmentManager().findFragmentByTag("fragment_toptrack");
    }

    public void searchTopTracks(){
        myCurrentTask = new SearchTopTracks();
        myCurrentTask.execute(id);
    }

    public class SearchTopTracks extends AsyncTask<String, Void, List<Track>> {

        @Override
        protected List<Track> doInBackground(String... params) {
            Map<String, Object> options = new HashMap<>();
            options.put("country","US");

            try{
                SpotifyApi api = new SpotifyApi();
                SpotifyService spo = api.getService();
                Tracks tracks = spo.getArtistTopTrack(id,options);
                return tracks.tracks;


            }catch (Exception e){
                e.printStackTrace();
                return null;
            }


        }

        protected  void onPostExecute(List<Track> trackList){
            super.onPostExecute(trackList);
            if(trackList == null){
                Toast.makeText(getActivity().getApplicationContext(), R.string.toptrack_search_network, Toast.LENGTH_SHORT).show();
            }

            if(trackList.size() == 0)
                Toast.makeText(getActivity().getApplicationContext(),R.string.toptrack_search,Toast.LENGTH_SHORT).show();


            myTopTrackList = trackList;
            fillTopTracks();
        }
    }

    public void fillTopTracks(){

        TopTrackSearch topTrackSearch = new TopTrackSearch(getActivity().getApplicationContext());
        topTrackSearch.setMyTopTrackList(myTopTrackList);
        listView.setAdapter(topTrackSearch);


    }


}

