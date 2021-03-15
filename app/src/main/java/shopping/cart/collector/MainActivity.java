package shopping.cart.collector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import shopping.cart.collector.R;

public class MainActivity extends AppCompatActivity {
    Button playGame;

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log.d("MainActivity.java", "onCreate()");
        super.onCreate(savedInstanceState);
        //Set content view to main menu
        setContentView(R.layout.mainmenu);

        //Associate button with play button
        playGame = findViewById(R.id.play_button);

        //Set window to fullscreen
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Open game when pushing button
        playGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame();
            }
        });

        //setContentView(R.layout.activity_main);
    }

    public void openGame(){
        //Set content view to new game class (for rendering objects)
        game = new Game(this);
        setContentView(game);
    }


    @Override
    protected void onStart() {
       // Log.d("MainActivity.java", "onStart()");
        super.onStart();
    }

    @Override
    protected void onResume() {
       // Log.d("MainActivity.java", "onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
      //  Log.d("MainActivity.java", "onPause()");
        game.pause();
        super.onPause();
    }

    @Override
    protected void onStop() {
       // Log.d("MainActivity.java", "onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
       // Log.d("MainActivity.java", "onDestroy()");
        super.onDestroy();
    }


    //Back button
    @Override
    public void onBackPressed() {
      //  Log.d("MainActivity.java", "onBackPressed()");

        //return to title page on back press
        game.pause();
        super.onPause();
        setContentView(R.layout.mainmenu);

        //Associate button with play button
        playGame = findViewById(R.id.play_button);
        //Open game when pushing button
        playGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame();
            }
        });


        //don't do anything on back button
        //super.onBackPressed();
    }
}