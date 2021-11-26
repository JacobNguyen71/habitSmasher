package com.example.habitsmasher.ui.profile;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.habitsmasher.HabitEvent;
import com.example.habitsmasher.ImageDatabaseHelper;
import com.example.habitsmasher.R;
import com.example.habitsmasher.User;
import com.example.habitsmasher.UserDatabaseHelper;
import com.example.habitsmasher.ui.history.HabitEventItemAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * UI class that represents and specifies the behaviour of the user's profile screen
 * Currently, only displays information of a test user
 */
public class ProfileFragment extends Fragment {
    private static final String USER_DATA_PREFERENCES_TAG = "USER_DATA";
    private static final String USERNAME_SHARED_PREF_TAG = "username";
    private static final String USER_ID_SHARED_PREF_TAG = "userId";

    private ProfileFragment _fragment = this;
    private ImageView _userImageView;
    private Bitmap _userImage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // create the sample user
        User user = new User("1", "TestUser", "123@gmail.com", "123", new ArrayList<String>(), new ArrayList<String>());

        SharedPreferences sharedPref = getContext().getSharedPreferences(USER_DATA_PREFERENCES_TAG, Context.MODE_PRIVATE);
        user.setUsername(sharedPref.getString(USERNAME_SHARED_PREF_TAG, "user"));
        String currentUserId = sharedPref.getString(USER_ID_SHARED_PREF_TAG, "id");

        // get the UI elements
        TextView usernameTextView = view.findViewById(R.id.username);
        Button numberOfFollowersButton = view.findViewById(R.id.number_followers);
        Button numberOfFollowingButton = view.findViewById(R.id.number_following);
        FloatingActionButton logoutButton = view.findViewById(R.id.logout_button);
        _userImageView = view.findViewById(R.id.profile_picture);

        // set the UI elements
        UserDatabaseHelper userDatabaseHelper = new UserDatabaseHelper(currentUserId,
                                                                       numberOfFollowersButton,
                                                                       numberOfFollowingButton);
        usernameTextView.setText("@" + user.getUsername());
        userDatabaseHelper.setFollowingCountOfUser();
        userDatabaseHelper.setFollowerCountOfUser();

        // Fetch profile picture from database
        ImageDatabaseHelper imageDatabaseHelper = new ImageDatabaseHelper();
        imageDatabaseHelper.fetchImagesFromDB(_userImageView, imageDatabaseHelper.getUserStorageReference(currentUserId));
        setClickListenerForFollowersButton(numberOfFollowersButton);
        setClickListenerForFollowingButton(numberOfFollowingButton);
        return view;
    }

    private void setClickListenerForFollowingButton(Button numberOfFollowing) {
        // TODO: extract click listener
        numberOfFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("FollowType", "Following");
                // navigate to following fragment
                navigateToFragmentWithAction(R.id.action_navigation_notifications_to_followListFragment, bundle);
            }
        });
    }

    private void setClickListenerForFollowersButton(Button numberOfFollowers) {
        // TODO: extract click listener
        numberOfFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("FollowType", "Followers");
                // navigate to followers fragment
                navigateToFragmentWithAction(R.id.action_navigation_notifications_to_followListFragment, bundle);
            }
        });
    }

    private void setClickListenerForLogoutButton(FloatingActionButton logoutButton) {
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                navigateToFragmentWithAction(R.id.action_logout);
            }
        });
    }

    private void navigateToFragmentWithAction(int actionId) {
        NavController controller = NavHostFragment.findNavController(_fragment);
        controller.navigate(actionId);
    }

    private void navigateToFragmentWithAction(int actionId, Bundle bundle) {
        NavController controller = NavHostFragment.findNavController(_fragment);
        controller.navigate(actionId, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}