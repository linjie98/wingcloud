package top.wingcloud.domain;

/**
 * 滚动流订单数据实体类
 */
public class ShopRoll {
    private String shopid;
    private String shopname;
    private String shoptype;

    public ShopRoll(String shopid, String shopname, String shoptype) {
        this.shopid = shopid;
        this.shopname = shopname;
        this.shoptype = shoptype;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getShoptype() {
        return shoptype;
    }

    public void setShoptype(String shoptype) {
        this.shoptype = shoptype;
    }

    @Override
    public String toString() {
        return "ShopRoll{" +
                "shopid='" + shopid + '\'' +
                ", shopname='" + shopname + '\'' +
                ", shoptype='" + shoptype + '\'' +
                '}';
    }
}
