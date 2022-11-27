//package com.example.finalproject.views;
//
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.example.finalproject.R;
//
//public class addAvatarActivity {
//
//    protected ImageView chosenAvatar;
//    protected ImageButton avatarButton1;
//    protected ImageButton avatarButton2;
//    protected ImageButton avatarButton3;
//    protected ImageButton avatarButton4;
//    protected ImageButton avatarButton5;
//    protected ImageButton avatarButton6;
//    protected ImageButton avatarButton7;
//    protected ImageButton avatarButton8;
//    protected ImageButton avatarButton9;
//    protected Button closeButton;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        setContentView(R.layout.activity_user_info_card);
//
//        chosenAvatar = findViewById(R.id.shownAvatar);
//        avatarButton1 = findViewById(R.id.editAvatarButton1);
//        avatarButton2 = findViewById(R.id.editAvatarButton2);
//        avatarButton3 = findViewById(R.id.editAvatarButton3);
//        avatarButton4 = findViewById(R.id.editAvatarButton4);
//        avatarButton5 = findViewById(R.id.editAvatarButton5);
//        avatarButton6 = findViewById(R.id.editAvatarButton6);
//        avatarButton7 = findViewById(R.id.editAvatarButton7);
//        avatarButton8 = findViewById(R.id.editAvatarButton8);
//        avatarButton9 = findViewById(R.id.editAvatarButton9);
//        closeButton = findViewById(R.id.closeDialogueButton);
//
//        // When user chooses avatarButton1, set chosenAvatar to avatarButton1
//        avatarButton1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chosenAvatar.setImageResource(R.drawable.avatars1);
//                getSupportFragmentManager().findFragmentById(R.id.shownAvatar);
//                // Make toast to show that avatar has been changed
//                Toast.makeText(getActivity(), "Avatar changed!", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // When user chooses avatarButton2, set chosenAvatar to avatarButton2
//        avatarButton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chosenAvatar.setImageResource(R.drawable.avatars2);
//                getSupportFragmentManager().findFragmentById(R.id.shownAvatar);
//                // Make toast to show that avatar has been changed
//                Toast.makeText(getActivity(), "Avatar changed!", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // When user chooses avatarButton3, set chosenAvatar to avatarButton3
//        avatarButton3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chosenAvatar.setImageResource(R.drawable.avatars3);
//                getSupportFragmentManager().findFragmentById(R.id.shownAvatar);
//                // Make toast to show that avatar has been changed
//                Toast.makeText(getActivity(), "Avatar changed!", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // When user chooses avatarButton4, set chosenAvatar to avatarButton4
//        avatarButton4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chosenAvatar.setImageResource(R.drawable.avatars4);
//                getSupportFragmentManager().findFragmentById(R.id.shownAvatar);
//                // Make toast to
//
//}
