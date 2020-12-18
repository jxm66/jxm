package com.app.shop.demo.beans;

import org.litepal.crud.DataSupport;

import java.io.Serializable;



public class GoodsBean extends DataSupport implements Serializable {

    private int goods_id;
    private String goods_name;
    private double goods_price;
    private int goods_num;
    private int goods_pic;
    private String goods_type;
    private String goods_ladu;
    private String remark;
    private String kll;
    private String zf;
    private String dbz;

    public String getKll() {
        return kll;
    }

    public void setKll(String kll) {
        this.kll = kll;
    }

    public String getZf() {
        return zf;
    }

    public void setZf(String zf) {
        this.zf = zf;
    }

    public String getDbz() {
        return dbz;
    }

    public void setDbz(String dbz) {
        this.dbz = dbz;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getGoods_ladu() {
        return goods_ladu;
    }

    public void setGoods_ladu(String goods_ladu) {
        this.goods_ladu = goods_ladu;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(double goods_price) {
        this.goods_price = goods_price;
    }

    public int getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(int goods_num) {
        this.goods_num = goods_num;
    }

    public int getGoods_pic() {
        return goods_pic;
    }

    public void setGoods_pic(int goods_pic) {
        this.goods_pic = goods_pic;
    }
}
