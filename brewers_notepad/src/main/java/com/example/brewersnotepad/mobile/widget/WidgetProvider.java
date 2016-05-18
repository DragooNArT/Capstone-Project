package com.example.brewersnotepad.mobile.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.fragments.ViewRecipeSecondary;

/**
 * Created by xnml on 18.5.2016 г..
 */
public class WidgetProvider extends AppWidgetProvider {




    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int widgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            Intent svcIntent = new Intent(context,WidgetService.class);

            Intent clickIntent = new Intent(context, ViewRecipeSecondary.class);
            PendingIntent pending = PendingIntent.getActivity(context, 0,
                    clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.widget_list,pending);
            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            views.setRemoteAdapter(R.id.widget_list,
                    svcIntent);
            //setting an empty view in case of no data
//            views.setEmptyView(R.layout.widget_layout, R.id.empty_view);
            appWidgetManager.updateAppWidget(widgetId,views);
            Toast.makeText(context, "widget added", Toast.LENGTH_SHORT).show();
        }
    }
}
