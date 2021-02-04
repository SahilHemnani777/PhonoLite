package com.example.phonolite;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


public class ProfileActivity extends AppCompatActivity {

    public static final String FRAGMENT_TAG="PROFILE_FRAGMENT_TAG";
    // UI Elements
    private ShapeableImageView profileImgIv;
    private TextView displayNameTv;
    private TextView emailTv;
    private Button signOutBtn;
    private TextView editProfileTv;

    // Firebase Auth
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileImgIv=findViewById(R.id.profile_img_iv);
        // configure display name textview
        displayNameTv=findViewById(R.id.displayname_tv);
        // configure email textview
        emailTv=findViewById(R.id.email_tv);
        // configure sign out button
        signOutBtn=findViewById(R.id.signout_btn);
        // give on click listener
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            }
        });
        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();

        // if user is logged in
        if(currentUser!=null) {
            // load the profile image in profile picture imageview
            Uri photoUrl=currentUser.getPhotoUrl();
            /* this photoUrl is 90px size photo Url, we want to request of higher resolution*/
            if(photoUrl!=null) {
                // get the uri in string format
                String photoUrlStr=photoUrl.toString();
                // convert the uri string to required pixel size
                photoUrlStr= googlePhotoUrlOfPixelSize(photoUrlStr,360);
                // load the picture in profile picture imageview
                Picasso.get().
                        load(photoUrlStr)
                        .placeholder(R.drawable.ic_baseline_account_circle_24)  // placeholder for network image
                        .error(R.drawable.ic_baseline_account_circle_24)    // if placeholder for error
                        .into(profileImgIv);
            }
            // load user name in display name textview
            displayNameTv.setText(currentUser.getDisplayName());
            // load user email in email textview
            emailTv.setText(currentUser.getEmail());
        }
    }

    public static String googlePhotoUrlOfPixelSize(String photoUrl, int pxWidth) {
        // Variable holding the original String portion of the url that will be replaced
        String originalPieceOfUrl = "s96-c";

        // Variable holding the new String portion of the url that does the replacing, to improve image quality
        String newPieceOfUrlToAdd = "s"+pxWidth+"-c";

        // Replace the original part of the Url with the new part
        String newPhotoUrl = photoUrl.replace(originalPieceOfUrl, newPieceOfUrlToAdd);

        // now the newPhotoUrl consist url of given pixel size
        return newPhotoUrl;
    }

}