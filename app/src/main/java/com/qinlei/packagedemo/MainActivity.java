package com.qinlei.packagedemo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView tvDomain;
    private TextView tvPermission;
    private TextView tvSignature;
    private TextView tvChannel;
    private TextView tvApplicationId;
    private TextView tvResource;

    String DOMAIN = BuildConfig.DOMAIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        tvDomain.setText(DOMAIN);
        tvPermission.setText("");
        getUsesPermission(getPackageName());
        tvSignature.setText(getSHA1Signature(this));
        tvChannel.setText(getChannelName(this));
        tvApplicationId.setText(getPackageName());
        tvResource.setText(R.string.app_name);
    }

    private void initView() {
        tvDomain = findViewById(R.id.tv_domain);
        tvPermission = findViewById(R.id.tv_permission);
        tvSignature = findViewById(R.id.tv_signature);
        tvChannel = findViewById(R.id.tv_channel);
        tvApplicationId = findViewById(R.id.tv_applicationId);
        tvResource = findViewById(R.id.tv_resource);
    }

    private void getUsesPermission(String packageName) {
        try {
            PackageManager packageManager = this.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            String[] usesPermissionsArray = packageInfo.requestedPermissions;
            for (int i = 0; i < usesPermissionsArray.length; i++) {

                //得到每个权限的名字,如:android.permission.INTERNET
                String usesPermissionName = usesPermissionsArray[i];
                System.out.println("usesPermissionName=" + usesPermissionName);

                //通过usesPermissionName获取该权限的详细信息
                PermissionInfo permissionInfo = packageManager.getPermissionInfo(usesPermissionName, 0);

                //获得该权限属于哪个权限组,如:网络通信
                PermissionGroupInfo permissionGroupInfo = packageManager.getPermissionGroupInfo(permissionInfo.group, 0);
                System.out.println("permissionGroup=" + permissionGroupInfo.loadLabel(packageManager).toString());

                //获取该权限的标签信息,比如:完全的网络访问权限
                String permissionLabel = permissionInfo.loadLabel(packageManager).toString();
                System.out.println("permissionLabel=" + permissionLabel);

                //获取该权限的详细描述信息,比如:允许该应用创建网络套接字和使用自定义网络协议
                //浏览器和其他某些应用提供了向互联网发送数据的途径,因此应用无需该权限即可向互联网发送数据.
                String permissionDescription = permissionInfo.loadDescription(packageManager).toString();
                System.out.println("permissionDescription=" + permissionDescription);
                System.out.println("===========================================");
            }

        } catch (Exception e) {
            // TODO: handle exception
        }


    }

    public String getSHA1Signature(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);

            byte[] cert = info.signatures[0].toByteArray();

            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            return hexString.toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取渠道名
     *
     * @param context 此处习惯性的设置为activity，实际上context就可以
     * @return 如果没有获取成功，那么返回值为空
     */
    public static String getChannelName(Context context) {
        if (context == null) {
            return null;
        }
        String channelName = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                //注意此处为ApplicationInfo，因为友盟设置的meta-data是在application标签中
                ApplicationInfo applicationInfo = packageManager.
                        getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        //这里的UMENG_CHANNEL要与manifest中的配置文件标识一致
                        channelName = String.valueOf(applicationInfo.metaData.get("UMENG_CHANNEL"));
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelName;
    }
}
