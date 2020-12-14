package com.example.kozlovskiy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Context context;
    LayoutInflater layoutInflater;
    public List<User> users = new ArrayList<>();
    UserListAdapter userListAdapter;
    FrameLayout UserPanel;
    private TextView NameTextView, StateTextView, AgeTextView;
    int positionActiveUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
        AddUsersInList();
        SetUserInfo();
    }

    private void AddUsersInList() {
        users.add(new User("Егор", "Он устал", 21, 1));
        users.add(new User("Егор", "Он устал", 21, 2));
        users.add(new User("Егор", "Он устал", 21, 0));

    }

    private void Init() {
        NameTextView = findViewById(R.id.NameTextView);
        StateTextView = findViewById(R.id.StateTextView);
        AgeTextView = findViewById(R.id.AgeTextView);

    }
    private void SetUserInfo(){
        NameTextView.setText(activeUser.getName());
        StateTextView.setText(activeUser.getState());
        AgeTextView.setText(String.valueOf(activeUser.getAge()));

    }
    public void Save(View view){
        activeUser.setName(NameTextView.getText().toString());
        activeUser.setState(StateTextView.getText().toString());
        activeUser.setAge(Integer.parseInt(AgeTextView.getText().toString()));

        MainActivity.UpdateListAndUserPanel(activeUser);
        finish();
    }

    public void BackToList(View view) {
        UserVisibility(false);
    }

    private void UserVisibility(boolean visible) {
        if (visible)
            UserPanel.setVisibility(View.VISIBLE);
        else
            UserPanel.setVisibility(View.GONE);
    }

    public void EditUser(View view) {
    GoToUserProfile(positionActriveUser);
    }

    private class UserListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return users.size();
        }

        @Override
        public User getItem(int position) {
            return users.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View currentView, ViewGroup parent) {
            User currentUser = getItem(position);
            currentView = layoutInflater.inflate(R.layout.item_user, parent, false);
            TextView nameView = currentView.findViewById(R.id.NameTextView);
            TextView stateView = currentView.findViewById(R.id.StateTextView);
            FrameLayout StateRound = currentView.findViewById(R.id.StateRound);
            currentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positionActriveUser = position;
                    InitPanel(getItem(position));
                    UserVisibility(true);
                }
            });
            switch (currentUser.getStateSignal()) {
                case 0:
                    StateRound.setBackgroundResource(R.drawable.back_offline);
                    break;
                case 1:
                    StateRound.setBackgroundResource(R.drawable.back_online);
                    break;
                case 2:
                    StateRound.setBackgroundResource(R.drawable.back_departed);
                    break;

            }
            return currentView;
        }

        public static void UpdateListAndUserPanel(User user){
            userListAdapter.notifyDataSetChanged();
            InitPanel(user);
        }
        private void InitPanel(User item) {
            NameTextView.setText(item.getName());
            StateTextView.setText(item.getState());
            AgeTextView.setText(item.getAge());
        }
    }
    public void GoToUserProfile()
    {
        Intent intent = new Intent(context,UserActivity.class);
        startActivity(intent);
    }
}

