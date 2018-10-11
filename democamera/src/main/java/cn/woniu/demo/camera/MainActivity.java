package cn.woniu.demo.camera;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnLookWord).setOnClickListener(this);
        findViewById(R.id.btnQR).setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnLookWord:
                startActivity(new Intent(this,CameraActivity.class));
                break;
            case R.id.btnQR:
                break;
        }
    }
}
