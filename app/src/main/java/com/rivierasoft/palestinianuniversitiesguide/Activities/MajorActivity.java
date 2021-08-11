package com.rivierasoft.palestinianuniversitiesguide.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.rivierasoft.palestinianuniversitiesguide.Adapters.InfoAdapter;
import com.rivierasoft.palestinianuniversitiesguide.Connectivity;
import com.rivierasoft.palestinianuniversitiesguide.Models.Info;
import com.rivierasoft.palestinianuniversitiesguide.Models.Program;
import com.rivierasoft.palestinianuniversitiesguide.OnRVIClickListener;
import com.rivierasoft.palestinianuniversitiesguide.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class MajorActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Info> infoList;
    private Program program;
    private Intent intent;
    private ImageView coverIV, logoIV;
    private TextView nameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getResources();
        Configuration newConfig = new Configuration(res.getConfiguration());
        Locale locale = new Locale("ar");
        newConfig.locale = locale;
        newConfig.setLocale(locale);
        newConfig.setLayoutDirection(locale);
        res.updateConfiguration(newConfig, null);
        setContentView(R.layout.activity_major);

        Connectivity.checkConnection(this);

        intent = getIntent();
        program = (Program) intent.getSerializableExtra("program");

        recyclerView = findViewById(R.id.rv_major);
        coverIV = findViewById(R.id.iv_major_cover);
        logoIV = findViewById(R.id.iv_major_logo);
        nameTV = findViewById(R.id.tv_major);

        Picasso.with(getApplicationContext())
                .load(program.getCover())
                .placeholder(R.drawable.app_icon)
                //.error(R.drawable.ic_broken_image)
                .into(coverIV);

        Picasso.with(getApplicationContext())
                .load(program.getLogo())
                //.placeholder(R.drawable.app_icon)
                //.error(R.drawable.ic_broken_image)
                .into(logoIV);

        nameTV.setText(program.getName());

        infoList = new ArrayList<>();

        infoList.add(new Info(R.drawable.ic_school, "الدرجة الممنوحة", program.getDegree()));
        infoList.add(new Info(R.drawable.ic_apartment, "الكلية / القسم", program.getDepartment()));
        infoList.add(new Info(R.drawable.ic_date, "مدة الدراسة", program.getDuration()));
        if (!program.getProgramType().equals("3"))
            infoList.add(new Info(R.drawable.ic_how_to_reg, "نسبة القبول", program.getRate()));
        if (!program.getPrice().equals(""))
            infoList.add(new Info(R.drawable.ic_invoice, "سعر الساعة", program.getPrice()));
        if (!program.getPlan().equals(""))
            infoList.add(new Info(R.drawable.ic_list_alt, "الخطة الدراسية", "اضغط هنا"));
        if (!program.getLink().equals(""))
            infoList.add(new Info(R.drawable.ic_link, "رابط البرنامج", "اضغط هنا"));

        InfoAdapter infoAdapter = new InfoAdapter(infoList, "m", getApplicationContext(), new OnRVIClickListener() {
            @Override
            public void OnClickListener(int b) {
                if (infoList.get(b).getIcon() == R.drawable.ic_list_alt)
                    startActivity(new Intent(getApplicationContext(), WebActivity.class).putExtra("link", program.getPlan()));
                else if (infoList.get(b).getIcon() == R.drawable.ic_link)
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(program.getLink())));
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(infoAdapter);
    }
}