package com.naroky.cookingthai;

import android.app.Activity;
import android.widget.LinearLayout;
import com.google.ads.*;

public class bannerLib {
	 private AdView adView;

	 public void createBanner(Activity context)
	    {
		  // Create an ad.
		    adView = new AdView(context, AdSize.BANNER, "a1517225bcd9db8");

		    // Add the AdView to the view hierarchy. The view will have no size
		    // until the ad is loaded.
		    LinearLayout layout = (LinearLayout) context.findViewById(R.id.bannerLayout);
		    layout.addView(adView);

		    // Create an ad request. Check logcat output for the hashed device ID to
		    // get test ads on a physical device.
		    AdRequest adRequest = new AdRequest();
		   
		    // Start loading the ad in the background.
		    adView.loadAd(adRequest);  	
	    }		

	  public void Destroy() {
		    // Destroy the AdView.
		    if (adView != null) {
		      adView.destroy();
		    }
	  }
}
