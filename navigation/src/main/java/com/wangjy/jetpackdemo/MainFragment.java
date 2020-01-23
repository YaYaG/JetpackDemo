package com.wangjy.jetpackdemo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDeepLinkBuilder;

/**
 * 姓名: 王梦阳
 * 时间： 2020-01-23
 * 描述：
 */
public class MainFragment extends Fragment {
    MainActivity mMainActivity;
    private static final String CHANNEL_ID = "1";
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mMainActivity = (MainActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.main, container, false);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.blank).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivity.goBlank();
            }
        });

        view.findViewById(R.id.message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivity.goMessage();
            }
        });

        view.findViewById(R.id.notify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });
    }

    /**
     * 向通知栏发送一个通知
     * */
    private void sendNotification()
    {
        if(getActivity() == null)
        {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "ChannelName", importance);
            channel.setDescription("description");
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("DeepLinkDemo")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(getPendingIntent())//设置PendingIntent
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());
        notificationManager.notify(1, builder.build());
    }

    /**
     * 通过PendingIntent设置，当通知被点击后需要跳转到哪个destination，以及传递的参数
     * */
    private PendingIntent getPendingIntent()
    {
        if(getActivity() != null)
        {
            Bundle bundle = new Bundle();
            bundle.putInt("age",11);
            return new NavDeepLinkBuilder(getActivity())
                    .setGraph(R.navigation.nav_graph)
                    .setDestination(R.id.blankFragment)
                    .setArguments(bundle)
                    .createPendingIntent();
        }
        return null;
    }
}
