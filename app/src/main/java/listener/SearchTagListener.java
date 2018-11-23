package listener;

import java.util.List;

import bean.Goods;

public interface SearchTagListener {
    void onFinish(List<String> mTagList);
    void onSearchResult(List<Goods> mResultList);
}
