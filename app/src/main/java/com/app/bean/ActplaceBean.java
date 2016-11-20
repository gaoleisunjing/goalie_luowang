package com.app.bean;

import java.util.List;

/**
 * Created by LWL on 2016/9/28.
 */
public class ActplaceBean {

    /**
     * err_code : 0
     * msg :
     * data : {"items":[{"place_id":100134,"name":"TADA方舟","name_en":"TADA","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100134/MTAwMTM0.jpg","distance":"0.020945","province_id":"103295","city_id":"103296","city_name":"台湾","fav":"9","comm":"0","type_id":"1","type_name":"Livehouse","lng":"116.404000","lat":"39.915000"},{"place_id":100472,"name":"中山公园音乐堂","name_en":"Forbidden City Concert Hall","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100472/MTAwNDcy.jpg","distance":"0.345627","province_id":"100001","city_id":"100002","city_name":"北京","fav":"6","comm":"0","type_id":"3","type_name":"音乐厅","lng":"116.401252","lat":"39.917524"},{"place_id":100252,"name":"Old What Bar","name_en":"Old What Bar","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100252/MTAwMjUy.jpg","distance":"0.918169","province_id":"100001","city_id":"100002","city_name":"北京","fav":"2","comm":"3","type_id":"1","type_name":"Livehouse","lng":"116.397833","lat":"39.921984"},{"place_id":100382,"name":"北京音乐厅","name_en":"Beijing Concert Hall","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100382/MTAwMzgy.jpg","distance":"1.295968","province_id":"100001","city_id":"100002","city_name":"北京","fav":"3","comm":"0","type_id":"3","type_name":"音乐厅","lng":"116.389096","lat":"39.912554"},{"place_id":100005,"name":"XP俱乐部（小萍）","name_en":"XP","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100005/MTAwMDA1.jpg","distance":"2.722955","province_id":"100001","city_id":"100002","city_name":"北京","fav":"8","comm":"1","type_id":"1","type_name":"Livehouse","lng":"116.402369","lat":"39.939600"},{"place_id":100244,"name":"黄昏黎明俱乐部","name_en":"Dusk Dawn Club","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100244/MTAwMjQ0.jpg","distance":"2.806111","province_id":"100001","city_id":"100002","city_name":"北京","fav":"20","comm":"7","type_id":"1","type_name":"Livehouse","lng":"116.416910","lat":"39.938312"},{"place_id":100313,"name":"地壳吧","name_en":"DIQIAOBA","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100313/MTAwMzEz.jpg","distance":"3.024909","province_id":"101990","city_id":"102115","city_name":"东莞","fav":"0","comm":"1","type_id":"1","type_name":"Livehouse","lng":"116.421885","lat":"39.938574"},{"place_id":100152,"name":"69cafe","name_en":"69cafe","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100152/MTAwMTUy.jpg","distance":"3.082716","province_id":"100001","city_id":"100002","city_name":"北京","fav":"16","comm":"0","type_id":"1","type_name":"Livehouse","lng":"116.409458","lat":"39.942528"},{"place_id":100007,"name":"江湖酒吧","name_en":"jianghu","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100007/MTAwMDA3.jpg","distance":"3.129671","province_id":"100001","city_id":"100002","city_name":"北京","fav":"22","comm":"1","type_id":"1","type_name":"Livehouse","lng":"116.414227","lat":"39.942140"},{"place_id":100002,"name":"愚公移山","name_en":"yugong","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100002/MTAwMDAy.jpg","distance":"3.178815","province_id":"100001","city_id":"100002","city_name":"北京","fav":"81","comm":"3","type_id":"1","type_name":"Livehouse","lng":"116.422262","lat":"39.940004"},{"place_id":100521,"name":"Modernsky Lab","name_en":"Modernsky Lab","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100521/5628550cba6d9.jpg","distance":"3.335495","province_id":"100001","city_id":"100002","city_name":"北京","fav":"21","comm":"0","type_id":"4","type_name":"展馆","lng":"116.440549","lat":"39.925521"},{"place_id":100149,"name":"蜗牛的家","name_en":"woniudejia","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100149/MTAwMTQ5.jpg","distance":"3.395875","province_id":"100001","city_id":"100002","city_name":"北京","fav":"17","comm":"0","type_id":"1","type_name":"Livehouse","lng":"116.416334","lat":"39.944141"},{"place_id":100592,"name":"驼队餐吧","name_en":"Caravan","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100592/57243083b00fd.jpg","distance":"3.417826","province_id":"100001","city_id":"100002","city_name":"北京","fav":"4","comm":"0","type_id":"5","type_name":"餐吧","lng":"116.443624","lat":"39.918882"},{"place_id":100253,"name":"坛酒吧","name_en":"Tan","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100253/MTAwMjUz.jpg","distance":"3.546054","province_id":"100001","city_id":"100002","city_name":"北京","fav":"1","comm":"0","type_id":"1","type_name":"Livehouse","lng":"116.405524","lat":"39.946997"},{"place_id":100282,"name":"DADA BAR","name_en":"DADA BAR","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100282/MTAwMjgy.jpg","distance":"3.546054","province_id":"100001","city_id":"100002","city_name":"北京","fav":"4","comm":"0","type_id":"1","type_name":"Livehouse","lng":"116.405524","lat":"39.946997"},{"place_id":100146,"name":"蓝溪酒吧","name_en":"lanxi","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100146/MTAwMTQ2.jpg","distance":"3.595919","province_id":"100001","city_id":"100002","city_name":"北京","fav":"4","comm":"0","type_id":"1","type_name":"Livehouse","lng":"116.400264","lat":"39.947350"},{"place_id":100003,"name":"MAO LIVE HOUSE","name_en":"MAO LIVE HOUSE","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100003/MTAwMDAz.jpg","distance":"3.613653","province_id":"100001","city_id":"100002","city_name":"北京","fav":"53","comm":"0","type_id":"1","type_name":"Livehouse","lng":"116.409844","lat":"39.947306"},{"place_id":100009,"name":"疆进酒","name_en":"jiangjinjiu","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100009/MTAwMDA5.jpg","distance":"3.640761","province_id":"100001","city_id":"100002","city_name":"北京","fav":"3","comm":"1","type_id":"1","type_name":"Livehouse","lng":"116.401900","lat":"39.947837"},{"place_id":100258,"name":"Siif如果酒吧","name_en":"Siif如果酒吧","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100258/MTAwMjU4.jpg","distance":"3.920975","province_id":"100001","city_id":"100002","city_name":"北京","fav":"7","comm":"0","type_id":"1","type_name":"Livehouse","lng":"116.409181","lat":"39.950155"},{"place_id":100388,"name":"远方酒吧","name_en":"Là-bas","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100388/MTAwMzg4.jpg","distance":"4.011920","province_id":"100001","city_id":"100002","city_name":"北京","fav":"11","comm":"0","type_id":"8","type_name":"酒吧","lng":"116.417848","lat":"39.949580"}],"pages":3}
     */

    private int err_code;
    private String msg;
    /**
     * items : [{"place_id":100134,"name":"TADA方舟","name_en":"TADA","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100134/MTAwMTM0.jpg","distance":"0.020945","province_id":"103295","city_id":"103296","city_name":"台湾","fav":"9","comm":"0","type_id":"1","type_name":"Livehouse","lng":"116.404000","lat":"39.915000"},{"place_id":100472,"name":"中山公园音乐堂","name_en":"Forbidden City Concert Hall","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100472/MTAwNDcy.jpg","distance":"0.345627","province_id":"100001","city_id":"100002","city_name":"北京","fav":"6","comm":"0","type_id":"3","type_name":"音乐厅","lng":"116.401252","lat":"39.917524"},{"place_id":100252,"name":"Old What Bar","name_en":"Old What Bar","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100252/MTAwMjUy.jpg","distance":"0.918169","province_id":"100001","city_id":"100002","city_name":"北京","fav":"2","comm":"3","type_id":"1","type_name":"Livehouse","lng":"116.397833","lat":"39.921984"},{"place_id":100382,"name":"北京音乐厅","name_en":"Beijing Concert Hall","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100382/MTAwMzgy.jpg","distance":"1.295968","province_id":"100001","city_id":"100002","city_name":"北京","fav":"3","comm":"0","type_id":"3","type_name":"音乐厅","lng":"116.389096","lat":"39.912554"},{"place_id":100005,"name":"XP俱乐部（小萍）","name_en":"XP","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100005/MTAwMDA1.jpg","distance":"2.722955","province_id":"100001","city_id":"100002","city_name":"北京","fav":"8","comm":"1","type_id":"1","type_name":"Livehouse","lng":"116.402369","lat":"39.939600"},{"place_id":100244,"name":"黄昏黎明俱乐部","name_en":"Dusk Dawn Club","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100244/MTAwMjQ0.jpg","distance":"2.806111","province_id":"100001","city_id":"100002","city_name":"北京","fav":"20","comm":"7","type_id":"1","type_name":"Livehouse","lng":"116.416910","lat":"39.938312"},{"place_id":100313,"name":"地壳吧","name_en":"DIQIAOBA","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100313/MTAwMzEz.jpg","distance":"3.024909","province_id":"101990","city_id":"102115","city_name":"东莞","fav":"0","comm":"1","type_id":"1","type_name":"Livehouse","lng":"116.421885","lat":"39.938574"},{"place_id":100152,"name":"69cafe","name_en":"69cafe","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100152/MTAwMTUy.jpg","distance":"3.082716","province_id":"100001","city_id":"100002","city_name":"北京","fav":"16","comm":"0","type_id":"1","type_name":"Livehouse","lng":"116.409458","lat":"39.942528"},{"place_id":100007,"name":"江湖酒吧","name_en":"jianghu","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100007/MTAwMDA3.jpg","distance":"3.129671","province_id":"100001","city_id":"100002","city_name":"北京","fav":"22","comm":"1","type_id":"1","type_name":"Livehouse","lng":"116.414227","lat":"39.942140"},{"place_id":100002,"name":"愚公移山","name_en":"yugong","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100002/MTAwMDAy.jpg","distance":"3.178815","province_id":"100001","city_id":"100002","city_name":"北京","fav":"81","comm":"3","type_id":"1","type_name":"Livehouse","lng":"116.422262","lat":"39.940004"},{"place_id":100521,"name":"Modernsky Lab","name_en":"Modernsky Lab","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100521/5628550cba6d9.jpg","distance":"3.335495","province_id":"100001","city_id":"100002","city_name":"北京","fav":"21","comm":"0","type_id":"4","type_name":"展馆","lng":"116.440549","lat":"39.925521"},{"place_id":100149,"name":"蜗牛的家","name_en":"woniudejia","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100149/MTAwMTQ5.jpg","distance":"3.395875","province_id":"100001","city_id":"100002","city_name":"北京","fav":"17","comm":"0","type_id":"1","type_name":"Livehouse","lng":"116.416334","lat":"39.944141"},{"place_id":100592,"name":"驼队餐吧","name_en":"Caravan","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100592/57243083b00fd.jpg","distance":"3.417826","province_id":"100001","city_id":"100002","city_name":"北京","fav":"4","comm":"0","type_id":"5","type_name":"餐吧","lng":"116.443624","lat":"39.918882"},{"place_id":100253,"name":"坛酒吧","name_en":"Tan","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100253/MTAwMjUz.jpg","distance":"3.546054","province_id":"100001","city_id":"100002","city_name":"北京","fav":"1","comm":"0","type_id":"1","type_name":"Livehouse","lng":"116.405524","lat":"39.946997"},{"place_id":100282,"name":"DADA BAR","name_en":"DADA BAR","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100282/MTAwMjgy.jpg","distance":"3.546054","province_id":"100001","city_id":"100002","city_name":"北京","fav":"4","comm":"0","type_id":"1","type_name":"Livehouse","lng":"116.405524","lat":"39.946997"},{"place_id":100146,"name":"蓝溪酒吧","name_en":"lanxi","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100146/MTAwMTQ2.jpg","distance":"3.595919","province_id":"100001","city_id":"100002","city_name":"北京","fav":"4","comm":"0","type_id":"1","type_name":"Livehouse","lng":"116.400264","lat":"39.947350"},{"place_id":100003,"name":"MAO LIVE HOUSE","name_en":"MAO LIVE HOUSE","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100003/MTAwMDAz.jpg","distance":"3.613653","province_id":"100001","city_id":"100002","city_name":"北京","fav":"53","comm":"0","type_id":"1","type_name":"Livehouse","lng":"116.409844","lat":"39.947306"},{"place_id":100009,"name":"疆进酒","name_en":"jiangjinjiu","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100009/MTAwMDA5.jpg","distance":"3.640761","province_id":"100001","city_id":"100002","city_name":"北京","fav":"3","comm":"1","type_id":"1","type_name":"Livehouse","lng":"116.401900","lat":"39.947837"},{"place_id":100258,"name":"Siif如果酒吧","name_en":"Siif如果酒吧","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100258/MTAwMjU4.jpg","distance":"3.920975","province_id":"100001","city_id":"100002","city_name":"北京","fav":"7","comm":"0","type_id":"1","type_name":"Livehouse","lng":"116.409181","lat":"39.950155"},{"place_id":100388,"name":"远方酒吧","name_en":"Là-bas","logo_url":"http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100388/MTAwMzg4.jpg","distance":"4.011920","province_id":"100001","city_id":"100002","city_name":"北京","fav":"11","comm":"0","type_id":"8","type_name":"酒吧","lng":"116.417848","lat":"39.949580"}]
     * pages : 3
     */

    private DataBean data;

    public int getErr_code() {
        return err_code;
    }

    public void setErr_code(int err_code) {
        this.err_code = err_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int pages;
        /**
         * place_id : 100134
         * name : TADA方舟
         * name_en : TADA
         * logo_url : http://7xkszy.com2.z0.glb.qiniucdn.com/pics/livehouse/100134/MTAwMTM0.jpg
         * distance : 0.020945
         * province_id : 103295
         * city_id : 103296
         * city_name : 台湾
         * fav : 9
         * comm : 0
         * type_id : 1
         * type_name : Livehouse
         * lng : 116.404000
         * lat : 39.915000
         */

        private List<ItemsBean> items;

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            private int place_id;
            private String name;
            private String name_en;
            private String logo_url;
            private String distance;
            private String province_id;
            private String city_id;
            private String city_name;
            private String fav;
            private String comm;
            private String type_id;
            private String type_name;
            private String lng;
            private String lat;

            public int getPlace_id() {
                return place_id;
            }

            public void setPlace_id(int place_id) {
                this.place_id = place_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getName_en() {
                return name_en;
            }

            public void setName_en(String name_en) {
                this.name_en = name_en;
            }

            public String getLogo_url() {
                return logo_url;
            }

            public void setLogo_url(String logo_url) {
                this.logo_url = logo_url;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getProvince_id() {
                return province_id;
            }

            public void setProvince_id(String province_id) {
                this.province_id = province_id;
            }

            public String getCity_id() {
                return city_id;
            }

            public void setCity_id(String city_id) {
                this.city_id = city_id;
            }

            public String getCity_name() {
                return city_name;
            }

            public void setCity_name(String city_name) {
                this.city_name = city_name;
            }

            public String getFav() {
                return fav;
            }

            public void setFav(String fav) {
                this.fav = fav;
            }

            public String getComm() {
                return comm;
            }

            public void setComm(String comm) {
                this.comm = comm;
            }

            public String getType_id() {
                return type_id;
            }

            public void setType_id(String type_id) {
                this.type_id = type_id;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }
        }
    }
}
