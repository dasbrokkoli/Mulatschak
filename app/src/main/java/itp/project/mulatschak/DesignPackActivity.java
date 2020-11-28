package itp.project.mulatschak;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class DesignPackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_pack);
        setTitle(getResources().getString(R.string.design));
    }
}