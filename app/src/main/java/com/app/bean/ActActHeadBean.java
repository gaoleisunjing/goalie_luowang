package com.app.bean;

import java.util.List;

/**
 * Created by LWL on 2016/9/29.
 */
public class ActActHeadBean {

    /**
     * err_code : 0
     * msg :
     * data : {"items":[{"ad_id":"424","image":"http://7xkszy.com2.z0.glb.qiniucdn.com/site/201609/57d25d5d5857d.jpg","title":"2016朱家角水乡音乐节","has_comment":"yes","has_music":"yes","type":"5","res_value":"http://www.musikid.com/special/waterviliage/","comm":0},{"ad_id":"428","image":"http://7xkszy.com2.z0.glb.qiniucdn.com/site/201609/57e0e076ef0e1.jpg","title":"2016简单生活节","has_comment":"yes","has_music":"yes","type":"5","res_value":"http://m.damai.cn/ticket/107633.html","comm":0}]}
     */

    private int err_code;
    private String msg;
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
        /**
         * ad_id : 424
         * image : http://7xkszy.com2.z0.glb.qiniucdn.com/site/201609/57d25d5d5857d.jpg
         * title : 2016朱家角水乡音乐节
         * has_comment : yes
         * has_music : yes
         * type : 5
         * res_value : http://www.musikid.com/special/waterviliage/
         * comm : 0
         */

        private List<ItemsBean> items;

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            private String ad_id;
            private String image;
            private String title;
            private String has_comment;
            private String has_music;
            private String type;
            private String res_value;
            private int comm;

            public String getAd_id() {
                return ad_id;
            }

            public void setAd_id(String ad_id) {
                this.ad_id = ad_id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getHas_comment() {
                return has_comment;
            }

            public void setHas_comment(String has_comment) {
                this.has_comment = has_comment;
            }

            public String getHas_music() {
                return has_music;
            }

            public void setHas_music(String has_music) {
                this.has_music = has_music;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getRes_value() {
                return res_value;
            }

            public void setRes_value(String res_value) {
                this.res_value = res_value;
            }

            public int getComm() {
                return comm;
            }

            public void setComm(int comm) {
                this.comm = comm;
            }
        }
    }
}
