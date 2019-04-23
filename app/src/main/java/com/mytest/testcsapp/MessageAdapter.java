package com.mytest.testcsapp;

import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_ME = 0;
    private static final int VIEW_TYPE_OTHER = 1;
    public static final int VIEW_TYPE_REWARD = 2;

    private List<MessageEntry> messages;

    private OnRecyclerItemClickListener listener;
    private View.OnClickListener adListener;

    public MessageAdapter(List<MessageEntry> list) {
        this.messages = list;
    }

    public void setOnClickListener(OnRecyclerItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnAdClickListener(View.OnClickListener listener) {
        this.adListener = listener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_ME:
                return new MessageViewHolder(LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.my_message_item, parent, false), listener);
            case VIEW_TYPE_OTHER:
                return new MessageViewHolder(LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.message_item, parent, false), listener);
            case VIEW_TYPE_REWARD:
                return new RewardedVideoViewHolder(LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.rewarded_banner, parent, false), adListener);
            default:
                return new MessageViewHolder(LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.message_item, parent, false), listener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
       holder.onBind(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (!messages.get(position).isAd()) {
            if (messages.get(position).isMe()) {
                return VIEW_TYPE_ME;
            } else {
                return VIEW_TYPE_OTHER;
            }
        } else return VIEW_TYPE_REWARD;
    }

    @Override
    public int getItemCount() {
        return (messages != null) ? messages.size() : 0;
    }

    /**
     * OnClick можно было бы и по другому обработать, создать другой ViewHolder, и только на него вешать
     * OnClickListener но для данной задачи решил что изменения файла ресурса макета, будет достаточно.
     */
    public class MessageViewHolder extends BaseViewHolder implements View.OnClickListener {

        @BindView(R.id.profile_image)
        protected ImageView profileImg;

        @BindView(R.id.message_text)
        protected TextView message;

        @BindView(R.id.date)
        protected TextView date;

        @BindView(R.id.name)
        protected TextView name;

        private MessageAdapter.OnRecyclerItemClickListener listener;

        public MessageViewHolder(@NonNull View itemView, MessageAdapter.OnRecyclerItemClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            this.listener = listener;
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            if (messages != null && position < messages.size()) {
                MessageEntry product = messages.get(position);
                if (!messages.get(position).isMe()) {
                    name.setText(messages.get(position).getName());
                }
                message.setText(product.getMessage());
                date.setText(product.getDate());
                Picasso.get().load(messages.get(position).getUrl()).error(R.drawable.ic_profile).into(profileImg);
            }
        }

        @Override
        public void onClick(View v) {
            listener.OnClick(messages.get(getAdapterPosition()));
        }

        @Override
        protected void clear() {

        }
    }

    public class RewardedVideoViewHolder extends BaseViewHolder implements View.OnClickListener {

        @BindView(R.id.watch)
        protected Button button;

        private View.OnClickListener listener;

        public RewardedVideoViewHolder(@NonNull View itemView, View.OnClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.listener = listener;
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v);
        }

        @Override
        protected void clear() {

        }
    }

    public interface OnRecyclerItemClickListener {
        void OnClick(MessageEntry entry);
    }
}
