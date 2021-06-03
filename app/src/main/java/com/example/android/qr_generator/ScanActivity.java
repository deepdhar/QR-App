package com.example.android.qr_generator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanActivity extends AppCompatActivity {

    Button scanButton, copyButton;
    TextView scanned_textview;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        scanButton = findViewById(R.id.scan_bn);
        copyButton = findViewById(R.id.copy_bn);
        scanned_textview = findViewById(R.id.scanned_text_tv);

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 1;
                IntentIntegrator intentIntegrator = new IntentIntegrator(ScanActivity.this);
                intentIntegrator.setPrompt("For flash, use volume up key");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
            }
        });

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==1) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("EditText", scanned_textview.getText().toString());
                    clipboard.setPrimaryClip(clip);

                    Toast.makeText(ScanActivity.this, "Copied!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        scanned_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag==1) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("EditText", scanned_textview.getText().toString());
                    clipboard.setPrimaryClip(clip);

                    Toast.makeText(ScanActivity.this, "Copied!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(intentResult.getContents() != null) {
            scanned_textview.setText(intentResult.getContents());
        } else {
            Toast.makeText(this, "OOPS....You didn't scan anything", Toast.LENGTH_SHORT).show();
        }
    }
}