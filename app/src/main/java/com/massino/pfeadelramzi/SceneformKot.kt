package com.massino.pfeadelramzi

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.view.MotionEvent
import android.view.PixelCopy
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.material.snackbar.Snackbar
import com.google.ar.core.*
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
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
          //  val imageUrl = intent.getStringExtra("image_url")
           // Toast.makeText(this,imageUrl,Toast.LENGTH_LONG).show()
            initializeGallery(anchor)
           // placeObject(arFragment, anchor, Uri.parse("Bench.sfb"))

        }

        fab.setOnClickListener{takePhoto()}
        del.setOnClickListener{suppObj()}
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

    // supprimer

    private fun suppObj() {
        val nodeList: List<Node> =
            ArrayList<Node>(
                arFragment.arSceneView.scene.children
            )
        for (childNode in nodeList) {
            if (childNode is AnchorNode) {
                if (childNode.anchor != null) {
                    childNode.anchor!!.detach()
                    childNode.setParent(null)
                }
            }
        }
    }

    //choisir le model


    private fun initializeGallery(anchor: Anchor) {
        // int imageUrl = getIntent().getIntExtra("image_url",0);
        val imageUrl = intent.getStringExtra("image_url")
        val gallery = findViewById<LinearLayout>(R.id.gallery_layout)
        //placeObject(arFragment, anchor, Uri.parse("Bench.sfb"))

        when (imageUrl) {
            "foteuil" -> {
                val andy = ImageView(this)
                andy.setImageResource(R.drawable.fauteilgris)
                andy.contentDescription = "fauteuil Créme"
                gallery.addView(andy)
                placeObject(arFragment,anchor,Uri.parse("foteuil.sfb"))

            }
            "Bench" -> {

                val andy = ImageView(this)
                andy.setImageResource(R.drawable.banc)
                andy.contentDescription = "banc extérieur"
                Toast.makeText(this,"aklin",Toast.LENGTH_LONG).show()

                placeObject(arFragment,anchor,Uri.parse("Bench.sfb"))
                gallery.addView(andy)
            }
            "bureau" -> {
                val andy = ImageView(this)
                andy.setImageResource(R.drawable.burau)
                andy.contentDescription = "bureau"

                    placeObject(arFragment,anchor,Uri.parse("bureau.sfb"))


                gallery.addView(andy)
            }
            "ff" -> {

                    val andy = ImageView(this)
                    andy.setImageResource(R.drawable.fauteuille3)
                    andy.contentDescription = "Fauteuil une place"

                        placeObject(arFragment,anchor,Uri.parse("ff.sfb"))


                    gallery.addView(andy)

            }
            "foteuil2" -> {
                val andy = ImageView(this)
                andy.setImageResource(R.drawable.fauteuille1)
                andy.contentDescription = "Fauteuil bleu"

                    placeObject(arFragment,anchor,Uri.parse("foteuil2.sfb"))


                gallery.addView(andy)
            }
            "table" -> {
                val andy = ImageView(this)
                andy.setImageResource(R.drawable.table)
                andy.contentDescription = "Table extérieur"

                    placeObject(arFragment,anchor,Uri.parse("table.sfb"))


                gallery.addView(andy)
            }
            "thor" -> {
                val andy = ImageView(this)
                andy.setImageResource(R.drawable.thor)
                andy.contentDescription = "Thor"

                    placeObject(arFragment,anchor,Uri.parse("thor.sfb"))


                gallery.addView(andy)
            }
            "fotUnePlace" -> {
                val andy = ImageView(this)
                andy.setImageResource(R.drawable.thor)
                andy.contentDescription = "fotUnePlace"

                    placeObject(arFragment,anchor,Uri.parse("fotUnePlace.sfb"))


                gallery.addView(andy)
            }
            "new2" -> {
                val andy = ImageView(this)
                andy.setImageResource(R.drawable.thor)
                andy.contentDescription = "new2"

                    placeObject(arFragment,anchor,Uri.parse("new2.sfb"))

                gallery.addView(andy)
            }
            "new3" -> {
                val andy = ImageView(this)
                andy.setImageResource(R.drawable.thor)
                andy.contentDescription = "new3"

                    placeObject(arFragment,anchor,Uri.parse("new3.sfb"))


                gallery.addView(andy)
            }
            "deco" -> {
                val andy = ImageView(this)
                andy.setImageResource(R.drawable.thor)
                andy.contentDescription = "deco"

                    placeObject(arFragment,anchor,Uri.parse("deco.sfb"))


                gallery.addView(andy)
            }
            "decos" -> {
                val andy = ImageView(this)
                andy.setImageResource(R.drawable.thor)
                andy.contentDescription = "decos"

                    placeObject(arFragment,anchor,Uri.parse("decos.sfb"))

                    gallery.addView(andy)

            }


            "new1" -> {
                    val andy = ImageView(this)
                    andy.setImageResource(R.drawable.thor)
                    andy.contentDescription = "new1"
                  //  andy.setOnClickListener { view: View? ->
                        //   addObject(Uri.parse("new1.sfb")  )
                        placeObject(arFragment,anchor,Uri.parse("new1.sfb"))

                    //}
                    gallery.addView(andy)
                }

            else -> Toast.makeText(applicationContext, "rien n'a etai ressu", Toast.LENGTH_SHORT).show();


            }


    }

}