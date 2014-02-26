package com.sakin.sohojshoncoi;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayerView;
import com.sakin.sohojshoncoi.custom.VideoElement;

public class YouTubeFullScreen extends YouTubeFailureRecoveryFragment implements
												View.OnClickListener,
												CompoundButton.OnCheckedChangeListener,
												YouTubePlayer.OnFullscreenListener {
	
	private static final int PORTRAIT_ORIENTATION = Build.VERSION.SDK_INT < 9
		      ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
		      : ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;

	private LinearLayout baseLayout;
	private YouTubePlayerView playerView;
	private YouTubePlayer player;
//	private Button fullscreenButton;
	private TextView videoTitle;
	private TextView videoDetails;
//	private CompoundButton checkbox;
	private View otherViews;
	private VideoElement videoElement;

	private boolean fullscreen;

	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.youtube_full_screen);
		
	    Utils.setActionBarTitle(this, "mnR mÂq");
	    getActionBar().setDisplayHomeAsUpEnabled(true);
	    getActionBar().setHomeButtonEnabled(true);
	    
	    baseLayout = (LinearLayout) findViewById(R.id.youtube_layout);
	    playerView = (YouTubePlayerView) findViewById(R.id.player);
//	    fullscreenButton = (Button) findViewById(R.id.fullscreen_button);
//	    checkbox = (CompoundButton) findViewById(R.id.landscape_fullscreen_checkbox);
	    otherViews = findViewById(R.id.other_views);
	    videoTitle = (TextView) findViewById(R.id.video_title);
	    videoDetails = (TextView) findViewById(R.id.video_details);

//	    checkbox.setOnCheckedChangeListener(this);
	    // You can use your own button to switch to fullscreen too
//	    fullscreenButton.setOnClickListener(this);
	    
	    playerView.initialize(Utils.DEVELOPER_KEY, this);

	    doLayout();
	    
	    videoElement = (VideoElement) getIntent().getSerializableExtra(Utils.VIDEO_ELEMENT_ID);
	    Utils.print("from new activity: " + videoElement.getVideoDescription());
	    videoTitle.setText(videoElement.getVideoTitle());
	    videoTitle.setTypeface(Utils.banglaTypeFace);
	    videoDetails.setText(videoElement.getVideoDescription());
	    videoDetails.setTypeface(Utils.banglaTypeFace);
	}
	
	@Override
	public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
	      boolean wasRestored) {
		this.player = player;
	    setControlsEnabled();
	    // Specify that we want to handle fullscreen behavior ourselves.
	    player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
	    player.setOnFullscreenListener(this);
	    if (!wasRestored) {
	    	player.cueVideo(videoElement.getVideoUrl().substring(videoElement.getVideoUrl().indexOf('=') + 1));
	    	PlayerStyle style = PlayerStyle.MINIMAL;
		    player.setPlayerStyle(style);
	    }
	}

	@Override
	protected YouTubePlayer.Provider getYouTubePlayerProvider() {
		return playerView;
	}

	@Override
	public void onClick(View v) {
	    player.setFullscreen(!fullscreen);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	    int controlFlags = player.getFullscreenControlFlags();
	    if (isChecked) {
	      // If you use the FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE, your activity's normal UI
	      // should never be laid out in landscape mode (since the video will be fullscreen whenever the
	      // activity is in landscape orientation). Therefore you should set the activity's requested
	      // orientation to portrait. Typically you would do this in your AndroidManifest.xml, we do it
	      // programmatically here since this activity demos fullscreen behavior both with and without
	      // this flag).
	      setRequestedOrientation(PORTRAIT_ORIENTATION);
	      controlFlags |= YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE;
	    } else {
	      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
	      controlFlags &= ~YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE;
	    }
	    player.setFullscreenControlFlags(controlFlags);
	  }

	private void doLayout() {
	    LinearLayout.LayoutParams playerParams =
	        (LinearLayout.LayoutParams) playerView.getLayoutParams();
	    if (fullscreen) {
	      // When in fullscreen, the visibility of all other views than the player should be set to
	      // GONE and the player should be laid out across the whole screen.
	      playerParams.width = LayoutParams.MATCH_PARENT;
	      playerParams.height = LayoutParams.MATCH_PARENT;

	      otherViews.setVisibility(View.GONE);
	    } else {
	      // This layout is up to you - this is just a simple example (vertically stacked boxes in
	      // portrait, horizontally stacked in landscape).
	      otherViews.setVisibility(View.VISIBLE);
	      ViewGroup.LayoutParams otherViewsParams = otherViews.getLayoutParams();
	      if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
	        playerParams.width = otherViewsParams.width = 0;
	        playerParams.height = WRAP_CONTENT;
	        otherViewsParams.height = MATCH_PARENT;
	        playerParams.weight = 1;
	        baseLayout.setOrientation(LinearLayout.HORIZONTAL);
	      } else {
	        playerParams.width = otherViewsParams.width = MATCH_PARENT;
	        playerParams.height = WRAP_CONTENT;
	        playerParams.weight = 0;
	        otherViewsParams.height = 0;
	        baseLayout.setOrientation(LinearLayout.VERTICAL);
	      }
	      setControlsEnabled();
	    }
	}
	
	private void setControlsEnabled() {
//		checkbox.setEnabled(player != null
//				&& getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
//		fullscreenButton.setEnabled(player != null);
	}

	@Override
	public void onFullscreen(boolean isFullscreen) {
		fullscreen = isFullscreen;
		doLayout();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		doLayout();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        NavUtils.navigateUpFromSameTask(this);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}

}
