package com.uninorte.crystalball;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uninorte.crystalball.ShakeDetector.OnShakeListener;

public class MainActivity extends Activity {

	private CrystallBall mCrystallBall = new CrystallBall();
	private TextView mAnswerLabel;
	private ImageView mCrystalBallImage;
	private SensorManager mSensorManager;
	private Sensor mAccelerometor;
	private ShakeDetector mShakeDetector;
	public static final String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mAnswerLabel = (TextView) findViewById(R.id.textView1);
		mCrystalBallImage = (ImageView) findViewById(R.id.imageView1);
		
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mAccelerometor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mShakeDetector = new ShakeDetector(new OnShakeListener() {
			
			@Override
			public void onShake() {
				getNewAnswer();
				
			}
		});
		
		Toast.makeText(this, "The Activity was created", Toast.LENGTH_LONG).show();
		
		Log.d(TAG,"The Activity was created");
	}
	
    @Override
    protected void onPause() {
    	super.onPause();
       	mSensorManager.unregisterListener(mShakeDetector);
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
     	mSensorManager.registerListener(mShakeDetector, mAccelerometor, SensorManager.SENSOR_DELAY_UI);
        
   }

	private void animateCrystalBall() {

		mCrystalBallImage.setImageResource(R.drawable.ball_animation);
		AnimationDrawable ballAnimation = (AnimationDrawable) mCrystalBallImage
				.getDrawable();
		if (ballAnimation.isRunning()) {
			ballAnimation.stop();
		}
		ballAnimation.start();
	}

	private void animateAnswer() {
		AlphaAnimation fadeAnimation = new AlphaAnimation(0, 1);
		fadeAnimation.setDuration(800); // 1.5 secs
		fadeAnimation.setFillAfter(true);
		mAnswerLabel.setAnimation(fadeAnimation);
	}
	
	private void playSound(){
		MediaPlayer player = MediaPlayer.create(this, R.raw.crystal_ball);
		player.start();
		player.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
				
			}
		});
	}
	
	private void scaleAnimation(){
		Animation translateAnim = new TranslateAnimation(0, 320, 0, 0);
		translateAnim.setDuration(500); 
		mCrystalBallImage.startAnimation(translateAnim);
		
		translateAnim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				Animation translateAnim = new TranslateAnimation(320, -320, 0, 0);
				translateAnim.setDuration(1000); 
				mCrystalBallImage.startAnimation(translateAnim);
				translateAnim.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						Animation translateAnim = new TranslateAnimation(-320, 0, 0, 0);
						translateAnim.setDuration(500); 
						mCrystalBallImage.startAnimation(translateAnim);
			
						
					}
				});
				
			}
		});
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void getNewAnswer() {
		String answer = mCrystallBall.getAnswer();
		mAnswerLabel.setText(answer);
		animateCrystalBall();
		animateAnswer();
		playSound();
		scaleAnimation();
	}
}
