package nossafirma.com.br.meuapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AboutActivity extends Activity {

    private ImageView ivLogo;
    private TextView tvVersion;
    private TextView tvDev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ivLogo = (ImageView) findViewById(R.id.ivLogo);
        tvVersion = (TextView) findViewById(R.id.tvVersion);
        tvDev = (TextView) findViewById(R.id.tvDev);

        ivLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
