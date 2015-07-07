package app.streamer.spotify.nanodegree.josedlujan.com.spotifystreamer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import app.streamer.spotify.nanodegree.josedlujan.com.spotifystreamer.Adapters.ArtistSearch;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;

/**
 * Created by jose on 03/07/15.
 */
public class MainActivityFragment extends Fragment {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    EditText editText;
    ListView listView;
    SearchArtistTask myCurrentTask;
    List<Artist> myArtists;
    ProgressBar pb;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        editText = (EditText) rootView.findViewById(R.id.search);
        listView = (ListView) rootView.findViewById(R.id.listview_forecast);


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    searchArtist();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return rootView;
    }



    public void searchArtist(){

      myCurrentTask= new SearchArtistTask();
      myCurrentTask.execute(editText.getText().toString());
    }

    public class SearchArtistTask extends AsyncTask<String, Void, List<Artist>> {

        @Override
        protected List<Artist> doInBackground(String... params) {
            String query = params[0];

            try {
                Thread.sleep(400);
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }

            if(isCancelled() || query.length()==0)
                return  new ArrayList<Artist>();

            try {
                SpotifyApi spo = new SpotifyApi();
                SpotifyService spotifyService = spo.getService();
                ArtistsPager results = spotifyService.searchArtists(query);
                List<Artist> artists = results.artists.items;

                for(int i=0;i<artists.size();i++){
                    Artist artist = artists.get(i);
                    Log.i(LOG_TAG, i + "" + artist.name);
                }

                return results.artists.items;

            }catch (Exception e){
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(List<Artist> artists) {
            super.onPostExecute(artists);
            myArtists = artists;
            fillListView();
        }
    }

    public void fillListView(){

       final ArtistSearch artistSearch = new ArtistSearch(getActivity().getApplicationContext());
        artistSearch.setArtisList(myArtists);
        listView.setAdapter(artistSearch);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Artist artist = artistSearch.getItem(position);
                Intent intent = new Intent(getActivity().getApplicationContext(),TopTrackActivity.class);
                intent.putExtra("id",artist.id);
                intent.putExtra("name",artist.name);
                startActivity(intent);
            }
        });


    }


}
