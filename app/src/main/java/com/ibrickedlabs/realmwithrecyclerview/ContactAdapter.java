package com.ibrickedlabs.realmwithrecyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;

class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private Context context;
    private RealmResults<Contact> realmResults;
    private Realm myRealm;

    public ContactAdapter(Context context, RealmResults<Contact> realmResults, Realm myRealm) {
        this.context = context;
        this.realmResults = realmResults;
        this.myRealm = myRealm;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = realmResults.get(position);
        holder.contactNameView.setText(contact.getFirstname() + " " + contact.getLastname());
        holder.phoneNumberView.setText(contact.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return realmResults.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView contactNameView;
        private TextView phoneNumberView;
        private ImageView deleteButton;
        private ImageView editButton;

        public ViewHolder(View itemView) {
            super(itemView);
            contactNameView = (TextView) itemView.findViewById(R.id.contactName);
            phoneNumberView = (TextView) itemView.findViewById(R.id.mobileNumber);
            deleteButton = (ImageView) itemView.findViewById(R.id.delete);
            editButton = (ImageView) itemView.findViewById(R.id.edit);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myRealm.beginTransaction();
                    //Deletes specific element at that pos
                    realmResults.deleteFromRealm(getAdapterPosition());
                    //Notifies adapter about the removal structure
                    notifyItemRemoved(getAdapterPosition());
                    //We need to mention because resultlist size changes
                    notifyItemRangeChanged(getAdapterPosition(), realmResults.size());
                    myRealm.commitTransaction();
                }
            });

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditActivity.class);
                    intent.putExtra("POSITION", getAdapterPosition());
                    context.startActivity(intent);
                }
            });


        }
    }
}
