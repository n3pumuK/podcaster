package de.exercicse.jrossbach.podcast;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import de.exercicse.jrossbach.podcast.service.WidgetPlayerService;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link PodcastPlayerWidgetConfigureActivity PodcastPlayerWidgetConfigureActivity}
 */
public class PodcastPlayerWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String songTitle) {

        CharSequence widgetText = PodcastPlayerWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.podcast_player_widget);
        views.setTextViewText(R.id.appwidget_text, songTitle);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        Intent playerStartIntent = new Intent(context, WidgetPlayerService.class);
        playerStartIntent.setAction(WidgetPlayerService.ACTION_START_PLAYER);
        PendingIntent playerStartPendingIntent = PendingIntent.getService(
                context,
                0,
                playerStartIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_play_btn, playerStartPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, "songTitle");
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            PodcastPlayerWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void updatePlayerWidgets(Context context, AppWidgetManager appWidgetManager, String songTitle, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, songTitle);
        }
    }
}

