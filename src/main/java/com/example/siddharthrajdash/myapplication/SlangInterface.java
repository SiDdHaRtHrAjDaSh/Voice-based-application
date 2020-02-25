package in.slanglabs.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.siddharthrajdash.myapplication.SeconfActivity;

import java.util.Locale;

import in.slanglabs.platform.SlangBuddy;
import in.slanglabs.platform.SlangBuddyOptions;
import in.slanglabs.platform.SlangIntent;
import in.slanglabs.platform.SlangLocale;
import in.slanglabs.platform.SlangSession;
import in.slanglabs.platform.action.SlangIntentAction;

public class SlangInterface {

    private static String buddy_id = "d47f7cacd6ba46af891859535de21362";
    private static String api_key = "a2d8c0f34d29481ab41a2eb59c08c2af";

    // To initialize Slang in your application, simply call SlangInterface.init(context)
    public static void init(Activity activity) {
        try {
            SlangBuddyOptions options = new SlangBuddyOptions.Builder()
                    .setApplication(activity.getApplication())
                    .setBuddyId(getAppId())
                    .setAPIKey(getApiKey())
                    .setListener(new BuddyListener(activity.getApplicationContext()))
                    .setIntentAction(new SlangAttendanceAction())
                    .setDefaultLocale(SlangLocale.LOCALE_ENGLISH_IN)
                    //.setConfigOverrides(getConfigOverrides())
                    .setEnvironment(SlangBuddy.Environment.PRODUCTION)
                    .setStartActivity(activity)
                    .build();
            SlangBuddy.initialize(options);

        } catch (SlangBuddyOptions.InvalidOptionException e) {
            e.printStackTrace();
        } catch (SlangBuddy.InsufficientPrivilegeException e) {
            e.printStackTrace();
        }
    }

    private static String getAppId() {
        return buddy_id;
    }

    private static String getApiKey() {
        return api_key;
    }

    private static class BuddyListener implements SlangBuddy.Listener {
        private Context appContext;

        BuddyListener(Context appContext) {
            this.appContext = appContext;
        }

        @Override
        public void onInitialized() {
            Log.d("BuddyListener", "Slang Initialised Successfully");
        }

        @Override
        public void onInitializationFailed(final SlangBuddy.InitializationError e) {
            Log.d("BuddyListener", "Slang failed:" + e.getMessage());

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(appContext, "Failed to initialise Slang:" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }, 10);
        }

        @Override
        public void onLocaleChanged(final Locale newLocale) {
            Log.d("BuddyListener", "Locale Changed:" + newLocale.getDisplayName());
        }

        @Override
        public void onLocaleChangeFailed(final Locale newLocale, final SlangBuddy.LocaleChangeError e) {
            Log.d("BuddyListener",
                    "Locale(" + newLocale.getDisplayName() + ") Change Failed:" + e.getMessage());

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(appContext,
                            "Locale(" + newLocale.getDisplayName() + ") Change Failed:" + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }, 10);
        }
    }

    public static class SlangAttendanceAction implements SlangIntentAction {

        static final String INTENT_MARK_ATTENDANCE = "mark_attendance";

        @Override
        public Status action(SlangIntent intent, SlangSession session) {
            if (intent.getName().equals(INTENT_MARK_ATTENDANCE)) {
                session.getCurrentActivity().startActivity(new Intent(session.getCurrentActivity(), SeconfActivity.class));

                if (session.getCurrentActivity() instanceof SeconfActivity) {
                    session.getCurrentActivity().finish();
                }
            }
            return Status.SUCCESS;
        }
    }
}