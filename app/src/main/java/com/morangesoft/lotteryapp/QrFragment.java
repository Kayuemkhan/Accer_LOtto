package com.morangesoft.lotteryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class QrFragment extends Fragment implements ZXingScannerView.ResultHandler {
    private ZXingScannerView zXingScannerView;
    private TextView textView;
    public QrFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_qr, container, false);

        zXingScannerView = root.findViewById(R.id.zxscan);
        textView= root.findViewById(R.id.txt_result);



        return root;
    }
    public void onDestroy() {
        zXingScannerView.stopCamera();
        super.onDestroy();
    }

    @Override
    public void handleResult(Result rawResult) {
        textView.setText(rawResult.getText());
        zXingScannerView.startCamera();
    }
    @Override
    public void onResume() {
        super.onResume();
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().finish();
    }
}
