package com.casualgames.upperbound;

import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.casualgames.upperbound.UpperBound;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class AndroidLauncher extends AndroidApplication implements AdsController{

	private static final String INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-4067772952315996/5332472911";
	InterstitialAd interstitialAd;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		setupAds();

		config.numSamples = 2;
		initialize(new UpperBound(this), config);
	}

	public void setupAds() {
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(INTERSTITIAL_AD_UNIT_ID);

		AdRequest.Builder builder = new AdRequest.Builder();
		AdRequest ad = builder.build();
		interstitialAd.loadAd(ad);
	}

	@Override
	public void showInterstitialAd(final Runnable then) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
//				if (then != null) {
					interstitialAd.setAdListener(new AdListener() {
						@Override
						public void onAdClosed() {
							Gdx.app.postRunnable(then);
							AdRequest.Builder builder = new AdRequest.Builder();
							AdRequest ad = builder.build();
							interstitialAd.loadAd(ad);
						}
					});
//				}
				interstitialAd.show();
			}
		});
	}

	@Override
	public void onAdClosed() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {

//				interstitialAd.setAdListener(new AdListener() {
//					@Override
//					public void onAdClosed() {
//						Gdx.app.postRunnable(then);
						AdRequest.Builder builder = new AdRequest.Builder();
						AdRequest ad = builder.build();
						interstitialAd.loadAd(ad);
//					}
//				});

			}
		});
	}
}
