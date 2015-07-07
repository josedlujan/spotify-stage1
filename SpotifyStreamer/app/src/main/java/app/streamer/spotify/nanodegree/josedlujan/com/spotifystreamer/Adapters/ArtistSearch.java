package app.streamer.spotify.nanodegree.josedlujan.com.spotifystreamer.Adapters;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import app.streamer.spotify.nanodegree.josedlujan.com.spotifystreamer.R;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Image;

/**
 * Created by jose on 04/07/15.
 */
public class ArtistSearch extends BaseAdapter {
   Context myContext;
    LayoutInflater myLayoutInflater;
    List<Artist> myArtistList;
    //para pruebas Log
    //public static final String LOG_TAG = MainActivity.class.getSimpleName();

    public  ArtistSearch(Context context){
    myContext = context;
    myLayoutInflater = LayoutInflater.from(context);
    myArtistList = new ArrayList<>();
    }

    public void setArtisList(List<Artist> artisList){
        myArtistList = artisList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return myArtistList.size();
    }

    @Override
    public Artist getItem(int position) {
        if(myArtistList.size()==0){
            return null;

        }
        return
                 myArtistList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView != null){
            holder = (ViewHolder) convertView.getTag();
        }else{
            convertView = myLayoutInflater.inflate(R.layout.list_item_artist,parent,false);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.textView);
            holder.imageView = (ImageView) convertView.findViewById(R.id.thumbnail);

            convertView.setTag(holder);
        }

        Artist artist = myArtistList.get(position);

        Image image = null;

       if((artist.images != null) && (artist.images.size()>0)){
            for (Image currentImage: artist.images){
                if (currentImage.width>=200){
                    image= currentImage;
                }
            }
        }


        if(image !=null){
        Picasso.with(myContext)
                     .load(image.url)
                  .into(holder.imageView);
        }else{
            holder.imageView.setImageBitmap(null);
        }

        holder.textView.setText(artist.name);
        return convertView;
    }

    static class ViewHolder{
        TextView textView;
        ImageView imageView;
    }
}
