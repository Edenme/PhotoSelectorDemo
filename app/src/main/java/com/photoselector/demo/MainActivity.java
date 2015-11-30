package com.photoselector.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listView);
    }

    private void setAdapter(List<String> urls) {
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, urls));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10 && resultCode == RESULT_OK) {
            List<String> urlList = data.getStringArrayListExtra("data");
            setAdapter(urlList);
        }
    }

    private static int clickCount = 0;

    public void onClick(View view) {
        if (view.getId() == R.id.buttonSelPhoto) {
            clickCount++;
            int limit = clickCount % 2 == 0 ? 1 : 6;
            Intent intent = new Intent(MainActivity.this, ChoosePhotoActivity.class);
            intent.putExtra(ChoosePhotoActivity.EXTRAS_MAX_SELECT_PIC_COUNT, limit);
            intent.putExtra(ChoosePhotoActivity.EXTRAS_MAX_TIPS, "不能再选了");
            startActivityForResult(intent, 10);
        }
    }
}
