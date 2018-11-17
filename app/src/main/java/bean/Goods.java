package bean;

public class Goods {
    int imageCode;
    String goodsTitle;
    String goodsPrice;

    public int getImageCode() {
        return imageCode;
    }

    public Goods(int imageCode, String goodsTitle, String goodsPrice) {
        this.imageCode = imageCode;
        this.goodsTitle = goodsTitle;
        this.goodsPrice = goodsPrice;
    }

    public void setImageCode(int imageCode) {
        this.imageCode = imageCode;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }
}
