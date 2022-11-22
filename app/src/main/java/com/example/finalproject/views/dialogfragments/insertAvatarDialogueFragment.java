package com.example.finalproject.views.dialogfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.finalproject.R;

public class insertAvatarDialogueFragment extends DialogFragment {

    protected ImageView chosenAvatar;
    protected ImageView avatarEditImageView1;
    protected ImageView avatarEditImageView2;
    protected ImageView avatarEditImageView3;
    protected ImageView avatarEditImageView4;
    protected ImageView avatarEditImageView5;
    protected ImageView avatarEditImageView6;
    protected ImageView avatarEditImageView7;
    protected ImageView avatarEditImageView8;
    protected ImageView avatarEditImageView9;
    protected Button saveButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialoguefragment_add_avatar, container);
        return view;
    }
}
