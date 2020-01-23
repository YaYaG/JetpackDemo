package com.wangjy.jetpackdemo;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener {

    private NavController mNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
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
