package com.bestebeyzayilmaz.instagramclone.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bestebeyzayilmaz.instagramclone.R;
import com.bestebeyzayilmaz.instagramclone.adapter.PostAdapter;
import com.bestebeyzayilmaz.instagramclone.databinding.ActivityFeedBinding;
import com.bestebeyzayilmaz.instagramclone.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<Post> postArrayList;
    private ActivityFeedBinding binding;
    PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        postArrayList = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        GetData();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(postArrayList);
        binding.recyclerView.setAdapter(postAdapter);
    }

    private void GetData(){
        firebaseFirestore.collection("Posts").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Toast.makeText(FeedActivity.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }

                if (value != null){
                    for(DocumentSnapshot snapshot : value.getDocuments()){
                        Map<String, Object> data = snapshot.getData();

                        //casting
                        String useremail = (String) data.get("usermail");
                        String comment = (String) data.get("comment");
                        String downloadurl = (String) data.get("downloadurl");

                        Post post = new Post(useremail,comment,downloadurl);
                        postArrayList.add(post);

                    }

                    postAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //bağlama işlemi

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //seçilince ne olacağı

        if (item.getItemId() == R.id.add_post){
            //upload activity yonlendirme
            Intent intentToUpload = new Intent(FeedActivity.this, UploadActivity.class);
            startActivity(intentToUpload);

        }else if (item.getItemId() == R.id.signout){
            //sign in
            auth.signOut();

            Intent intentToMain = new Intent(FeedActivity.this, MainActivity.class);
            startActivity(intentToMain);
            finish();
        }

        return super.onOptionsItemSelected(item);

    }
}