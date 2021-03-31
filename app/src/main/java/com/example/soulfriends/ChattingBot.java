package com.example.soulfriends;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soulfriends.databinding.ActivityChattingBotBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class ChattingBot extends AppCompatActivity {

    private static final String TAG = "ChattingBot";
    private FirebaseDatabase mDatabase;
    private FirebaseRecyclerAdapter<ChatMessage, MessageViewHolder>
            mFirebaseAdapter;
    public static final String MESSAGES_CHILD = "messages";
    public static final String ANONYMOUS = "anonymous";
    private static final int REQUEST_IMAGE = 2;
    private static final String LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif";
    private LinearLayoutManager mLinearLayoutManager;
    private ActivityChattingBotBinding mBinding;
    private FirebaseAuth mFirebaseAuth;
    EmojiconEditText emojiconEditText;
    ImageView emojiButton;
    Button submitButton;
    EmojIconActions emojIconActions;
    RelativeLayout activity_main;
    RelativeLayout chatting_bot;
    RecyclerView listOfMessage;
    Query query;
   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_sign_out)
        {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Snackbar.make(activity_main,"You have been signed out.", Snackbar.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
        return true;
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_bot);

        submitButton=(Button) findViewById(R.id.sendButton);
        mBinding = ActivityChattingBotBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        // Initialize Realtime Database
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference messagesRef = mDatabase.getReference().child(MESSAGES_CHILD);
        FirebaseRecyclerOptions<ChatMessage> options =
                new FirebaseRecyclerOptions.Builder<ChatMessage>()
                        .setQuery(messagesRef, ChatMessage.class)
                        .build();

       mFirebaseAdapter=new FirebaseRecyclerAdapter<ChatMessage, MessageViewHolder>(options) {
           @Override
           protected void onBindViewHolder(@NonNull MessageViewHolder holder, int position, @NonNull ChatMessage model) {

           }

           @NonNull
           @Override
           public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
               LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
               return new MessageViewHolder(inflater.inflate(R.layout.activity_messagelist, viewGroup, false));
           }
       };
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        mBinding.messageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mBinding.messageRecyclerView.setAdapter(mFirebaseAdapter);
mFirebaseAdapter.registerAdapterDataObserver(new MyScrollToBottomObserver(mBinding.messageRecyclerView,mFirebaseAdapter,mLinearLayoutManager));

        // Disable the send button when there's no text in the input field
// See MyButtonObserver.java for details
        mBinding.messageEditText.addTextChangedListener(new MyButtonObserver(mBinding.sendButton));
        // When the send button is clicked, send a text message
        mBinding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatMessage friendlyMessage = new
                        ChatMessage(mBinding.messageEditText.getText().toString(),
                        getUserName(),
                        getUserPhotoUrl(),
                        null /* no image */);

                mDatabase.getReference().child(MESSAGES_CHILD).push().setValue(friendlyMessage);
                mBinding.messageEditText.setText("");
            }
        });
        // When the image button is clicked, launch the image picker
        mBinding.addMessageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        });
     /*   activity_main = (RelativeLayout)findViewById(R.id.activity_chatting_bot);

        listOfMessage = (RecyclerView) findViewById(R.id.list_of_message);
        listOfMessage.setLayoutManager(new LinearLayoutManager(this));

        query = FirebaseDatabase.getInstance().getReference();
        options = new FirebaseRecyclerOptions.Builder<ChatMessage>()
                .setQuery(query,ChatMessage.class)
                .build();
*/
      /*  emojiButton = (ImageView)findViewById(R.id.emoji_button);
        submitButton = (ImageView)findViewById(R.id.submit_button);
        emojiconEditText = (EmojiconEditText)findViewById(R.id.emojicon_edit_text);
        emojIconActions = new EmojIconActions(getApplicationContext(),activity_main,emojiButton,emojiconEditText);
        emojIconActions.ShowEmojicon();
        */
     /*   submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                emojiconEditText.setText("");
                emojiconEditText.requestFocus();
            }
        });
        if(FirebaseAuth.getInstance().getCurrentUser() == null)
        {
            Intent Chtosign = new Intent(ChattingBot.this,Signin.class);
            startActivity(Chtosign);
        }
        else
        {
            Snackbar.make(activity_main,"Welcome "+FirebaseAuth.getInstance().getCurrentUser().getEmail(),Snackbar.LENGTH_SHORT).show();
            //Load content

        }*/
    }

    private String getUserPhotoUrl() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null && user.getPhotoUrl() != null) {
            return user.getPhotoUrl().toString();
        }

        return null;
    }

    private String getUserName() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null) {
            return user.getDisplayName();
        }

        return ANONYMOUS;
    }

    @Override
    public void onPause() {
        mFirebaseAdapter.stopListening();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mFirebaseAdapter.startListening();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK && data != null) {
                final Uri uri = data.getData();
                Log.d(TAG, "Uri: " + uri.toString());

                final FirebaseUser user = mFirebaseAuth.getCurrentUser();
                ChatMessage tempMessage = new ChatMessage(
                        null, getUserName(), getUserPhotoUrl(), LOADING_IMAGE_URL);

                mDatabase.getReference().child(MESSAGES_CHILD).push()
                        .setValue(tempMessage, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError,
                                                   DatabaseReference databaseReference) {
                                if (databaseError != null) {
                                    Log.w(TAG, "Unable to write message to database.",
                                            databaseError.toException());
                                    return;
                                }

                                // Build a StorageReference and then upload the file
                                String key = databaseReference.getKey();
                                StorageReference storageReference =
                                        FirebaseStorage.getInstance()
                                                .getReference(user.getUid())
                                                .child(key)
                                                .child(uri.getLastPathSegment());

                                putImageInStorage(storageReference, uri, key);
                            }
                        });
            }
        }
    }
    private void putImageInStorage(StorageReference storageReference, Uri uri, final String key) {
        // First upload the image to Cloud Storage
        storageReference.putFile(uri)
                .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // After the image loads, get a public downloadUrl for the image
                        // and add it to the message.
                        taskSnapshot.getMetadata().getReference().getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        ChatMessage friendlyMessage = new ChatMessage(
                                                null, getUserName(), getUserPhotoUrl(), uri.toString());
                                        mDatabase.getReference()
                                                .child(MESSAGES_CHILD)
                                                .child(key)
                                                .setValue(friendlyMessage);
                                    }
                                });
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Image upload task was not successful.", e);
                    }
                });
    }

/*
    private void displayChatMessage() {


        adapter = new FirebaseRecyclerAdapter<ChatMessage, ChatViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ChatViewHolder holder, int position, @NonNull ChatMessage model) {
                holder.messageText.setText(model.getMessageText());
                holder.messageUser.setText(model.getMessageUser());
                holder.messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));

            }

            @NonNull
            @Override
            public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new ChatViewHolder(LayoutInflater.from(ChattingBot.this)
                        .inflate(R.layout.activity_messagelist,viewGroup,false));
            }
        };


        listOfMessage.setAdapter(adapter);
    }*/


   /* private class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView messageText, messageUser, messageTime;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = (BubbleTextView) itemView.findViewById(R.id.message_text);
            messageUser = (TextView) itemView.findViewById(R.id.message_user);
            messageTime = (TextView) itemView.findViewById(R.id.message_time);
        }
    }*/
}