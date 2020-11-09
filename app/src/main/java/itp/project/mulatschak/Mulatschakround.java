package itp.project.mulatschak;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Mulatschakround extends AppCompatActivity {

    public static Colors adout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mulatschakround);
    }

    public static Colors getAdout() {
        return adout;
    }
}