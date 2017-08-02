package Bean;

/**
 * Created by acer on 2017/7/30.
 */

import java.util.ArrayList;

/**
 * 頁簽詳情數據對象
 */



  public   class TabNews{
        public    String  more;
        public ArrayList<ListItem> news;
        public ArrayList<NewsTop> topnews;
    public class ListItem{
        public int id;
        public String title;
        public String listimage;
        public String pubdate;
        public String url;
        public String type;

        @Override
        public String toString() {
            return title+pubdate;
        }
    }
    public class NewsTop{
        public  int id;
        public String pubdate;
        public String title;
        public  String topimage;
        public String url;

        @Override
        public String toString() {
            return title+pubdate;
        }
    }

    @Override
    public String toString() {
        return  news.toString()+topnews.toString();
    }
}

