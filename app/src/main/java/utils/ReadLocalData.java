package utils;

import com.pddtest.android.MyApplication;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

import bean.Type;


public class ReadLocalData {
    //private static CopyOnWriteArrayList<String> mTxtList=new CopyOnWriteArrayList<>();
    private static List<String> mTxtList=new ArrayList<>();
    private static List<List<Type>> mSrcList;
    private static volatile int flag=0;
    public static List<String> readFromTxt(){
        if(flag==0) {
            synchronized (ReadLocalData.class){
                if(flag==0){
                    InputStream path = null;
                    Scanner in = null;
                    try {

                        path = MyApplication.getContext().getAssets().open("goods.txt");
                        in = new Scanner(path, "gbk");
                        while (in.hasNextLine()) {
                            mTxtList.add(in.nextLine());
                        }
                        flag=1;
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (in != null) in.close();
                            if (path != null) path.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                }
            }


        return mTxtList;
    }

    public static synchronized List<List<Type>> readSrc()  {
        if(mSrcList!=null)return mSrcList;
        mSrcList=new ArrayList<>();
        //File file=new File("file:///android_asset/query/");
        //System.out.println(file.exists());
        try {
            String[] files = MyApplication.getContext().getAssets().list("query");
            //if(file.isDirectory()){
            if (files != null && files.length > 0) {
                for (char i = 'a'; i <= 'j'; i++) {
                    List<Type> tmp = new ArrayList<>();
                    for (String s : files) {
                        if (s.startsWith(i + "")) {
                            tmp.add(new Type("file:///android_asset/query/" + s, s.substring(1, s.length() - 5)));
                        }
                    }
                    mSrcList.add(tmp);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return mSrcList;
    }
}
