package zym.stream.onlineshop;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;

/**
 * @Author unyielding
 * @date 2018/7/20 0020 13:34
 * @desc
 */
public class Client {
    private static final Logger log = Logger.getGlobal();

    public static void main(String[] args) {
        getAsyncProduct();
        log.info("----------------------------假装有一条分界线-------------------------------------");
        getSyncShopList();
    }

    private static void getSyncShopList() {
        long start = System.nanoTime();
//        System.out.println(findParallelPrices("I love you"));
//        System.out.println(findPriceCompletableFuture("I love you"));
//        System.out.println(findPrices("I love you"));
        System.out.println(findPricesByCompletable("I love you"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("method[getSyncShopList] Done in " + duration + "secs");
    }

    private static List<Shop> shopsFive = Arrays.asList(new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"),
            new Shop("Lv"));

    private static List<Shop> shopNine = Arrays.asList(new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"),
            new Shop("Lv"),
            new Shop("SUGAR"),
            new Shop("Dior"),
            new Shop("YSL"),
            new Shop("Jorlen"));
    /**
     * 创建一个线程池
     */
    private static final ExecutorService executor =
            Executors.newFixedThreadPool(Math.min(shopsFive.size(), 1000), r -> {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            });

    public static List<String> findParallelPrices(String product) {
        return shopNine.parallelStream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(toList());
    }

    public static List<String> findPriceCompletableFuture(String product) {
        return shopsFive.stream().map(shop ->
                CompletableFuture.supplyAsync(() -> String.format("%s price is %.2f",
                        shop.getName(), shop.getPrice(product)), executor))
                .collect(toList()).stream()
                .map(CompletableFuture::join).collect(toList());
    }

    /**
     * 并行流耗时 4049secs
     */
    static List<String> findPrices(String product) {
        return shopsFive.parallelStream()
                .map(shop -> shop.getPriceAndDiscount(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(toList());
    }

    /**
     * completableFuture 耗时：
     */
    static List<String> findPricesByCompletable(String product) {
        return shopsFive.stream()
                .map(shop ->
                        CompletableFuture.supplyAsync(
                                () -> shop.getPriceAndDiscount(product),
                                executor) //采用completable执行获取价格和折扣
                ).map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote ->//thenCompose 需要在future值结构中运行某个函数，
                                                        // 同样返回一个future，简单说就是两个future 有某种依赖
                        CompletableFuture.supplyAsync(
                                () -> Discount.applyDiscount(quote), executor)))// 去计算折扣
                .collect(toList()).stream()
                .map(CompletableFuture::join)//等待任务的返回
                .collect(toList());
    }

    private static void getAsyncProduct() {
        Shop shop = new Shop();
        long start = System.nanoTime();
        Future<Double> priceAsyn = shop.getPriceAsyn("my best like food");
        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Invocation returned after " + invocationTime + "msecs");

        //执行更多的任务
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            double price = priceAsyn.get();
            log.info("获取的价格为：" + price + "元");
        } catch (InterruptedException | ExecutionException e) {
            log.log(Level.WARNING, e, () -> "抛出运行时异常");
        }
        long retrievalTime = ((System.nanoTime() - start) / 1_000_00);
        log.info("price returned after " + retrievalTime + "msecs");
    }
}
