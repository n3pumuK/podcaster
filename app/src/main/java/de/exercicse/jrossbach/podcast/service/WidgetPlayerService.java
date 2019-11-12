package de.exercicse.jrossbach.podcast.service;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import de.exercicse.jrossbach.podcast.PodcastPlayerWidget;

public class WidgetPlayerService extends IntentService {

    public static final String ACTION_START_PLAYER = "de.exercicse.jrossbach.podcast.action.start_player";
    public static final String ACTION_UPDATE_WIDGET = "de.exercicse.jrossbach.podcast.action.update_widget";

    public static final String INTENT_SONG_TITLE_EXTRA = "intent_song_title_extra";

    private String currentTitle = "";

    public WidgetPlayerService(String name) {
        super(name);

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_START_PLAYER.equals(action)) {
                handleActionPlay();
            }
            else if(ACTION_UPDATE_WIDGET.equals(action)) {
                handleUpdateWidgetAction();
            }
        }
    }

    private void handleUpdateWidgetAction() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, PodcastPlayerWidget.class));

        PodcastPlayerWidget.updatePlayerWidgets(this, appWidgetManager, currentTitle, appWidgetIds);
    }


    private void handleActionPlay() {
        currentTitle = "new Title";
    }

    public static void startActionPlay(Context context, String song) {
        Intent intent = new Intent(context, WidgetPlayerService.class);
        intent.putExtra(INTENT_SONG_TITLE_EXTRA, song);
        intent.setAction(ACTION_START_PLAYER);
        context.startService(intent);
    }

    public static void startActionUpdateWidget(Context context, String song) {
        Intent intent = new Intent(context, WidgetPlayerService.class);
        intent.putExtra(INTENT_SONG_TITLE_EXTRA, song);
        intent.setAction(ACTION_UPDATE_WIDGET);
        context.startService(intent);
    }
}
