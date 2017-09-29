package com.lujinfei.deerdwmap;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.github.lujinfeifly.freejava.http.HttpRet;
import com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.adapter.GbxListAdapter;
import com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.adapter.PathListAdapter;
import com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.bean.AData;
import com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.bean.Path;
import com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.bean.PathMsg;
import com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.storedata.LocalPath;
import com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.storedata.PathData;
import com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.storedata.User;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import deer.milu.freeandroid.swipelistview.SwipeMenuListView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnClickListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener{

    private SwipeMenuListView lvPathList;
    private ListView lvGbxList;
    private PathListAdapter pathListAdapter = null;
    private GbxListAdapter gbxListAdapter = null;

    private ViewFlipper vfLogin;
    private ViewFlipper vfList;
    private ViewFlipper vfWatch;
    private ViewFlipper vfAllgbx;
    private ViewFlipper vfAllimp;
    private ViewFlipper vfSelf;

    private Button btnLinkWatch;
    private List<File> localFileListGbx;

    private TextView tvAccountLogout;
    private AutoCompleteTextView tvLinkCode;

    private NavigationView navigationView;

    private AData data = null;

    private int nowindex = 0;

    private FloatingActionButton fab;
    private TextView tvAccountShow;

    private String linkWatchKey;
    private int fileUploadIndex;

    private LinearLayout llstep1,llstep2,llstep3;

    private Button btnLinkWatchEnd;
    private TextView txtLinkResult;
    private int mCurrentIndex;

    private WebView webView;

    private boolean isloadWebView = false;

    private SwipeRefreshLayout mSwipeLayoutPathList;

    private Button btnDistanceKm, btnDistanceMales;

    private int distanceSwitch = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.hideOverflowMenu();
//        toolbar.setOverflowIcon(null);

        vfLogin = (ViewFlipper)findViewById(R.id.vf_loginout);
        vfList = (ViewFlipper)findViewById(R.id.vf_list);
        vfWatch = (ViewFlipper)findViewById(R.id.vf_watchapp);
        vfAllgbx = (ViewFlipper)findViewById(R.id.vf_allgbx);
        vfAllimp = (ViewFlipper)findViewById(R.id.vf_allimp);
        vfSelf = (ViewFlipper)findViewById(R.id.vf_self);

        tvAccountLogout = (TextView)findViewById(R.id.logout_tvaccount);
        tvLinkCode = (AutoCompleteTextView)findViewById(R.id.link_code);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_LAUNCHER);
//            ComponentName cn = new ComponentName(packageName, className);
//            intent.setComponent(cn);
//            startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button btnLogout = (Button) findViewById(R.id.logout_btnlogoutbutton);
        btnLogout.setOnClickListener(this);

        tvAccountShow = (TextView)navigationView.getHeaderView(0).findViewById(R.id.tv_accountshow);
        lvPathList = (SwipeMenuListView)findViewById(R.id.lv_account_list);

        // step 1. create a MenuCreator
//        SwipeMenuCreator creator = new SwipeMenuCreator() {
//
//            @Override
//            public void create(SwipeMenu menu) {
//                // create "delete" item
//                SwipeMenuItem deleteItem = new SwipeMenuItem(
//                        getApplicationContext());
//                // set item background
//                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
//                        0x3F, 0x25)));
//                // set item width
//                deleteItem.setWidth(200);
//                // set a icon
//                deleteItem.setIcon(R.drawable.ic_delete);
//                // add to menu
//                menu.addMenuItem(deleteItem);
//            }
//        };

//        lvPathList.setMenuCreator(creator);
        lvGbxList = (ListView)findViewById(R.id.lv_filegbx_list);

        btnLinkWatch = (Button)findViewById(R.id.linkwatch_button);
        btnLinkWatch.setOnClickListener(this);
        txtLinkResult = (TextView) findViewById(R.id.txt_link_result);

        llstep1 = (LinearLayout)findViewById(R.id.ll_step1);
        llstep2 = (LinearLayout)findViewById(R.id.ll_step2);
        llstep3 = (LinearLayout)findViewById(R.id.ll_step3);

        btnLinkWatchEnd = (Button)findViewById(R.id.btn_preto2);
        btnLinkWatchEnd.setOnClickListener(this);


        Button btnLinkWatchNext2 = (Button)findViewById(R.id.btn_nextto2);
        btnLinkWatchNext2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                llstep1.setVisibility(View.INVISIBLE);
                llstep2.setVisibility(View.VISIBLE);
            }
        });



        data = new AData();
        pathListAdapter = new PathListAdapter(MainActivity.this, getPathList(data));
        lvPathList.setAdapter(pathListAdapter);

        localFileListGbx = new ArrayList<File>();
        gbxListAdapter = new GbxListAdapter(MainActivity.this, localFileListGbx);
        lvGbxList.setAdapter(gbxListAdapter);

        lvPathList.setOnItemClickListener(this);
        lvGbxList.setOnItemClickListener(this);

        mSwipeLayoutPathList = (SwipeRefreshLayout) findViewById(R.id.swipe_ly);
        mSwipeLayoutPathList.setOnRefreshListener(this);

        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);


        btnDistanceKm = (Button) findViewById(R.id.btn_sw_km);
        btnDistanceMales = (Button) findViewById(R.id.btn_sw_miles);

        btnDistanceKm.setOnClickListener(this);
        btnDistanceMales.setOnClickListener(this);

        mCurrentIndex = R.id.vf_list; //表示为第一页
        initView();
    }

    private List<Path> getPathList(AData data) {
        if(data == null) {
            return null;
        }

        List<Path> list = data.getRows();
        for(int i=0;i<list.size(); i++) {
            list.get(i).getTable().setDistanceUnit(distanceSwitch);
        }

        return data.getRows();
    }

    private void initView() {

        navigationView.setCheckedItem(R.id.nav_allpath);

        String strData = PathData.getName(this);
        try {
            data = new AData(strData);
            pathListAdapter.setData(getPathList(data));
        }catch(JSONException ex) {
            ex.printStackTrace();
        }
        if(data.getRows() != null) {
            handler.obtainMessage(0, null).sendToTarget();
        }

        // 账号显示
        String acc = User.getName(this);
        tvAccountShow.setText(acc);
        tvAccountLogout.setText(getText(R.string.line_account) + acc);

        distanceSwitch = User.getSwitchDistance(this);
        switch (distanceSwitch) {
            case 1:
                btnDistanceMales.setBackgroundResource(R.drawable.btn_bottom_distance_selected);
                btnDistanceKm.setBackgroundResource(R.drawable.btn_bottom_distance);
                break;
            case 2:
                btnDistanceMales.setBackgroundResource(R.drawable.btn_bottom_distance);
                btnDistanceKm.setBackgroundResource(R.drawable.btn_bottom_distance_selected);
                break;
        }
    }



    private void showMsg(String msg) {
        Snackbar.make(fab, msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void invisibleAllPage() {
        vfSelf.setVisibility(View.GONE);
        vfAllgbx.setVisibility(View.GONE);
        vfAllimp.setVisibility(View.GONE);
        vfLogin.setVisibility(View.GONE);
        vfList.setVisibility(View.GONE);
        vfWatch.setVisibility(View.GONE);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        invisibleAllPage();
        mCurrentIndex = id;
        switch(id) {
            case R.id.nav_manage:
                vfLogin.setVisibility(View.VISIBLE);
                break;
            case R.id.nav_allpath:
                vfList.setVisibility(View.VISIBLE);
                break;
            case R.id.nav_watchapp: // 连接手机，需要请求进行
                new Thread(networkTaskLinkPre).start();
                initLinkWatchView();
                break;
            case R.id.nav_localgbx:
                loadLocalGbx();
                vfAllgbx.setVisibility(View.VISIBLE);
                break;
            case R.id.nav_help:
                if(!isloadWebView) {
                    webView.loadUrl("http://123.59.100.61:8082/wuye/ReadMe.html");
                    webView.setWebViewClient(new WebViewClient());
                    isloadWebView = true;
                }
                vfAllimp.setVisibility(View.VISIBLE);
                break;
            case R.id.nav_self:
                vfSelf.setVisibility(View.VISIBLE);
                break;
            default:

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initLinkWatchView() {
        vfWatch.setVisibility(View.VISIBLE);
        llstep1.setVisibility(View.VISIBLE);
        llstep2.setVisibility(View.INVISIBLE);
        llstep3.setVisibility(View.INVISIBLE);
    }

    private void linkWatchView(int ret) {
        llstep2.setVisibility(View.INVISIBLE);
        llstep3.setVisibility(View.VISIBLE);
        switch(ret) {
            case 0:
                txtLinkResult.setText(getText(R.string.link_watch_last_step));
                btnLinkWatchEnd.setVisibility(View.INVISIBLE);
                break;
            case 2:
                txtLinkResult.setText(getText(R.string.link_watch_already_linked));
                btnLinkWatchEnd.setVisibility(View.VISIBLE);
                break;
            case 3:
                txtLinkResult.setText(getText(R.string.link_watch_time_exceeded));
                btnLinkWatchEnd.setVisibility(View.VISIBLE);
                break;
            case 1:

            default:
                txtLinkResult.setText(getText(R.string.link_watch_failed));
                btnLinkWatchEnd.setVisibility(View.VISIBLE);
                break;
        }
    }


    private void loadLocalGbx() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }else {
            readGpx();
        }
    }

    private void readGpx() {
        localFileListGbx.clear();
        localFileListGbx.addAll(LocalPath.getSixFootGbx(this));

        gbxListAdapter.notifyDataSetChanged();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {// 读存储权限
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                readGpx();
            } else {
                // Permission Denied

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.logout_btnlogoutbutton:
                User.setName(this,"","");
                User.setSession(this,"");
                exitToLogin();
                break;
            case R.id.linkwatch_button:
                hideKeyBoard();
                new Thread(networkTaskLink).start();
                break;
            case R.id.btn_preto2:
                llstep3.setVisibility(View.INVISIBLE);
                llstep2.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_sw_km:
                if(distanceSwitch == 1) {
                    distanceSwitch = 2;
                    User.setSwitchDistance(this, 2);
                    btnDistanceMales.setBackgroundResource(R.drawable.btn_bottom_distance);
                    btnDistanceKm.setBackgroundResource(R.drawable.btn_bottom_distance_selected);
                    pathListAdapter.setData(getPathList(data));
                    pathListAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.btn_sw_miles:
                if(distanceSwitch == 2) {
                    distanceSwitch = 1;
                    User.setSwitchDistance(this, 1);
                    btnDistanceMales.setBackgroundResource(R.drawable.btn_bottom_distance_selected);
                    btnDistanceKm.setBackgroundResource(R.drawable.btn_bottom_distance);
                    pathListAdapter.setData(getPathList(data));
                    pathListAdapter.notifyDataSetChanged();
                }
                break;
            default:
        }
    }

    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            HttpRet abc = httpFunc.dwMaplogin("", "");
            Log.i("www", abc.toString());

            HttpRet  ret  = httpFunc.dwMapGetList();


            try {
                data = new AData(ret.getmRetContent());
                pathListAdapter.setData(getPathList(data));
            }catch(JSONException ex) {
                ex.printStackTrace();
            }
            if(data.getRows() != null) {
                handler.obtainMessage(0, null).sendToTarget();
            }

        }
    };

    Runnable networkTaskGetList = new Runnable() {

        @Override
        public void run() {
            HttpRet  ret  = httpFunc.dwMapGetList();


            try {
                data = new AData(ret.getmRetContent());
                pathListAdapter.setData(getPathList(data));
            }catch(JSONException ex) {
                ex.printStackTrace();
            }
            if(data.getRows() != null) {
                handler.obtainMessage(0, null).sendToTarget();
            }

        }
    };

    // 请求连接手机的准备
    Runnable networkTaskLinkPre = new Runnable() {

        @Override
        public void run() {
        HttpRet ret = httpFunc.dwMapLinkWatchPre();
        Log.i("www", ret.toString());
        if(ret.getmRetCode() == 200) {
            Document doc = Jsoup.parse(ret.getmRetContent());
            Elements contents = doc.getElementsByAttributeValue("name", "authenticity_token");
            Element content = contents.get(0);
            linkWatchKey = content.attr("value");
        }

        handler.obtainMessage(2, "").sendToTarget();

        }
    };

    // 连接手机
    Runnable networkTaskLink = new Runnable() {

        @Override
        public void run() {
            HttpRet ret = httpFunc.dwMapLinkWatch(linkWatchKey, tvLinkCode.getText().toString());
            Log.i("www", ret.toString());

            int pass = 1;

            if(ret.getmRetCode() == 200) {
                if(ret.getmRetContent().contains("Link code not found")) {
                    pass = 1; // 没有找到此链接码
                } else if(ret.getmRetContent().contains("Watch is already linked")) {
                    pass = 2;  // 已经连接
                } else if(ret.getmRetContent().contains("Link code has expired")) {
                    pass = 3;  // 连接码已超时
                }
            }else if (ret.getmRetCode() == 302 && ret.getmRetContent().contains("me")) {
                pass = 0;
            }

            handler.obtainMessage(3, pass).sendToTarget();

        }
    };

    Runnable networkTaskSetDefault = new Runnable() {
        @Override
        public void run() {

            HttpRet ret = httpFunc.dwMapSetDefault(data.getRows().get(nowindex).getTable().getId());
            if(ret.getmRetCode() == 200) {
                int size = data.getRows().size();
                for(int i = 0;i<size;i++) {
                    data.getRows().get(i).getTable().setCurrent_sync(false);
                }
                data.getRows().get(nowindex).getTable().setCurrent_sync(true);

                handler.obtainMessage(1, null).sendToTarget();
            }
        }
    };

    Runnable networkTaskUpload = new Runnable() {
        @Override
        public void run() {
            File file = localFileListGbx.get(fileUploadIndex);

            /// xml parse begin
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

            org.w3c.dom.Document document = null;
            try {
                DocumentBuilder builder = builderFactory.newDocumentBuilder();
                document = builder.parse(file);
            } catch (ParserConfigurationException e) {
                handler.obtainMessage(4, 2).sendToTarget();
                return;
            } catch (SAXException e) {
                handler.obtainMessage(4, 2).sendToTarget();
                return;
            } catch (IOException e) {
                handler.obtainMessage(4, 2).sendToTarget();
                return;
            }

            NodeList list = document.getChildNodes();
            NodeList nodelist = list.item(0).getChildNodes();
            int length = nodelist.getLength();
            boolean ishave = false;
            out1:   for(int i=0;i<length;i++) {
                if(nodelist.item(i).getNodeName().equals("trk")) {
                    NodeList nodelist2 = nodelist.item(i).getChildNodes();
                    int length2 = nodelist2.getLength();
                    for(int j=0;j<length2;j++) {
                        Node node = nodelist2.item(j);
                        String name = node.getNodeName();
                        if(node.getNodeName().equals("name")) {
                            String cccc = nodelist2.item(j).getTextContent();
                            System.out.println(cccc);
                            ishave = true;
                            break out1;
                        }

                    }
                    // 没有找到
                }
            }

            if(!ishave) {
                try {
                    insert(file.getAbsolutePath(), 0, file.getName().substring(0, file.getName().indexOf('.')));
                }catch(Exception ex) {

                }
            }

            //// xml parse end

            HttpRet ret = httpFunc.dwMapUpload(file);
            int data = 1;
            if(ret.getmRetCode() == 200) {
                data = 0;
            } else {
                data = 1;
            }
            handler.obtainMessage(4, data).sendToTarget();
        }
    };


    public static boolean insert(String fileName,long pos,String insertContent) throws IOException
    {
        FileWriter writer = new FileWriter(fileName+"-tmp");
        FileReader reader = new FileReader(fileName);
        BufferedReader br = new BufferedReader(reader);
        BufferedWriter wr = new BufferedWriter(writer);
        String str = null;
        boolean havedo =false;
        while((str = br.readLine()) != null) {
            if(!havedo && str.contains("<trk>")) {
                havedo = true;
                int index = str.indexOf("<trk>");
                index += 4;
                String x = str.substring(0, index+1) + "<name>"+ insertContent +"</name>" + str.substring(index+1);
                wr.write(x);
                continue;
            }
            wr.write(str);
        }

        br.close();
        reader.close();
        wr.close();
        writer.close();

        File oldFile  = new File(fileName);
        oldFile.delete();
        File newFile  = new File(fileName+"-tmp");
        return newFile.renameTo(oldFile);

    }

    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            int ret = 1;
            switch (msg.what) {
                case 0:
                    pathListAdapter.notifyDataSetChanged();
                    if(mCurrentIndex == R.id.vf_list) {
                        vfList.setVisibility(View.VISIBLE);
                        vfLogin.setVisibility(View.GONE);
                        mSwipeLayoutPathList.setRefreshing(false);
                    }
                    MobclickAgent.onEvent(MainActivity.this, "SET_SYNC");
                    break;
                case 1:
                    cancleLoading();
                    pathListAdapter.notifyDataSetChanged();
                    break;
                case 2: // 准备好同步手机
                    btnLinkWatch.setEnabled(true);
                    break;
                case 3: // 连接手机结果
                    ret = (int)msg.obj;
                    linkWatchView(ret);
                    break;
                case 4: // 上传结果
                    ret = (int)msg.obj;
                    switch(ret) {
                        case 0:
                            cancleLoading();
                            MobclickAgent.onEvent(MainActivity.this, "UPLOAD_PATH_OK");
                            new Thread(networkTaskGetList).start();
                            break;
                        case 1:
                            cancelLoadingWithError();
                            MobclickAgent.onEvent(MainActivity.this, "UPLOAD_PATH_FAILED");
                            break;
                        default:
                            cancelLoadingWithError();
                            MobclickAgent.onEvent(MainActivity.this, "UPLOAD_PATH_FAILED");
                    }
                    MobclickAgent.onEvent(MainActivity.this, "UPLOAD_PATH");
                    break;
            }

        }
    };

    private void exitToLogin() {
        Intent loginInfo = new Intent(this, LoginActivity.class);
        loginInfo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginInfo);
        this.finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        switch(parent.getId()) {
            case R.id.lv_filegbx_list: // 列表
                showOperDialog(1, this.getString(R.string.tip_upload_title),
                        getString(R.string.tip_upload_Msg1) +
                                localFileListGbx.get(position).getName() +
                                getString(R.string.tip_upload_Msg2),
                        getString(R.string.action_cancel),
                        getString(R.string.action_upload),
                        null,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                fileUploadIndex = position;
                                showLoading(getString(R.string.tip_uploading));
                                new Thread(networkTaskUpload).start();
                            }
                        });
                break;
            case R.id.lv_account_list: // 路径列表
                showOperDialog(1, this.getString(R.string.tip_setdefault_title),
                        getString(R.string.tip_setdefault_Msg1) +
                                data.getRows().get(position).getTable().getName() +
                                getString(R.string.tip_setdefault_Msg2),
                        getString(R.string.action_cancel),
                        getString(R.string.action_sure),
                        null,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showLoading(getString(R.string.tip_setting));
                                nowindex = position;
                                new Thread(networkTaskSetDefault).start();
                            }
                        });
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        new Thread(networkTaskGetList).start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mSwipeLayoutPathList.isRefreshing()) {
                    mSwipeLayoutPathList.setRefreshing(false);
                }
            }
        }, 5000);
    }
}
