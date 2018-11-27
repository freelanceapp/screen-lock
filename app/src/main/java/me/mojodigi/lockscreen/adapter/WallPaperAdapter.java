package me.mojodigi.lockscreen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.List;

import me.mojodigi.lockscreen.R;
import me.mojodigi.lockscreen.model.WallPaperModel;

public class WallPaperAdapter extends  RecyclerView.Adapter<WallPaperAdapter.MyViewHolder> {
    private List<WallPaperModel> wallpaperlList;
     Context ctx;
     WallPaperListener listener;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
            return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position)

    {

        String url = wallpaperlList.get(position).getFilePath();

            Glide.with(ctx)
                    .load(url)
                    .into(holder.wallImg);
            }

    @Override
    public int getItemCount() {
        return wallpaperlList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
   
        public ImageView wallImg;

        public MyViewHolder(View view) {
            super(view);
            wallImg=(ImageView)view.findViewById(R.id.wallImg);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected wallpaer in callback
                    int pos=getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION)
                        listener.onWallPaperSelected(wallpaperlList.get(getAdapterPosition()));
                }
            });

        }
    }
    public WallPaperAdapter(List<WallPaperModel> wallpaperlList,Context ctx,WallPaperListener listener) {
        this.wallpaperlList = wallpaperlList;
        this.ctx=ctx;
        this.listener=listener;
    }

    public interface WallPaperListener {
        void onWallPaperSelected(WallPaperModel  wallPaperModel);
    }
}
