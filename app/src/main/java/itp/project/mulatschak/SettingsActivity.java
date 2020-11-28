package itp.project.mulatschak;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(getResources().getString(R.string.settings_name));

        // wechseln zu Design-Activity aus Button
        View design = findViewById(R.id.design_button);
        design.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, DesignPackActivity.class));
            }
        });
    }
}