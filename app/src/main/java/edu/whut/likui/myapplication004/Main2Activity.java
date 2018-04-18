package edu.whut.likui.myapplication004;

        import android.Manifest;
        import android.content.pm.PackageManager;
        import android.support.annotation.NonNull;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.baidu.location.BDLocation;
        import com.baidu.location.BDLocationListener;
        import com.baidu.location.LocationClient;

        import java.util.ArrayList;
        import java.util.List;

public class Main2Activity extends AppCompatActivity {
public LocationClient mLocationClient;
private TextView positionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient=new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        setContentView(R.layout.activity_main2);
        positionText=findViewById(R.id.position_text_view);
        List<String > permissionList=new ArrayList<>();
        if (ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()) {
            String[] permissions=permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(Main2Activity.this,permissions,1);
        }else {
            requestLocation();
        }
    }
    private void requestLocation(){
        mLocationClient.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch (requestCode){
        case 1:
            if (grantResults.length>0){
                for (int result:grantResults){
                    if (result!=PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,"必须同一所有权限！",Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                }
                requestLocation();
            }else {
                Toast.makeText(this,"发生未知错误！",Toast.LENGTH_SHORT).show();
                finish();
            }
            break;
            default:
    }
    }
    public class MyLocationListener implements BDLocationListener{
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            StringBuilder currenPosition =new StringBuilder();
            currenPosition.append("时间："+bdLocation.getTime()+"\n");
            currenPosition.append("维度：").append(bdLocation.getLatitude()).append("\n");

            currenPosition.append("经度：").append(bdLocation.getLongitude()).append("\n");
            currenPosition.append("国家：").append(bdLocation.getCountry()).append("\n");
            currenPosition.append("省：").append(bdLocation.getCity()).append("\n");
            currenPosition.append("市：").append(bdLocation.getDistrict()).append("\n");
            currenPosition.append("街道：").append(bdLocation.getStreet()).append("\n");
            currenPosition.append("定位方式：");
            if (bdLocation.getLocType()==BDLocation.TypeGpsLocation){
                currenPosition.append("GPS");
            }else if (bdLocation.getLocType()==BDLocation.TypeNetWorkLocation){
                currenPosition.append("网络");
            }
            Log.d("描述：",currenPosition.toString());
            positionText.setText(currenPosition);
        }
    }
}
