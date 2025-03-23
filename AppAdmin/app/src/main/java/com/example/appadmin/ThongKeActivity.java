package com.example.appadmin;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appadmin.api.API;
import com.example.appadmin.databinding.ActivityThongKeBinding;
import com.example.appadmin.model.DataResponse;
import com.example.appadmin.model.ThongKe;
import com.example.appadmin.model.ThongKeThang;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongKeActivity extends AppCompatActivity {
    private ActivityThongKeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityThongKeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ActionToolBar();
        getDataChart();
        settingBarChart();
    }

    private void settingBarChart() {
        binding.barchart.getDescription().setEnabled(false);
        binding.barchart.setDrawValueAboveBar(false);
        XAxis xAxis = binding.barchart.getXAxis();
        xAxis.setAxisMinimum(1);
        xAxis.setAxisMaximum(12);
        YAxis yAxisright = binding.barchart.getAxisRight();
        yAxisright.setAxisMinimum(0);
        YAxis yAxisleft = binding.barchart.getAxisLeft();
        yAxisleft.setAxisMinimum(0);
    }

    private void getDataChart() {
        List<PieEntry> listData = new ArrayList<>();
        API.apiService.getThongKe().enqueue(new Callback<DataResponse<List<ThongKe>>>() {
            @Override
            public void onResponse(Call<DataResponse<List<ThongKe>>> call, Response<DataResponse<List<ThongKe>>> response) {
                if (response.isSuccessful()){
                    for (int i= 0; i < response.body().getResult().size(); i++){
                        String tensp = response.body().getResult().get(i).getTensanpham();
                        int tong = response.body().getResult().get(i).getTong();
                        listData.add(new PieEntry(tong, tensp));
                    }
                    PieDataSet pieDataSet = new PieDataSet(listData, "Thống kê");
                    PieData data = new PieData();
                    data.setDataSet(pieDataSet);
                    data.setValueTextSize(12f);
                    data.setValueFormatter(new PercentFormatter(binding.piechart));
                    pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

                    binding.piechart.setData(data);
                    binding.piechart.animateXY(2000, 2000);
                    binding.piechart.setUsePercentValues(true);
                    binding.piechart.getDescription().setEnabled(false);
                    binding.piechart.invalidate();

                }
            }

            @Override
            public void onFailure(Call<DataResponse<List<ThongKe>>> call, Throwable t) {

            }
        });
    }

    private void ActionToolBar() {
        setSupportActionBar(binding.toolbarThongKe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarThongKe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_thongke, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.tkDoanhThu){
            getThongKeThang();
        } else if (id == R.id.tkmathang) {
            getDataChart();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getThongKeThang() {
        binding.barchart.setVisibility(View.VISIBLE);
        binding.piechart.setVisibility(View.GONE);
        API.apiService.getThongKeThang().enqueue(new Callback<DataResponse<List<ThongKeThang>>>() {
            @Override
            public void onResponse(Call<DataResponse<List<ThongKeThang>>> call, Response<DataResponse<List<ThongKeThang>>> response) {
                if (response.isSuccessful()){
                    List<BarEntry> listdata = new ArrayList<>();
                    for(int i = 0; i< response.body().getResult().size();i++){
                        String tongTien = response.body().getResult().get(i).getTongtienthang();
                        String thang = response.body().getResult().get(i).getThang();
                        listdata.add(new BarEntry(Integer.parseInt(thang), Float.parseFloat(tongTien)));
                    }
                    BarDataSet barDataSet = new BarDataSet(listdata, "Thống kê");
                    barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                    barDataSet.setValueTextSize(14f);
                    barDataSet.setValueTextColor(Color.RED);

                    BarData data = new BarData(barDataSet);
                    binding.barchart.animateXY(2000, 2000);
                    binding.barchart.setData(data);
                    binding.barchart.invalidate();

                }
            }

            @Override
            public void onFailure(Call<DataResponse<List<ThongKeThang>>> call, Throwable t) {

            }
        });
    }
}