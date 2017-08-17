package cn.colintree.aix.ColinTreeFirstRun;

import android.content.Context;
import android.util.Log;
import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.*;
import com.google.appinventor.components.runtime.util.*;

import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import android.content.SharedPreferences;
import org.json.JSONException;

@DesignerComponent(version = ColinTreeFirstRun.VERSION,
    description = "by ColinTree at http://aix.colintree.cn/",
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    iconName = "images/extension.png")

@SimpleObject(external = true)

public class ColinTreeFirstRun extends AndroidNonvisibleComponent implements Component {
    public static final int VERSION = 1;
    private ComponentContainer container;
    private Context context;
    private SharedPreferences sharedPreferences;
    private static final String LOG_TAG = "ColinTreeFirstRun";
    private FirstRun firstRun;
    public ColinTreeFirstRun(ComponentContainer container) {
        super(container.$form());
        this.container = container;
        context = (Context) container.$context();
        Log.d(LOG_TAG, "FirstRun Created" );
        firstRun=new FirstRun(container);
    }
    
    @SimpleFunction(description = "return if it is first time to run, if it is true, set a flag.")
    public boolean IsFirstRun() {
        return firstRun.IsFirstRun();
    }
    @SimpleFunction(description = "")
    public void ClearFirstRunFlag() {
        firstRun.ClearFirstRunFlag();
    }

    public class FirstRun{
        final String tag="ColinTreeFirstRun";
        final Object valueToStore="false";

        private SharedPreferences sharedPreferences;
        
        public FirstRun(ComponentContainer container) {
            sharedPreferences = context.getSharedPreferences("ColinTreeFirstRun", Context.MODE_PRIVATE);
        }
        
        public boolean IsFirstRun() {
            if (GetValue(tag,"FirstRun!!!!!")=="FirstRun!!!!!"||GetValue(tag,"FirstRun!!!!!")==valueToStore){
                StoreValue(tag,valueToStore);
                return true;
            }
            return false;
        }
        public void ClearFirstRunFlag() {
            final SharedPreferences.Editor sharedPrefsEditor = sharedPreferences.edit();
            sharedPrefsEditor.clear();
            sharedPrefsEditor.commit();
        }
        
        private void StoreValue(final String tag, final Object valueToStore) {
            final SharedPreferences.Editor sharedPrefsEditor = sharedPreferences.edit();
            try {
                sharedPrefsEditor.putString(tag, JsonUtil.getJsonRepresentation(valueToStore));
                sharedPrefsEditor.commit();
            } catch (JSONException e) {
                throw new YailRuntimeError("Value failed to convert to JSON.", "JSON Creation Error.");
            }
        }
        
        private Object GetValue(final String tag, final Object valueIfTagNotThere) {
            try {
                String value = sharedPreferences.getString(tag, "");
                return (value.length() == 0) ? valueIfTagNotThere : JsonUtil.getObjectFromJson(value);
            } catch (JSONException e) {
                throw new YailRuntimeError("Value failed to convert from JSON.", "JSON Creation Error.");
            }
        }
        
    }
}