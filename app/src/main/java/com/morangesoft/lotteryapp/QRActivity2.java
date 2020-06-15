package com.morangesoft.lotteryapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRActivity2 extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView zXingScannerView;
    private TextView textView;
    Context context;
    private String resultText = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        zXingScannerView = new ZXingScannerView(this); /* Initialize object */
        setContentView(zXingScannerView); /* Set the ScannerView as a content of current activity */
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        zXingScannerView.setResultHandler(QRActivity2.this);
                        zXingScannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(QRActivity2.this,"Please Accept this permissions",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        return;
                    }
                }).check();
    }

    @Override
    public void handleResult(Result scanResult) {

        ToneGenerator toneNotification = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100); /* Setting beep sound */
        toneNotification.startTone(ToneGenerator.TONE_PROP_BEEP, 150);

        resultText = scanResult.getText();

        Intent intent = new Intent(QRActivity2.this, Check2.class);
        intent.putExtra("ResultText", resultText); /* Sending text to next activity to display */
        startActivity(intent);

    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    @Override
    public void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            zXingScannerView.setResultHandler(this);
            zXingScannerView.startCamera();
        } else {
            ActivityCompat.requestPermissions(QRActivity2.this, new
                    String[]{Manifest.permission.CAMERA}, 1024);
        }
    }
    @Override
    public void onDestroy() {
        zXingScannerView.stopCameraPreview();
        zXingScannerView.stopCamera();
        zXingScannerView.destroyDrawingCache();
        super.onDestroy();
    }

    @Override
    public void onStop() {
        zXingScannerView.stopCameraPreview();
        zXingScannerView.stopCamera();

        super.onStop();
    }
}