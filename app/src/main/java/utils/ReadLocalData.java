package utils;

import android.text.TextUtils;

import com.pddtest.android.MyApplication;

import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

import bean.Goods;
import bean.Type;
import listener.SearchTagListener;


public class ReadLocalData {
    //private static CopyOnWriteArrayList<String> mTxtList=new CopyOnWriteArrayList<>();
    private static List<String> mTxtList = new ArrayList<>();

    private static List<List<Type>> mSrcList;
    private static volatile int flag = 0;

    private static List<String> srcTitle = new ArrayList<>();

    private static List<List<String>> wordsDict;

    public static List<String> readFromTxt() {
        if (flag == 0) {
            synchronized (ReadLocalData.class) {
                if (flag == 0) {
                    InputStream path = null;
                    Scanner in = null;
                    try {

                        path = MyApplication.getContext().getAssets().open("goods.txt");
                        in = new Scanner(path, "gbk");
                        while (in.hasNextLine()) {
                            mTxtList.add(in.nextLine());
                        }
                        flag = 1;
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

    public static synchronized List<List<Type>> readSrc() {
        if (mSrcList != null) return mSrcList;
        mSrcList = new ArrayList<>();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mSrcList;
    }

    //根据分词表生成更多词的算法
    private static synchronized List<List<String>> getDict(){
        if(wordsDict!=null)return wordsDict;
        List<List<String>> dict=new ArrayList<>();
        IKAnalyzer analyzer = new IKAnalyzer();
        for (String line : srcTitle) {
            List<String> words = null;
            try {
                words = analyzer.split(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (words == null) break;
            int presize = 0;
            int len=words.size();
            while (presize < words.size()) {
                int i = 0;
                int j = presize;
                presize = words.size();
                for (; i < len- 1; i++) {
                    for (; j < presize; j++) {
                        String pre = words.get(i);
                        String post = words.get(j);
                        if ( !post.contains(pre)&&!pre.contains(post) &&  post.length() + pre.length() < 18) {
                            words.add(post + pre);
                            words.add(pre + post);
                        }
                    }
                }
            }
            dict.add(words);
        }
        analyzer.close();
        wordsDict=dict;
        return dict;
    }

    //根据输入内容实时更新选择菜单
    public static synchronized void searchTag(final String tag, final SearchTagListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> resultList = new ArrayList<>();
                HashMap<String, Integer> dict = new HashMap<>();
                if (TextUtils.isEmpty(tag)) {
                    listener.onFinish(resultList);
                    return;
                }
                if (srcTitle.size() == 0)
                    for (String s : readFromTxt()) {
                        srcTitle.add(s.split("&")[0]);
                    }

                for (List<String> words: getDict()) {
                    for (String word : words) {
                        //System.out.println(word);
                        if (word.startsWith(tag) && !word.equals(tag)) {
                            if (dict.get(word) == null) dict.put(word, 1);
                            else dict.put(word, dict.get(word) + 1);
                        }
                    }
                }
                List<Map.Entry<String, Integer>> list = new ArrayList<>();

                list.addAll(dict.entrySet());
                Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                        return o2.getValue().compareTo(o1.getValue());
                    }
                });
                Iterator<Map.Entry<String, Integer>> it = list.iterator();
                int i = 0;
                while (it.hasNext()) {
                    if (i++ == 10) break;
                    Map.Entry entry = (Map.Entry) it.next();
                    resultList.add(entry.getKey().toString());
                }
//                for (String s : resultList) {
//                    System.out.println(s);
//                }
                listener.onFinish(resultList);
            }
        }).start();

    }

    public static void searchResult(final String tag, final SearchTagListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Goods> mResultList = new ArrayList<>();
                for (int i = 0; i < readFromTxt().size(); i++) {
                    String []lines=mTxtList.get(i).split("&");
                    for(int j=0;j<tag.length();j++){
                        if(lines[0].contains(tag.charAt(j)+"")){
                            mResultList.add(new Goods(i+1,lines[0],lines[1]));
                            //System.out.println("tag:"+tag+"title:"+lines[0]);
                            break;
                        }
                    }
                }
                listener.onSearchResult(mResultList);
            }
        }).start();
    }
}
