package io.ipoli.android.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import org.joda.time.LocalDate;

import java.util.Date;
import java.util.Set;

import javax.inject.Inject;

import io.ipoli.android.Constants;
import io.ipoli.android.R;
import io.ipoli.android.app.App;
import io.ipoli.android.app.AppComponent;
import io.ipoli.android.app.ui.NoInternetDialogFragment;
import io.ipoli.android.app.utils.DateUtils;
import io.ipoli.android.app.utils.LocalStorage;
import io.ipoli.android.challenge.activities.PickDailyChallengeQuestsActivity;

/**
 * Created by Venelin Valkov <venelin@curiousily.com>
 * on 1/7/16.
 */
public class BaseActivity extends RxAppCompatActivity {

    @Inject
    protected Bus eventBus;

    @Inject
    protected LocalStorage localStorage;

    protected NoInternetDialogFragment noInternetDialog;

    protected AppComponent appComponent() {
        return App.getAppComponent(this);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appComponent().inject(this);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Date todayUtc = DateUtils.toStartOfDayUTC(LocalDate.now());
        Date lastCompleted = new Date(localStorage.readLong(Constants.KEY_DAILY_CHALLENGE_LAST_COMPLETED));
        boolean isCompletedForToday = todayUtc.equals(lastCompleted);

        Set<Integer> challengeDays = localStorage.readIntSet(Constants.KEY_DAILY_CHALLENGE_DAYS, Constants.DEFAULT_DAILY_CHALLENGE_DAYS);
        int currentDayOfWeek = LocalDate.now().getDayOfWeek();
        if (isCompletedForToday || !challengeDays.contains(currentDayOfWeek)) {
            menu.findItem(R.id.action_pick_daily_challenge_quests).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_pick_daily_challenge_quests) {
            startActivity(new Intent(this, PickDailyChallengeQuestsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void showLevelDownMessage(int newLevel) {
        Toast.makeText(this, "Level lost! Your level is " + newLevel + "!", Toast.LENGTH_LONG).show();
    }

    protected void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected void showKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    protected void showNoInternetActivity() {
        Intent intent = new Intent(this, NoInternetActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
