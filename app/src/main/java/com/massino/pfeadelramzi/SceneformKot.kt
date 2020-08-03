package com.massino.pfeadelramzi

import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.Anchor
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode


class SceneformKot : AppCompatActivity() {

    private val TAG = "SceneformKot"
    private lateinit var arFragment: ArFragment

    private val pointer = PointerDrawable()
    private val isTracking = false
    private val isHitting = false
    //private var fragment: ArFragment? = null
    private var modelLoader: ModelLoader? = null
    private val theAnchorNode: AnchorNode? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sceneforme)
        arFragment = supportFragmentManager.findFragmentById(R.id.sceneform_fragment) as ArFragment

        arFragment?.setOnTapArPlaneListener { hitResult: HitResult, plane: Plane, motionEvent: MotionEvent ->
            val anchor = hitResult.createAnchor()

            // int imageUrl = getIntent().getIntExtra("image_url",0);
            val imageUrl = intent.getStringExtra("image_url")
            Toast.makeText(this,imageUrl,Toast.LENGTH_LONG).show()
            placeObject(arFragment, anchor, Uri.parse("Bench.sfb"))

        }
    }

    private fun placeObject(arFragment: ArFragment, anchor: Anchor, uri: Uri) {
        ModelRenderable.builder()
            .setSource(arFragment.context, uri)
            .build()
            .thenAccept({ modelRenderable -> addNodeToScene(arFragment, anchor, modelRenderable) })
            .exceptionally { throwable ->
                Toast.makeText(arFragment.context, "Error:" + throwable.message, Toast.LENGTH_LONG)
                    .show()
                null
            }

    }

    private fun addNodeToScene(arFragment: ArFragment, anchor: Anchor, renderable: Renderable) {
        val anchorNode = AnchorNode(anchor)
        val node = TransformableNode(arFragment.transformationSystem)
        node.renderable = renderable
        node.setParent(anchorNode)
        arFragment.arSceneView.scene.addChild(anchorNode)
        node.select()
    }



}