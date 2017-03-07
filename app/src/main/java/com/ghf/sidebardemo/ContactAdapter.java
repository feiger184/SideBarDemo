package com.ghf.sidebardemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7 0007.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private List<Contact> list;

    public ContactAdapter(List<Contact> contacts) {
        this.list = contacts;
        notifyDataSetChanged();
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacts, null);

        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        Contact contact = list.get(position);
        if (position == 0 || !list.get(position - 1).getIndex().equals(contact.getIndex())) {
            holder.tvIndex.setVisibility(View.VISIBLE);
            holder.tvIndex.setText(contact.getIndex());
        } else {
            holder.tvIndex.setVisibility(View.GONE);
        }

        holder.tvName.setText(contact.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView tvIndex;
        ImageView ivAvatar;
        TextView tvName;

        public ContactViewHolder(View itemView) {
            super(itemView);
            tvIndex = (TextView) itemView.findViewById(R.id.tv_index);
            ivAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

}
