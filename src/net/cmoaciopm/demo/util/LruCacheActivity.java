package net.cmoaciopm.demo.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.cmoaciopm.demo.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.util.Log;

public class LruCacheActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config_change);
		
		demoLruCache();
	}
	
	private String[] cacheKeys = new String[]{
			"bitmap0",
			"bitmap1",
			"bitmap2",
			"bitmap3",
			"bitmap4",
	};
	
	private int insertCount = 20;
	private int useCount = 100;
	private void demoLruCache() {
		LruCache<String, String> cache = new LruCache<String, String>(cacheKeys.length) {
			@Override
			public String create(String key) {
				Log.d("", "Key " + key + " miss");
				return "missing value";
			}
			
			@Override
			public void entryRemoved(boolean evicted, String key, String oldValue, String newValue) {
				Log.d("", "evicted=" + evicted + ", key=" + key + ", oldValue=" + oldValue + ", newValue=" + newValue);
			}
		};
		
		for(int i=0; i<cacheKeys.length; i++) {
			String content = cacheKeys[i]+" content";
			Log.d("", "Key=" + i + " inserted, value=" + content);
			cache.put(cacheKeys[i], content);
		}
		
		for(int i=0; i<100; i++) {
			cache.get(cacheKeys[0]);
			cache.get(cacheKeys[1]);
		}
		cache.get(cacheKeys[2]);
		for(int i=0; i<100; i++) {
			cache.get(cacheKeys[3]);
			cache.get(cacheKeys[4]);
		}
		
		Map<String, String> snapShot = cache.snapshot();
		Set<String> keys = snapShot.keySet();
		Iterator<String> it = keys.iterator();
		while(it.hasNext()) {
			Log.d("", "Key = " + it.next());
		}
		
		cache.put("bitmapExceed", "bitmapExceed content");
		cache.put("bitmapExceed2", "bitmapExceed content");
		cache.put("bitmapExceed3", "bitmapExceed content");
		cache.put("bitmapExceed4", "bitmapExceed content");
		cache.put("bitmapExceed5", "bitmapExceed content");
		
	}
	
	private int random(int max) {
		return (int)(Math.random()*max);
	}
}
