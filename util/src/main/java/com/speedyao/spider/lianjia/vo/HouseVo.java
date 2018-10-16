package com.speedyao.spider.lianjia.vo;

/**
 * Created by speedyao on 2018/10/12.
 */
public class HouseVo {
    String url;//链接地址
    String title;//标题
    String xiaoqu;//小区
    String xiaoquUrl;//小区url
    String info;
    String flood;//楼层
    String position;//社区
    String positionUrl;//社区url
    String followInfo;//带看信息
    String tag;//标签
    Double totalPrice;//价格
    String unitPrice;//单价

    public String getUrl() {
        return url;
    }

    public HouseVo setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public HouseVo setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getInfo() {
        return info;
    }

    public HouseVo setInfo(String info) {
        this.info = info;
        return this;
    }

    public String getFlood() {
        return flood;
    }

    public HouseVo setFlood(String flood) {
        this.flood = flood;
        return this;
    }

    public String getPositionUrl() {
        return positionUrl;
    }

    public HouseVo setPositionUrl(String positionUrl) {
        this.positionUrl = positionUrl;
        return this;
    }

    public String getPosition() {

        return position;
    }

    public HouseVo setPosition(String position) {
        this.position = position;
        return this;
    }

    public String getFollowInfo() {
        return followInfo;
    }

    public HouseVo setFollowInfo(String followInfo) {
        this.followInfo = followInfo;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public HouseVo setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public HouseVo setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }


    public String getXiaoqu() {
        return xiaoqu;
    }

    public HouseVo setXiaoqu(String xiaoqu) {
        this.xiaoqu = xiaoqu;
        return this;
    }

    public String getXiaoquUrl() {
        return xiaoquUrl;
    }

    public HouseVo setXiaoquUrl(String xiaoquUrl) {
        this.xiaoquUrl = xiaoquUrl;
        return this;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public HouseVo setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    @Override
    public String toString() {
        return
                "url='" + url + '\'' +
                        "; title='" + title + '\'' +
                        "; xiaoqu='" + xiaoqu + '\'' +
                        "; xiaoquUrl='" + xiaoquUrl + '\'' +
                        "; info='" + info + '\'' +
                        "; flood='" + flood + '\'' +
                        "; position='" + position + '\'' +
                        "; positionUrl='" + positionUrl + '\'' +
                        "; followInfo='" + followInfo + '\'' +
                        "; tag='" + tag + '\'' +
                        "; totalPrice=" + totalPrice +
                        "; unitPrice='" + unitPrice;

    }
}
