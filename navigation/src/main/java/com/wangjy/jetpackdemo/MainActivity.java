package com.wangjy.jetpackdemo;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener {

    private NavController mNavController;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupWithNavController(mToolbar,mNavController);
    }

    public void goMessage(){
        NavDirections mainFragmentToMessageFragment = MainFragmentDirections.actionMainFragmentToMessageFragment();
        mNavController.navigate(mainFragmentToMessageFragment);
    }

    public void goBlank(){
        NavDirections navDirections = MainFragmentDirections.actionMainFragmentToBlankFragment();
        Bundle bundle = new MainFragmentArgs.Builder().setAge(123).build().toBundle();
        mNavController.navigate(navDirections.getActionId(),bundle);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
