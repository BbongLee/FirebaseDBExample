package com.bepro.globe22.firebasedbexample;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView mNameTextView;
    private TextView mGithubTextView;
    private ImageView mProfilImageView;

    FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNameTextView = (TextView) findViewById(R.id.profile_name);
        mGithubTextView = (TextView) findViewById(R.id.profile_github);
        mProfilImageView = (ImageView) findViewById(R.id.profile_image);

        ImageView mProfileImageView;
        Picasso.with(this)
                .load("http://i.imgur.com/DvpvklR.png")
                .centerCrop() //가운데만 잘라서
                .resize(100,100) //가로세로 100픽셀
                .into(mProfilImageView);

        // Write a message to the database
        mDatabase = FirebaseDatabase.getInstance();

        DatabaseReference profileNameRef = mDatabase.getReference("profile/name");

        ValueEventListener profileNameListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                mNameTextView.setText(name);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        profileNameRef.addValueEventListener(profileNameListener);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.profile_github:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse( mGithubTextView.getText().toString() );
                intent.setData(uri);
                startActivity(intent);
                break;

        }
    }
}
