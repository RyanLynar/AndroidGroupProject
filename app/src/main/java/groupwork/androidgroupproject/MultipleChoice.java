package groupwork.androidgroupproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

/**
 * Created by Adam on 2018-03-28.
 */

public class MultipleChoice extends Activity {

    protected static final String ACTIVITY_NAME="MultipleChoice";

    Button newQuiz;
    Button existingQuiz;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);

        newQuiz=(Button)findViewById(R.id.NewQuizButton);
        existingQuiz=(Button)findViewById(R.id.ExistingQuizButton);

        newQuiz.setOnClickListener(t->{
            Log.i(ACTIVITY_NAME,"User clicked 'new quiz'");

            intent = new Intent(MultipleChoice.this, NewQuiz.class);
            startActivity(intent);
        });

        existingQuiz.setOnClickListener(t->{
            Log.i(ACTIVITY_NAME,"User clicked 'existing quiz'");
        });



    }


}
