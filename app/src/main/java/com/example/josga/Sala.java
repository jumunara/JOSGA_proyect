package com.example.josga;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class Sala extends AppCompatActivity {

    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala);

        image = (ImageView) findViewById(R.id.ruleta);
        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View Evento, MotionEvent Accion) {
                switch (Accion.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        GirarRuleta(image);
                        break;
                }

                return true;

            }

        });

    }

    private void GirarRuleta(ImageView image) {
        RotateAnimation Animacion = new RotateAnimation(0,570,RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        Animacion.setDuration(2000);
        image.startAnimation(Animacion);

    }

}