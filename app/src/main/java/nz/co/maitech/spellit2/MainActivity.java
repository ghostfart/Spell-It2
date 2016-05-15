package nz.co.maitech.spellit2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int level = 0;
    private int position = 0;
    private String answer = "";
    private String[] words = new String[5];
    private Random rand = new Random();
    private boolean nextLevelReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        populateWords();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setButtons();
    }

    /**
     * Reset the game after all words are guessed.
     *
     * @param view the button that was clicked.
     */
    public void reset(View view) {
        level = 0;
        resetWord();
    }

    /**
     * If the image is clicked
     * @param view
     */
    public void nextSpellIt(View view) {
        if (nextLevelReady) {
            clearBoard();
            setUpPositions();
            setImage();
            nextLevelReady = false;
            View answer = findViewById(R.id.image_name);
            answer.setVisibility(View.GONE);
        }
    }

    /**
     * Resets the current word to the beginning, and arranges the board accordingly.
     */
    private void resetWord() {
        answer = "";
        position = 0;
    }

    /**
     * Set's up the words for play.
     */
    private void populateWords() {
        words[0] = "cat";
        words[1] = "dog";
        words[2] = "ball";
        words[3] = "tree";
        words[4] = "house";
    }

    /**
     * Processes the button push of a letter in the game.
     *
     * @param view the letter Button that was pushed.
     */
    public void buttonPush(View view) {
        Button btn = (Button) view;
        String letter = btn.getText().toString();
        TextView currentPosition = getCurrentPosition();
        if (letter.equals(words[level].substring(position, position + 1))) {
            updateCurrentPosition(currentPosition, letter);
            setButtons();
        } else {
            Toast.makeText(this, "Sorry its not " + letter + "\nPlease guess again!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Updates the current guessing position at the top of the board. This method assumes the parameters are correct.
     *
     * @param currentPosition The view associated with the current guessing postion.
     * @param letter          The correctly guessed letter to be placed on screen.
     */
    private void updateCurrentPosition(TextView currentPosition, String letter) {
        answer += letter;
        currentPosition.setText(letter);
        // Check to see if you've completed the word.
        if (answer.equals(words[level])) {
            youWin();
        } else {
            position++;
        }
    }

    /**
     * Manage whats happens when the spelling word has been completed.
     */
    private void youWin() {
        TextView view = (TextView) findViewById(R.id.image_name);
        view.setText(answer);
        view.setVisibility(View.VISIBLE);
        nextLevelReady = true;
        if (level < 4) {
            level++;
        } else {
            level = 0;
        }
        resetWord();
    }

    /**
     * This method changes the onscreen image to the correct image.
     */
    private void setImage() {
        ImageButton image = (ImageButton) findViewById(R.id.picture_view);
        switch (level) {
            case 0:
                image.setImageResource(R.drawable.cat);
                break;
            case 1:
                image.setImageResource(R.drawable.dog);
                break;
            case 2:
                image.setImageResource(R.drawable.ball);
                break;
            case 3:
                image.setImageResource(R.drawable.tree);
                break;
            case 4:
                image.setImageResource(R.drawable.house);
                break;
        }
    }

    /**
     * This method will set the positions for the board.
     */
    private void setUpPositions() {
        if (level < 2) {
            TextView textView = (TextView) findViewById(R.id.fourth_letter);
            View view = findViewById(R.id.fourth_underline);
            textView.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
            textView = (TextView) findViewById(R.id.fifth_letter);
            view = findViewById(R.id.fifth_underline);
            textView.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        } else if (level < 4 && level > 1) {
            TextView textView = (TextView) findViewById(R.id.fourth_letter);
            View view = findViewById(R.id.fourth_underline);
            textView.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
        } else if (level == 4) {
            TextView textView = (TextView) findViewById(R.id.fifth_letter);
            View view = findViewById(R.id.fifth_underline);
            textView.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method will reset the letters displayed on screen. Back to the beginning start of a ?
     */
    private void clearBoard() {
        TextView textView = (TextView) findViewById(R.id.first_letter);
        textView.setText("?");
        textView = (TextView) findViewById(R.id.second_letter);
        textView.setText("");
        textView = (TextView) findViewById(R.id.third_letter);
        textView.setText("");
        textView = (TextView) findViewById(R.id.fourth_letter);
        textView.setText("");
        textView = (TextView) findViewById(R.id.fifth_letter);
        textView.setText("");
    }

    /**
     * This method returns the TextView of the current letter being guessed.
     *
     * @return TextView of current position.
     */
    private TextView getCurrentPosition() {
        TextView view;
        switch (position) {
            case 0:
                view = (TextView) findViewById(R.id.first_letter);
                break;
            case 1:
                view = (TextView) findViewById(R.id.second_letter);
                break;
            case 2:
                view = (TextView) findViewById(R.id.third_letter);
                break;
            case 3:
                view = (TextView) findViewById(R.id.fourth_letter);
                break;
            case 4:
                view = (TextView) findViewById(R.id.fifth_letter);
                break;
            default:
                view = null;
                break;
        }
        return view;
    }

    /**
     * This method sets the buttons randomly at the base of the display. But includes the correct letter to be displayed.
     */
    private void setButtons() {
        String currentLetter = words[level].substring(position, position + 1);
        int r = rand.nextInt(3);
//        setButtonOne(currentLetter, r);
//        setButtonTwo(currentLetter, r);
//        setButtonThree(currentLetter, r);
        Button[] buttons = new Button[3];
        buttons[0] = (Button) findViewById(R.id.button_one);
        buttons[1] = (Button) findViewById(R.id.button_two);
        buttons[2] = (Button) findViewById(R.id.button_three);
        for (int i = 0; i < 3; i++) {
            if (r == i) {
                buttons[i].setText(currentLetter);
            } else {
                buttons[i].setText(randomLetter(currentLetter));
            }
        }
    }


    /**
     * This method returns a random letter in the alphabet that is not the correct letter.
     *
     * @param c is the correctly guessed letter
     * @return a letter as a String object.
     */
    private String randomLetter(String c) {
        String randomLetter = c;
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        while (randomLetter.equals(c)) {
            int r = rand.nextInt(26);
            randomLetter = alphabet.substring(r, r + 1);
        }
        return randomLetter;
    }

}
