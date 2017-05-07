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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private boolean EVENT_ON = false;
    private CoordinatorLayout coordinatorLayout;
    private EventReceiver receiver;
    private Button startButton;
    private AVLoadingIndicatorView avi;
    private ConstraintLayout mainLayout;
    private ConstraintLayout signInLayout;
    private SignInButton signInButton;
    private Button logOutButton;
    private TextView userNameView;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 666;
    private String userId;
    LocalBroadcastManager manager;

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
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                singIn();
            }
        });
        logOutButton = (Button) findViewById(R.id.logOutButton);
        userNameView = (TextView) findViewById(R.id.userName);
        avi.hide();
        mainLayout.setVisibility(View.GONE);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestId().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();
    }

    @Override
    protected void onResume () {
        super.onResume();

        IntentFilter filter = new IntentFilter("fi.tamk.tiko.project.kallio.eetu.event");
        manager = LocalBroadcastManager.getInstance(this);
        receiver = new EventReceiver();
        manager.registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause () {
        super.onPause();
        manager.unregisterReceiver(receiver);
    }

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
        }
    }

    public void  postEvent(WorkEvent event) {

        new EventPostTask().execute(event);
    }

    public void viewEvents(View view) {

        startActivity(new Intent(this, EventListActivity.class).putExtra("user",userId));
    }

    public void singIn () {

        Log.d("signIn", "got here");
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQ_CODE);
    }

    public void signOut(View view) {

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult (@NonNull Status status) {
                updateUI(false);
            }
        });
    }

    public void handleResult(GoogleSignInResult result) {

        if ( result.isSuccess() ) {
            GoogleSignInAccount account = result.getSignInAccount();
            if ( account.getDisplayName() != null ) {
                userNameView.setText(account.getDisplayName());
                userId = account.getId();
            } else {
                userNameView.setText("");
            }
            userId = account.getId();
            updateUI(true);

        } else {
            updateUI(false);
        }
    }

    public void updateUI(boolean login) {

        if ( login ) {
            mainLayout.setVisibility(View.VISIBLE);
            signInLayout.setVisibility(View.GONE);
        }else {
            mainLayout.setVisibility(View.GONE);
            signInLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onConnectionFailed (@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == REQ_CODE ) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }

    public String getUserId () {
        return userId;
    }
}
