package com.example.project250;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.logging.SocketHandler;

import static com.example.project250.R.id.fragment;
import static com.example.project250.R.id.sceneform_ar_scene_view;

public class MainActivity extends AppCompatActivity {

    private ArFragment arFragment;
        private  enum  Shapetype{
            Cube,Sphere,Cylinder
        }

        private Shapetype shapetype=Shapetype.Cube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        arFragment=(ArFragment) getSupportFragmentManager().findFragmentById(fragment);

        Button cube=findViewById(R.id.cube);
        Button sphere =findViewById(R.id.spehere);
        Button cylinder =findViewById(R.id.cylinder);

        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {

            if(shapetype==Shapetype.Cube)
                placecube(hitResult.createAnchor());
            else if(shapetype==Shapetype.Cylinder)
                placecylinder(hitResult.createAnchor());
            else
                placesphere(hitResult.createAnchor());

        });

        cube.setOnClickListener(view->shapetype=Shapetype.Cube);
        sphere.setOnClickListener(view->shapetype=Shapetype.Sphere);
        cylinder.setOnClickListener(view->shapetype=Shapetype.Cylinder);









    }

    private void placesphere(Anchor anchor) {
        MaterialFactory
                .makeOpaqueWithColor(this,new Color(android.graphics.Color.RED))
                        .thenAccept(material -> {
                            ModelRenderable modelRenderable=
                                    ShapeFactory.makeSphere(.1f,new Vector3(0f,.1f,0f)
                                    ,material);
                placemodel(modelRenderable,anchor);
                        });
    }

    private void placecylinder(Anchor anchor) {
MaterialFactory
        .makeOpaqueWithColor(this,new Color(android.graphics.Color.YELLOW))
        .thenAccept(material -> {
           ModelRenderable modelRenderable=
           ShapeFactory.makeCylinder(.1f,.2f,new Vector3(0f,.2f,0f),
                   material);
           placemodel(modelRenderable,anchor);

        });

    }

    private void placecube(Anchor anchor) {
        MaterialFactory
                .makeOpaqueWithColor(this,new Color(android.graphics.Color.BLUE))
                .thenAccept(material -> {
                    ModelRenderable modelRenderable=
                            ShapeFactory.makeCube(new Vector3(.1f,.1f,.1f),new Vector3(0f,.1f,0f),
                                    material);
                    placemodel(modelRenderable,anchor);

                });
    }

    private void placemodel(ModelRenderable modelRenderable, Anchor anchor) {

        AnchorNode anchorNode=new AnchorNode(anchor);
        anchorNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);


    }
}
