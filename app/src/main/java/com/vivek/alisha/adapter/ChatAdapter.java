package com.vivek.alisha.adapter;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vivek.alisha.R;
import com.vivek.alisha.model.ChatModel;
import com.vivek.alisha.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vivek on 20-09-2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {

    Context context;
    List<ChatModel> chatModelList;

    public ChatAdapter(Context context, List<ChatModel> chatModelList) {
        this.context = context;
        this.chatModelList = chatModelList;
    }

    @Override
    public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_chat, parent, false);

        return new ChatHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatHolder holder, int position) {

        switch (chatModelList.get(position).getUser()){

            case Constants.AI:
                holder.rowTxtChat.setText("Alisha:\u0020"+chatModelList.get(position).getChat());
                break;
            case Constants.USER:
                holder.rowTxtChat.setText("Me:\u0020"+chatModelList.get(position).getChat());
                break;
        }


        if (position % 2 == 0) {

          holder.rowTxtChat.setBackgroundColor(ResourcesCompat
                  .getColor(context.getResources(),android.R.color.white,null));
        } else {

            holder.rowTxtChat.setBackgroundColor(ResourcesCompat
                    .getColor(context.getResources(),R.color.colorLightGrey,null));
        }
    }

    @Override
    public int getItemCount() {
        return chatModelList.size();
    }

    public class ChatHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rowTxtChat)
        TextView rowTxtChat;

        public ChatHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
