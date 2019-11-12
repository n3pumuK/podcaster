package de.exercicse.jrossbach.podcast;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.media.session.MediaButtonReceiver;

import static android.content.Intent.ACTION_VIEW;


public class NotificationHelper extends ContextWrapper {

    private NotificationManager notificationManager;

    //Set the channel’s ID//
    public static final String CHANNEL_ONE_ID = "de.exercicse.jrossbach.podcast.podcaster.ONE";

    //Set the channel’s user-visible name//
    public static final String CHANNEL_ONE_NAME = "Channel One";

    public static final String CHANNEL_TWO_ID = "de.exercicse.jrossbach.podcast.podcaster.TWO";
    public static final String CHANNEL_TWO_NAME = "Channel Two";


    public NotificationHelper(Context base) {
        super(base);
        createChannels();
    }

    private void createChannels() {

        NotificationChannel channelOne = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channelOne = new NotificationChannel(CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME, notificationManager.IMPORTANCE_HIGH);
            channelOne.enableLights(true);
            channelOne.setLightColor(Color.RED);
            channelOne.setShowBadge(true);
            channelOne.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getNotificationManager().createNotificationChannel(channelOne);

            NotificationChannel channelTwo = new NotificationChannel(CHANNEL_TWO_ID,
                    CHANNEL_TWO_NAME, notificationManager.IMPORTANCE_DEFAULT);
            channelTwo.enableLights(false);
            channelTwo.enableVibration(true);
            channelTwo.setLightColor(Color.RED);
            channelTwo.setShowBadge(false);
            getNotificationManager().createNotificationChannel(channelTwo);
        }

    }

    //Send your notifications to the NotificationManager system service//
    private NotificationManager getNotificationManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    public void notify(int id, Notification.Builder notification) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getNotificationManager().notify(id, notification.build());
        }
    }

    //Create the notification that’ll be posted to Channel One//
    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getNotificationOnChannelOne(String title, String body) {
        return new Notification.Builder(getApplicationContext(), CHANNEL_ONE_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(getPendingIntent(getBaseContext(), 1))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true);
    }

    //Create the notification that’ll be posted to Channel Two//
    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getNotificationOnChannelTwo(String title, String body) {
        return new Notification.Builder(getApplicationContext(), CHANNEL_TWO_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(getPendingIntent(getBaseContext(), 1))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true);
    }

    public Notification buildForegroundNotification(MediaSessionCompat mediaSessionCompat, Context context) {

        // Get the session's metadata
        MediaControllerCompat controller = mediaSessionCompat.getController();
        MediaMetadataCompat mediaMetadata = controller.getMetadata();
        //   MediaDescriptionCompat description = mediaMetadata.getDescription();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());

        builder
                // Add the metadata for the currently playing track
                .setContentTitle("title")
                .setContentText("subtitle")
                .setSubText("descrption")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setChannelId("4565")

                // Enable launching the player by clicking the notification
                .setContentIntent(getPendingIntent(context, 1))

                // Stop the service when the notification is swiped away
                .setDeleteIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(this,
                        PlaybackStateCompat.ACTION_STOP))

                // Make the transport controls visible on the lockscreen
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

                // Add an app icon and set its accent color
                // Be careful about the color
                .setSmallIcon(R.drawable.ic_play_arrow_24dp)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))

                // Add a pause button
                .addAction(new NotificationCompat.Action(
                        R.drawable.ic_pause_24dp, getString(R.string.pause),
                        MediaButtonReceiver.buildMediaButtonPendingIntent(this,
                                PlaybackStateCompat.ACTION_PLAY_PAUSE)))

                // Take advantage of MediaStyle features
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(mediaSessionCompat.getSessionToken())
                        .setShowActionsInCompactView(0)

                        // Add a cancel button
                        .setShowCancelButton(true)
                        .setCancelButtonIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(this,
                                PlaybackStateCompat.ACTION_STOP)));
        return builder.build();
    }

    private static PendingIntent getPendingIntent(Context context, int requestCode) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setAction(ACTION_VIEW);
        intent.setPackage(BuildConfig.APPLICATION_ID);

        return PendingIntent.getActivity(context, requestCode,intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
