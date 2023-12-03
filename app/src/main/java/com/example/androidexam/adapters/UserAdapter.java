package com.example.androidexam.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidexam.R;
import com.example.androidexam.models.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList;

    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setUserList(List<User> newList) {
        userList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);

        holder.textName.setText(user.name);
        holder.textEmail.setText(user.email);
        holder.textPhone.setText(user.phone);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textEmail;
        TextView textPhone;

        UserViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textFullName);
            textEmail = itemView.findViewById(R.id.textEmailAddress);
            textPhone = itemView.findViewById(R.id.textPhoneNumber);
        }
    }
}

