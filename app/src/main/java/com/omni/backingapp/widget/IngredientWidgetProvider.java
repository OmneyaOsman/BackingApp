package com.omni.backingapp.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.omni.backingapp.R;
import com.omni.backingapp.db.IngredientEntry;
import com.omni.backingapp.db.RecipeEntry;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidgetProvider extends AppWidgetProvider {


 public    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
     Realm realm = Realm.getDefaultInstance();


        SharedPreferences sp = context.getSharedPreferences("recipe", Activity.MODE_PRIVATE);
        int myIntValue = sp.getInt("recipeId", -1);

     RealmList<IngredientEntry>  ingredientEntries =
             realm.where(RecipeEntry.class).equalTo("id" ,myIntValue).findFirst().getIngredients();
     String recipeName = realm.where(RecipeEntry.class).equalTo("id" ,myIntValue).findFirst().getName();

     RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget_provider);
     String values = "";
     if (ingredientEntries != null && ingredientEntries.size() != 0)
     {
         for (IngredientEntry ingredient: ingredientEntries) {
             String ingredientN = ingredient.getIngredient();
             String measure = ingredient.getMeasure();
             Float quantity = ingredient.getQuantity();
             values = values + quantity+" "+measure+" "+ingredientN+"\n";
         }
     }

     CharSequence widgetText = context.getString(R.string.ingredients).concat(" Of " ).concat(recipeName);

        // Construct the RemoteViews object

        views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setTextViewText(R.id.appwidget_ingedients, values);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//Start the intent service update widget action, the service takes care of updating the widgets UI
//        UpdateWidgetService.startActionUpdateWidgets(context);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
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
}

