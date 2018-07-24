package zym.stream.onlineshop;


/**
 * @Author unyielding
 * @date 2018/7/21 0021 9:58
 * @desc 报价
 */
public class Quote {
    private final String shopName;

    private final Discount.Code code;

    private final Double price;

    public Quote(String shopName, Discount.Code code, Double price) {
        this.shopName = shopName;
        this.code = code;
        this.price = price;
    }

    public static Quote parse(String s) {
        String[] strs = s.split(":");
        double price = Double.valueOf(strs[1]);
        Discount.Code code = Discount.Code.valueOf(strs[2]);
        return new Quote(strs[0], code, price);
    }

    public String getShopName() {
        return shopName;
    }

    public Discount.Code getCode() {
        return code;
    }

    public Double getPrice() {
        return price;
    }
}
