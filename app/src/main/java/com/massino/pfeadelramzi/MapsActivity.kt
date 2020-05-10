package com.massino.pfeadelramzi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import org.jetbrains.anko.toast


class MapsActivity : AppCompatActivity(), OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {

    companion object {
        const val universite_lat=36.751660
        const val universite_lgn=5.040632

        const val tobale_lat=36.748842
        const val tobale_lng=5.071809

        const val remla_lat=36.734095
        const val remla_lgn= 5.053177

        const val mangin_lat=36.759122
        const val mangin_lgn= 5.085548



    }

    //deux variables pour controler l'accée a internet et au gps
    var gps_enabled:Boolean?=false
    var network_enabled:Boolean?=false
    var lm: LocationManager? = null

    private val PERMISSION_CODE = 1000
    var mapFragment : SupportMapFragment?=null

    lateinit var apiClient: GoogleApiClient
    var mLocation: Location? =null
    var lat: Double = 0.0
    var lng: Double = 0.0



    lateinit var mLocationRequest: LocationRequest
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = supportFragmentManager?.findFragmentById(R.id.map) as SupportMapFragment?


        lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        apiClient=GoogleApiClient.Builder(applicationContext).addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_DENIED ||
                checkSelfPermission( Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_DENIED){
                //permission was not enabled
                val permission = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                //show popup to request permission
                requestPermissions(permission, PERMISSION_CODE)
                /**
                 * on verif a chaque fois si les permissions sont accordées
                 * */
                Log.i("XXXX","les permissions n'etaient pas accordées elles doivent l'etre")


            }
            else{
                /***
                 * si les permissions sont accordées
                 * ont verifie si le gps et l'accé
                 * a internet sont dispo
                 * */


                Log.i("XXXX","on check la localisation et internet")
                gps_enabled = lm?.isProviderEnabled(LocationManager.GPS_PROVIDER)
                network_enabled = lm?.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            }
        }
        else{

            toast("ancien telephone je ne  sais pas quoi faire ")

        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Ajouter quelque marker a notre MAP
        /**
         * I love  Sydney <3
         * */
        var options: MarkerOptions= MarkerOptions()
        var options1: MarkerOptions= MarkerOptions()
        var options2: MarkerOptions= MarkerOptions()
        var options3: MarkerOptions= MarkerOptions()




        val targa = LatLng(universite_lat, universite_lgn)
        val citemangin = LatLng(mangin_lat, mangin_lgn)
        val remla = LatLng(remla_lat, remla_lgn)
        val tobale = LatLng(tobale_lat, tobale_lng)



        options.title("Principal")
        options.snippet("snippet")
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_svg))
        options.position(targa)



        options3.title("Magazin 1")
        options3.snippet("Cité Mangin")
        options3.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_svg))
        options3.position(citemangin)


        options2.title("Magazin 2")
        options2.snippet("Cité Remla")
        options2.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_svg))
        options2.position(remla)


        options1.title("Magazin 3")
        options1.snippet("Cité Tobale")
        options1.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_svg))
        options1.position(tobale)




        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL)
        mMap.setMyLocationEnabled(true)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(targa,20F))
        mMap.addMarker(options)
        mMap.addMarker(options1)
        mMap.addMarker(options2)
        mMap.addMarker(options3)

    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        toast("Connection to Google API Failed")    }

    override fun onConnectionSuspended(p0: Int) {
        toast("Service  indisponible pour le moment")
    }

    override fun onConnected(p0: Bundle?) {
        Log.i("XXXXXXXXXXXXXX","On va check les permissions")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_DENIED ||
                checkSelfPermission( Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_DENIED){
                //permission was not enabled
                val permission = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                //show popup to request permission
                requestPermissions(permission, PERMISSION_CODE)
                Log.i("XXXX","les permissions n'etaient pas accordées elles doivent l'etre")


            }
            else{

                if ((gps_enabled!!)&&(network_enabled!!))
                {
                    Log.i("XXXXxxxx","on va recup le localisation")
                    mLocation=LocationServices.FusedLocationApi.getLastLocation(apiClient)
                    if (mLocation!=null){
                        lat= mLocation!!.latitude
                        lng= mLocation!!.longitude
                        Log.i("XXXX","on est dans le if")
                        mapFragment?.getMapAsync(this)


                    }
                    else{
                        lat= universite_lat
                        lng= universite_lgn

                        Log.i("XXXX","position affécté manuélement")
                        toast("la position a etait affecté manuélement")

                    }

                }
                else{
                    Log.i("XXXX","la localisation est désactivé")
                    toast("localisation indisponible")

                }


            }
        }

        else{

            Log.i("XXXXxxxx","ancien telephone je ne sais pas quoi faire")

        }

    }



    override fun onStart() {
        super.onStart()
        apiClient.connect()
    }

    override fun onStop() {
        super.onStop()
        apiClient.disconnect()
    }


    override fun onResume() {
        super.onResume()
        apiClient.connect()
    }
    override fun onPause() {
        super.onPause()
        apiClient.disconnect()
    }


    override fun onDestroy() {
        super.onDestroy()
        apiClient.disconnect()
    }


    override fun onRestart() {
        super.onRestart()
        apiClient.connect()
    }

}


