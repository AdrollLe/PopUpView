package view.adroll.popupview;

import androidx.appcompat.app.AppCompatActivity;
import view.adroll.popupview.dialog.PopUpViewDialog;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private PopUpViewDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN){
            if (dialog == null){
                dialog = new PopUpViewDialog(this);
                Window window = dialog.getWindow();
                window.setBackgroundDrawableResource(R.color.transparent);
                window.getDecorView().setPadding(0, 0, 0, 0);
                window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            }

            dialog.show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
