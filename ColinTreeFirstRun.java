/**
 * @author ColinTree (colinycl123@gmail.com)
 * @original appinventor.components.src.com.google.appinventor.components.runtime.TinyDB
 */

package cn.colintree.aix;

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

public class ColinTreeFirstRun extends AndroidNonvisibleComponent
implements Component {
    public static final int VERSION = 1;
    private ComponentContainer container;
    private Context context;
    private SharedPreferences sharedPreferences;
    private static final String LOG_TAG = "ColinTreeFirstRun";
    public ColinTreeFirstRun(ComponentContainer container) {
        super(container.$form());
        this.container = container;
        context = (Context) container.$context();
        Log.d(LOG_TAG, "FirstRun Created" );
        sharedPreferences = context.getSharedPreferences("ColinTreeFirstRun", Context.MODE_PRIVATE);
    }
    
    final String tag="ColinTreeFirstRun";
    final Object valueToStore="false";
    
    @SimpleFunction(description = "return if it is first time to run, if it is true, set a flag.")
    public boolean IsFirstRun() {
    	if (GetValue(tag,"FirstRun!!!!!")=="FirstRun!!!!!"||GetValue(tag,"FirstRun!!!!!")=="false"){
    		StoreValue(tag,valueToStore);
    		return true;
    	}else{
    		return false;
    	}
    }
    @SimpleFunction(description = "")
    public void ClearFirstRunFlag() {
    	final SharedPreferences.Editor sharedPrefsEditor = sharedPreferences.edit();
    	sharedPrefsEditor.clear();
    	sharedPrefsEditor.commit();
    }
    
    /**
    * Store the given value under the given tag.  The storage persists on the
    * phone when the app is restarted.
    *
    * @param tag The tag to use
    * @param valueToStore The value to store. Can be any type of value (e.g.
    * number, text, boolean or list).
    */
    public void StoreValue(final String tag, final Object valueToStore) {
    	final SharedPreferences.Editor sharedPrefsEditor = sharedPreferences.edit();
    	try {
    		sharedPrefsEditor.putString(tag, JsonUtil.getJsonRepresentation(valueToStore));
    		sharedPrefsEditor.commit();
    	} catch (JSONException e) {
    		throw new YailRuntimeError("Value failed to convert to JSON.", "JSON Creation Error.");
    	}
    }
    /**
    * Retrieve the value stored under the given tag.  If there's no such tag, then return valueIfTagNotThere.
    *
    * @param tag The tag to use
    * @param valueIfTagNotThere The value returned if tag in not in TinyDB
    * @return The value stored under the tag. Can be any type of value (e.g.
    * number, text, boolean or list).
    */
    public Object GetValue(final String tag, final Object valueIfTagNotThere) {
    	try {
    		String value = sharedPreferences.getString(tag, "");
    		// If there's no entry with tag as a key then return the empty string.
    		//    was  return (value.length() == 0) ? "" : JsonUtil.getObjectFromJson(value);
    		return (value.length() == 0) ? valueIfTagNotThere : JsonUtil.getObjectFromJson(value);
    	} catch (JSONException e) {
    		throw new YailRuntimeError("Value failed to convert from JSON.", "JSON Creation Error.");
    	}
    }
}
