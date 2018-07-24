package zym.stream.onlineshop;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static zym.stream.onlineshop.Helper.delay;

/**
 * @Author unyielding
 * @date 2018/7/20 0020 13:08
 * @desc 在线商店
 */
public class Shop {

    private String name;
    Shop() {}
    Shop(String name) {
        this.name = name;
    }

    public String getPriceAndDiscount(String product) {
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[new Random().nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", name, price, code);
    }

    public String getName() {
        return name;
    }


    /**
     * 获取价格
     *
     * @param product 商品名称
     * @return 返回打折后的价格
     */
    public double getPrice(String product) {
        return calculatePrice(product);
    }

    /**
     * 非阻塞式获取价格
     *
     * @param product
     * @return
     */
    public Future<Double> getPriceAsyn(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            try {
                double price = calculatePrice(product);
                futurePrice.complete(price);
            } catch (Exception e) {
                futurePrice.completeExceptionally(e);
            }
        }).start();
        return futurePrice;
    }

    private double calculatePrice(String product) {
        delay();//阻塞1s
        return new Random().nextDouble() * product.charAt(0) + product.charAt(1);
    }

    /**
     * 获取商品
     * @param product 商品名称
     * @return
     */
    public Future<Double> getPriceAsync(String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

}
