package com.massino.pfeadelramzi

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.*
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import android.graphics.Point
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.view.PixelCopy
import androidx.core.content.FileProvider
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.content_main2.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class SceneformKot : AppCompatActivity() {

    private val TAG = "SceneformKot"
    private lateinit var arFragment: ArFragment

    private val pointer = PointerDrawable()
    private var isTracking = false
    private var isHitting = false
    //private var fragment: ArFragment? = null
    private var modelLoader: ModelLoader? = null
    private val theAnchorNode: AnchorNode? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sceneforme)
        arFragment = supportFragmentManager.findFragmentById(R.id.sceneform_fragment) as ArFragment
        arFragment.arSceneView.scene
            .addOnUpdateListener(Scene.OnUpdateListener { frameTime: FrameTime? ->
                arFragment.onUpdate(frameTime)
                onUpdate()
            })
        arFragment?.setOnTapArPlaneListener { hitResult: HitResult, plane: Plane, motionEvent: MotionEvent ->
            val anchor = hitResult.createAnchor()

            //AJOUTER TOUT LES MODELS
            val imageUrl = intent.getStringExtra("image_url")
            Toast.makeText(this,imageUrl,Toast.LENGTH_LONG).show()
            placeObject(arFragment, anchor, Uri.parse("Bench.sfb"))

        }

        del.setOnClickListener{takePhoto()}
    }

    private fun placeObject(arFragment: ArFragment, anchor: Anchor, uri: Uri) {
        ModelRenderable.builder()
            .setSource(arFragment.context, uri)
            .build()
            .thenAccept { modelRenderable -> addNodeToScene(arFragment, anchor, modelRenderable) }
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

    private fun onUpdate() {
        val trackingChanged = updateTracking()
        val contentView = findViewById<View>(android.R.id.content)
        if (trackingChanged) {
            if (isTracking) {
                contentView.overlay.add(pointer)
            } else {
                contentView.overlay.remove(pointer)
            }
            contentView.invalidate()
        }
        if (isTracking) {
            val hitTestChanged = updateHitTest()
            if (hitTestChanged) {
                pointer.isEnabled = isHitting
                contentView.invalidate()
            }
        }
    }

    private fun updateTracking(): Boolean {
        val frame: Frame? = arFragment.arSceneView.arFrame
        val wasTracking = isTracking
        isTracking = frame != null &&
                frame.camera.trackingState == TrackingState.TRACKING
        return isTracking != wasTracking
    }

    private fun updateHitTest(): Boolean {
        val frame: Frame? = arFragment.arSceneView.arFrame
        val pt: Point = getScreenCenter()
        val hits: List<HitResult>
        val wasHitting = isHitting
        isHitting = false
        if (frame != null) {
            hits = frame.hitTest(pt.x.toFloat(), pt.y.toFloat())
            for (hit in hits) {
                val trackable = hit.trackable
                if (trackable is Plane &&
                    trackable.isPoseInPolygon(hit.hitPose)
                ) {
                    isHitting = true
                    break
                }
            }
        }
        return wasHitting != isHitting
    }

    private fun getScreenCenter(): Point {
        val vw = findViewById<View>(android.R.id.content)
        return Point(vw.width / 2, vw.height / 2)
    }



    // button ScreenShot 
    private fun takePhoto() {
        val filename = generateFilename()
        val view = arFragment.arSceneView
        val bitmap = Bitmap.createBitmap(view.width, view.height,
            Bitmap.Config.ARGB_8888)
        val handlerthread = HandlerThread("PixelCopier")
        handlerthread.start()
        PixelCopy.request(view, bitmap, {
            if (it == PixelCopy.SUCCESS) {
                try {
                    saveToBitmap(bitmap, filename)
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                    return@request
                }
                val snack = Snackbar.make(findViewById(android.R.id.content),
                    "Photo saved", Snackbar.LENGTH_LONG)
                snack.setAction("open in photos") {
                    val photoFile = File(filename)
                    val photoURI = FileProvider.getUriForFile(this,
                        this.packageName + ".ar.hariofspades.provider",
                        photoFile)
                    val intent = Intent(Intent.ACTION_VIEW, photoURI)
                    intent.setDataAndType(photoURI, "image/*")
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    startActivity(intent)
                }
                snack.show()
            } else {
                Toast.makeText(this, "Failed to copyPixels: $it", Toast.LENGTH_LONG).show()
            }
            handlerthread.quitSafely()
        }, Handler(handlerthread.looper))
    }

    private fun generateFilename(): String {
        val date = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
        return this.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() +
                File.separator + "Sceneform/" + date + "_screenshot.jpg"
      //  return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() +
            //    File.separator + "Sceneform/" + date + "_screenshot.jpg"
    }

    private fun saveToBitmap(bitmap: Bitmap, filename: String) {
        val out = File(filename)
        if (!out.parentFile.exists()) {
            out.parentFile.mkdirs()
        }
        try {

            val outputStream = FileOutputStream(filename)
            val outputData = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputData)
            outputData.writeTo(outputStream)
            outputStream.flush()
            outputStream.close()

        } catch (e: Exception) {
            e.printStackTrace()
            throw IOException("Failed to save bitmap to disk", e)
        }

    }


}