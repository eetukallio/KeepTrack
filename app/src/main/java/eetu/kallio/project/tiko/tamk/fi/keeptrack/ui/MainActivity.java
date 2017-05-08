package eetu.kallio.project.tiko.tamk.fi.keeptrack.ui;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.wang.avi.AVLoadingIndicatorView;

import eetu.kallio.project.tiko.tamk.fi.keeptrack.R;
import eetu.kallio.project.tiko.tamk.fi.keeptrack.resources.WorkEvent;
import eetu.kallio.project.tiko.tamk.fi.keeptrack.http.EventPostTask;
import eetu.kallio.project.tiko.tamk.fi.keeptrack.receivers.EventReceiver;
import eetu.kallio.project.tiko.tamk.fi.keeptrack.services.EventService;

/**
 * Main activity
 *
 * @author Eetu Kallio
 * @version 4.0
 * @since 1.0
 */
public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private boolean EVENT_ON = false;
    private CoordinatorLayout coordinatorLayout;
    private EventReceiver receiver;
    private Button startButton;
    private AVLoadingIndicatorView avi;
    private ConstraintLayout mainLayout;
    private ConstraintLayout signInLayout;
    private SignInButton signInButton;
    private TextView userNameView;
    private ImageView profilePic;
    private Toolbar toolbar;
    private String img_url;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 666;
    private String userId;
    private LocalBroadcastManager manager;
    private Animation pulse;

    /**
     * Called when an activity instance is created. Initializes most attributes.
     *
     * @param savedInstanceState Carries over data from previous use.
     */
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.myCoordinatorLayout);
        startButton = (Button) findViewById(R.id.startTrackingButton);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        mainLayout = (ConstraintLayout) findViewById(R.id.mainLayout);
        signInLayout = (ConstraintLayout) findViewById(R.id.signInLayout);
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setColorScheme(SignInButton.COLOR_DARK);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                singIn();
            }
        });
        userNameView = (TextView) findViewById(R.id.userName);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        profilePic = (ImageView) findViewById(R.id.profile_pic);
        pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);
        setSupportActionBar(toolbar);
        avi.hide();
        mainLayout.setVisibility(View.GONE);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestId().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();
    }

    /**
     * Called when resuming from paused state.
     */
    @Override
    protected void onResume () {
        super.onResume();

        IntentFilter filter = new IntentFilter("fi.tamk.tiko.project.kallio.eetu.event");
        manager = LocalBroadcastManager.getInstance(this);
        receiver = new EventReceiver();
        manager.registerReceiver(receiver, filter);
    }

    /**
     * Called when moving into paused state.
     */
    @Override
    protected void onPause () {
        super.onPause();
        manager.unregisterReceiver(receiver);
    }

    /**
     * Used to start a new or stop a running work event.
     *
     * @param v The view which initiated method via onClick.
     */
    public void startEvent(View v) {

        if ( !EVENT_ON ) {

            EVENT_ON = true;
            Intent intent;
            intent = new Intent(this, EventService.class);
            startService(intent);
            System.out.println("Event started");
            Snackbar.make(coordinatorLayout, "Event started", Snackbar.LENGTH_SHORT).show();
            startButton.setText("STOP TRACKING");
            avi.smoothToShow();
            startButton.startAnimation(pulse);
        } else {

            WorkEvent event;
            stopService(new Intent(this, EventService.class));
            event = receiver.getEvent();
            event.setUser(userId);
            System.out.println("Event ended");
            startButton.setText("START TRACKING");
            String endMsg = "Event ended. Duration: " + event.getDurationSeconds();
            Snackbar.make(coordinatorLayout, endMsg, Snackbar.LENGTH_SHORT).show();
            stopService(new Intent(this, EventService.class));
            EVENT_ON = false;
            postEvent(event);
            avi.smoothToHide();
            startButton.startAnimation(pulse);
        }
    }

    /**
     * Used to post a new event into the database.
     *
     * @param event WorkEvent to be posted to the database.
     */
    public void  postEvent(WorkEvent event) {

        new EventPostTask().execute(event);
    }

    /**
     * Used to switch to the EventListActivity.
     */
    public void viewEvents(View view) {

        startActivity(new Intent(this, EventListActivity.class).putExtra("user",userId));
    }

    /**
     * Used to initiate Google Sign-In.
     */
    public void singIn () {

        Log.d("signIn", "got here");
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQ_CODE);
    }

    /**
     * Used to initiate a log out of a Google account.
     */
    public void signOut() {

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult (@NonNull Status status) {
                updateUI(false);
            }
        });
    }

    /**
     * Used to handle the results of a Google Sign-In. Modifies and inflates
     * the necessary UI elements.
     *
     * @param result The result of a Google Sign-In.
     */
    public void handleResult(GoogleSignInResult result) {

        if ( result.isSuccess() ) {
            GoogleSignInAccount account = result.getSignInAccount();
            if ( account != null ) {
                userNameView.setText(account.getDisplayName());
                userId = account.getId();
                img_url = account.getPhotoUrl().toString();
                Glide.with(this).load(img_url).into(profilePic);
            } else {
                userNameView.setText("");
            }
            userId = account.getId();
            updateUI(true);

        } else {
            updateUI(false);
        }
    }

    /**
     * Updates the UI to show the correct elements.
     *
     * @param login State of the login.
     */
    public void updateUI(boolean login) {

        if ( login ) {
            mainLayout.setVisibility(View.VISIBLE);
            signInLayout.setVisibility(View.GONE);
        }else {
            mainLayout.setVisibility(View.GONE);
            signInLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Called when connection to Google fails.
     *
     * @param connectionResult The result of a tried connection.
     */
    @Override
    public void onConnectionFailed (@NonNull ConnectionResult connectionResult) {

    }

    /**
     * Handles the result provided by an activity. In this case Google Sign-In.
     *
     * @param requestCode int to identify the request.
     * @param resultCode int to identify the result.
     * @param data Intent to initiate a request.
     */
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == REQ_CODE ) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }

    /**
     * Used to inflate the overflow menu in the appbar.
     *
     * @param menu The Menu to be inflated.
     * @return Tells android whether to show the menu or not.
     */
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Called when an item in the overflow menu is selected and invokes the corresponding actions.
     *
     * @param item The selected MenuItem.
     * @return Return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_log_out:
                signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
