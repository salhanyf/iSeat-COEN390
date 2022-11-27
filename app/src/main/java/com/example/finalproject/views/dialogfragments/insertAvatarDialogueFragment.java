package com.example.finalproject.views.dialogfragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.finalproject.R;
import com.example.finalproject.views.Cards.Admin_InfoCard;
import com.example.finalproject.views.Cards.User_InfoCard;

public class insertAvatarDialogueFragment extends DialogFragment {

    protected ImageView chosenAvatar;
    protected ImageButton avatarButton1;
    protected ImageButton avatarButton2;
    protected ImageButton avatarButton3;
    protected ImageButton avatarButton4;
    protected ImageButton avatarButton5;
    protected ImageButton avatarButton6;
    protected ImageButton avatarButton7;
    protected ImageButton avatarButton8;
    protected ImageButton avatarButton9;
    protected Button closeButton;
    private SharedPreferences sharedPreferences;
    private boolean isUser;

    public insertAvatarDialogueFragment(boolean isUser) {
        // Required empty public constructor
        this.isUser = isUser;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.sharedPreferences = getContext().getSharedPreferences("profile_Shared_Pref", MODE_PRIVATE);

        View view1 = inflater.inflate(R.layout.dialoguefragment_add_avatar, container);
        View view2 = inflater.inflate(R.layout.activity_user_info_card, container);

        chosenAvatar = view2.findViewById(R.id.shownUserAvatar);
        avatarButton1 = view1.findViewById(R.id.editAvatarButton1);
        avatarButton2 = view1.findViewById(R.id.editAvatarButton2);
        avatarButton3 = view1.findViewById(R.id.editAvatarButton3);
        avatarButton4 = view1.findViewById(R.id.editAvatarButton4);
        avatarButton5 = view1.findViewById(R.id.editAvatarButton5);
        avatarButton6 = view1.findViewById(R.id.editAvatarButton6);
        avatarButton7 = view1.findViewById(R.id.editAvatarButton7);
        avatarButton8 = view1.findViewById(R.id.editAvatarButton8);
        avatarButton9 = view1.findViewById(R.id.editAvatarButton9);
        closeButton = view1.findViewById(R.id.closeDialogueButton);

        // When user chooses avatarButton1, set chosenAvatar to avatarButton1
        avatarButton1.setOnClickListener(v -> setImageID(1));

        // When user chooses avatarButton2, set chosenAvatar to avatarButton2
        avatarButton2.setOnClickListener(v -> setImageID(2));

        // When user chooses avatarButton3, set chosenAvatar to avatarButton3
        avatarButton3.setOnClickListener(v -> setImageID(3));

        // When user chooses avatarButton4, set chosenAvatar to avatarButton4
        avatarButton4.setOnClickListener(v -> setImageID(4));

        // When user chooses avatarButton5, set chosenAvatar to avatarButton5
        avatarButton5.setOnClickListener(v -> setImageID(5));

        // When user chooses avatarButton6, set chosenAvatar to avatarButton6
        avatarButton6.setOnClickListener(v -> setImageID(6));

        // When user chooses avatarButton7, set chosenAvatar to avatarButton7
        avatarButton7.setOnClickListener(v -> setImageID(7));

        // When user chooses avatarButton8, set chosenAvatar to avatarButton8
        avatarButton8.setOnClickListener(v -> setImageID(8));

        // When user chooses avatarButton9, set chosenAvatar to avatarButton9
        avatarButton9.setOnClickListener(v -> setImageID(9));

        // When user clicks closeButton, close dialogue fragment
        closeButton.setOnClickListener(v -> getDialog().dismiss());

        return view1;
    }

    private void setImageID(int imageID) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(isUser == true) {
            editor.putInt("ImageID", imageID);
            editor.commit();
            Toast.makeText(getActivity(), "Avatar changed!", Toast.LENGTH_SHORT).show();
            ((User_InfoCard) getActivity()).updateImage();
        } else {
            editor.putInt("ImageID", imageID);
            editor.commit();
            Toast.makeText(getActivity(), "Avatar changed!", Toast.LENGTH_SHORT).show();
            ((Admin_InfoCard) getActivity()).updateImage();
        }
    }
}