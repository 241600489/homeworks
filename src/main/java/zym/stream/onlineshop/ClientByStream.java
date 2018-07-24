package zym.stream.onlineshop;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @Author unyielding
 * @date 2018/7/20 0020 13:34
 * @desc
 */
public class ClientByStream {
    private static final Logger log = Logger.getGlobal();

    public static void main(String[] args) {
        log.info("----------------------------假装有一条分界线-------------------------------------");
        getSyncShopList();
    }

    private static void getSyncShopList() {
        long start = System.nanoTime();
//        System.out.println(findPrices("I love you"));
        CompletableFuture<Void>[] futures = findPricesByCompletable("I love you")
                .map(f -> f.thenAccept(s -> System.out.println(s + " done in" +
                        ((System.nanoTime() - start) / 1_000_000) + "msces")))
                .toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(futures).join();
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("method[getSyncShopList] Done in " + duration + "secs");
    }

    private static List<Shop> shopsFive = Arrays.asList(
            new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"),
            new Shop("Lv"));

    private static List<Shop> shopNine = Arrays.asList(
            new Shop("BestPrice"),
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

    /**
     * 并行流耗时 4049secs
     */
    static Stream<String> findPrices(String product) {
        return shopsFive.parallelStream()
                .map(shop -> shop.getPriceAndDiscount(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount);
    }

    /**
     * completableFuture 耗时：
     */
    static Stream<CompletableFuture<String>> findPricesByCompletable(String product) {
        return shopsFive.stream()
                .map(shop ->
                        CompletableFuture.supplyAsync(
                                () -> shop.getPriceAndDiscount(product),
                                executor) //采用completable执行获取价格和折扣
                ).map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote ->//thenCompose 需要在future值结构中运行某个函数，
                        // 同样返回一个future，简单说就是两个future 有某种依赖
                        CompletableFuture.supplyAsync(
                                () -> Discount.applyDiscount(quote), executor)));// 去计算折扣

    }


}
