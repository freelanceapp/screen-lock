package me.mojodigi.lockscreen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.mojodigi.lockscreen.R;
import me.mojodigi.lockscreen.model.NewsDataModel;
import me.mojodigi.lockscreen.utils.Helper;


public class NewsAdapter extends  RecyclerView.Adapter<NewsAdapter.NewsViewHolder>
{

    private List<NewsDataModel> newsList;
    private newsListener listener;
    Context ctx;

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item_view, parent, false);
                   return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position)
    {
        holder.newsTitle.setText(newsList.get(position).getNewsDesc());
        holder.newsTitle.setSelected(true);
        holder.newsTitle.setTypeface(Helper.typeFace_CORBEL(ctx));
        holder.newsTitle.requestFocus();

        if(position==0)
            holder.newsIcon.setImageResource(R.mipmap.filehunt);
        else  if(position==1)
        holder.newsIcon.setImageResource(R.mipmap.khulasa);
        else if(position==2)
            holder.newsIcon.setImageResource(R.mipmap.dailyhunt);


        }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        public TextView newsTitle;
        ImageView newsIcon;


        public NewsViewHolder(View view) {
            super(view);
            newsTitle = (TextView) view.findViewById(R.id.newsTitle);
            newsIcon=(ImageView)view.findViewById(R.id.newsIcon);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION)
                        listener.onNewsClicked(newsList.get(getAdapterPosition()));

                }
            });
        }



    }

    public NewsAdapter(List<NewsDataModel> newsList, newsListener listener,Context ctx) {
        this.newsList = newsList;
        this.listener = listener;
        this.ctx=ctx;
    }


    public interface newsListener {
        void onNewsClicked(NewsDataModel newsDataModel);
    }

}