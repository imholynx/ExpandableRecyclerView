package com.imholynx.expandablerecyclerview;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.imholynx.expandablerecyclerview.data.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<Question> faq = new ArrayList<>();
    RecyclerView rv;
    RVAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initializeFaqList();

        rv = findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        adapter = new RVAdapter(faq);
        rv.setAdapter(adapter);
    }

    public class RVAdapter extends RecyclerView.Adapter<RVAdapter.QuestionViewHolder> {

        List<Question> faq;
        List<Boolean> expanded;

        public RVAdapter(List<Question> faq) {
            this.faq = faq;
            expanded = new ArrayList<>(Arrays.asList(new Boolean[faq.size()]));
            Collections.fill(expanded,Boolean.FALSE);
        }

        @NonNull
        @Override
        public RVAdapter.QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.question_cardview, viewGroup, false);
            QuestionViewHolder questionViewHolder = new QuestionViewHolder(view);
            return questionViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RVAdapter.QuestionViewHolder questionViewHolder, final int i) {
            questionViewHolder.question.setText(faq.get(i).getQuestion());
            questionViewHolder.answer.setText(faq.get(i).getAnswer());
            final boolean isExpanded = expanded.get(i);
            questionViewHolder.answer.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            questionViewHolder.itemView.setActivated(isExpanded);
            questionViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View view) {
                    expanded.set(i, !isExpanded);
                    Log.e("i",""+i);
                    TransitionManager.beginDelayedTransition(rv);
                    notifyItemChanged(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return faq.size();
        }

        public class QuestionViewHolder extends RecyclerView.ViewHolder {
            CardView cardView;
            TextView question;
            TextView answer;

            QuestionViewHolder(View itemView) {
                super(itemView);
                cardView = itemView.findViewById(R.id.cv);
                question = itemView.findViewById(R.id.question);
                answer = itemView.findViewById(R.id.answer);
            }
        }
    }

    private void initializeFaqList() {
        for (int i = 0; i < 25; i++)
            faq.add(new Question("Вопрос" + i + "?", "Ответ" + i));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
