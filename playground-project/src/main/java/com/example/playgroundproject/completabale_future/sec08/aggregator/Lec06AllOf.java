package com.example.playgroundproject.completabale_future.sec08.aggregator;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

@Slf4j
public class Lec06AllOf {
    public static void main(String[] args) {
        aggregator2();
    }

    private static void aggregator2(){
        var service = Executors.newVirtualThreadPerTaskExecutor();
        var aggregator = new AggregatorService(service);
        var compFutures = IntStream.rangeClosed(1,50)
                .mapToObj(id-> CompletableFuture.supplyAsync(() -> aggregator.getProduct(id), service))
                .toList();
        CompletableFuture.allOf(compFutures.toArray(CompletableFuture[]::new)).join();

        compFutures.stream()
                .map(CompletableFuture::join)
                .forEach(x->log.info("PRODUCT-{}: {}",x.id(),x));

        // got all products in one sec

        /*
            08:42:00.835 [virtual-96] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/8
08:42:00.836 [virtual-86] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/3
08:42:00.827 [virtual-84] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/5
08:42:00.835 [virtual-105] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/13
08:42:00.837 [virtual-107] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/13
08:42:00.837 [virtual-127] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/24
08:42:00.837 [virtual-110] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/16
08:42:00.837 [virtual-112] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/17
08:42:00.837 [virtual-129] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/24
08:42:00.837 [virtual-143] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/32
08:42:00.837 [virtual-128] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/25
08:42:00.839 [virtual-90] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/5
08:42:00.839 [virtual-100] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/10
08:42:00.826 [virtual-93] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/8
08:42:00.850 [virtual-159] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/40
08:42:00.850 [virtual-102] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/11
08:42:00.850 [virtual-131] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/26
08:42:00.850 [virtual-176] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/48
08:42:00.827 [virtual-89] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/2
08:42:00.851 [virtual-130] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/25
08:42:00.851 [virtual-136] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/29
08:42:00.851 [virtual-145] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/33
08:42:00.851 [virtual-85] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/6
08:42:00.851 [virtual-169] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/45
08:42:00.851 [virtual-170] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/45
08:42:00.827 [virtual-97] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/9
08:42:00.851 [virtual-160] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/41
08:42:00.851 [virtual-146] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/33
08:42:00.851 [virtual-103] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/12
08:42:00.851 [virtual-162] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/41
08:42:00.851 [virtual-177] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/49
08:42:00.851 [virtual-132] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/26
08:42:00.851 [virtual-87] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/1
08:42:00.826 [virtual-77] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/2
08:42:00.826 [virtual-81] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/4
08:42:00.852 [virtual-118] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/20
08:42:00.852 [virtual-91] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/6
08:42:00.827 [virtual-88] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/4
08:42:00.852 [virtual-120] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/20
08:42:00.852 [virtual-147] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/34
08:42:00.852 [virtual-106] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/14
08:42:00.852 [virtual-135] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/28
08:42:00.837 [virtual-113] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/16
08:42:00.852 [virtual-108] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/14
08:42:00.852 [virtual-163] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/42
08:42:00.853 [virtual-151] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/36
08:42:00.853 [virtual-122] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/22
08:42:00.853 [virtual-116] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/19
08:42:00.853 [virtual-94] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/7
08:42:00.837 [virtual-99] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/9
08:42:00.854 [virtual-119] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/19
08:42:00.837 [virtual-114] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/17
08:42:00.854 [virtual-109] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/15
08:42:00.854 [virtual-125] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/22
08:42:00.854 [virtual-139] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/30
08:42:00.854 [virtual-148] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/35
08:42:00.854 [virtual-111] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/15
08:42:00.839 [virtual-98] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/10
08:42:00.854 [virtual-154] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/38
08:42:00.850 [virtual-101] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/11
08:42:00.855 [virtual-167] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/43
08:42:00.855 [virtual-157] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/38
08:42:00.850 [virtual-144] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/32
08:42:00.855 [virtual-124] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/23
08:42:00.850 [virtual-115] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/18
08:42:00.855 [virtual-171] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/46
08:42:00.855 [virtual-126] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/23
08:42:00.855 [virtual-141] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/31
08:42:00.855 [virtual-142] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/31
08:42:00.850 [virtual-117] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/18
08:42:00.855 [virtual-156] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/39
08:42:00.850 [virtual-161] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/40
08:42:00.855 [virtual-158] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/39
08:42:00.856 [virtual-172] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/47
08:42:00.850 [virtual-175] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/48
08:42:00.851 [virtual-123] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/21
08:42:00.856 [virtual-174] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/47
08:42:00.851 [virtual-121] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/21
08:42:00.851 [virtual-78] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/1
08:42:00.856 [virtual-168] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/44
08:42:00.851 [virtual-138] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/29
08:42:00.851 [virtual-153] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/37
08:42:00.851 [virtual-155] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/37
08:42:00.851 [virtual-179] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/49
08:42:00.852 [virtual-104] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/12
08:42:00.852 [virtual-149] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/34
08:42:00.852 [virtual-137] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/28
08:42:00.854 [virtual-95] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/7
08:42:00.854 [virtual-133] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/27
08:42:00.854 [virtual-134] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/27
08:42:00.854 [virtual-140] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/30
08:42:00.854 [virtual-164] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/42
08:42:00.854 [virtual-150] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/35
08:42:00.855 [virtual-165] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/43
08:42:00.854 [virtual-178] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/50
08:42:00.855 [virtual-180] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/50
08:42:00.855 [virtual-173] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/46
08:42:00.856 [virtual-152] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/36
08:42:00.856 [virtual-166] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/44
08:42:00.826 [virtual-80] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/3
08:42:01.936 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-1: ProductDTO[id=1, description=Practical Marble Shoes, rating=2]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-2: ProductDTO[id=2, description=Awesome Granite Bottle, rating=3]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-3: ProductDTO[id=3, description=Synergistic Aluminum Knife, rating=2]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-4: ProductDTO[id=4, description=Practical Rubber Shirt, rating=2]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-5: ProductDTO[id=5, description=Aerodynamic Granite Shirt, rating=3]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-6: ProductDTO[id=6, description=Ergonomic Linen Bench, rating=1]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-7: ProductDTO[id=7, description=Mediocre Marble Car, rating=4]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-8: ProductDTO[id=8, description=Small Leather Bottle, rating=1]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-9: ProductDTO[id=9, description=Enormous Copper Bag, rating=1]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-10: ProductDTO[id=10, description=Ergonomic Wooden Computer, rating=4]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-11: ProductDTO[id=11, description=Enormous Granite Bench, rating=1]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-12: ProductDTO[id=12, description=Synergistic Rubber Clock, rating=4]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-13: ProductDTO[id=13, description=Awesome Marble Wallet, rating=3]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-14: ProductDTO[id=14, description=Ergonomic Cotton Shoes, rating=4]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-15: ProductDTO[id=15, description=Ergonomic Linen Pants, rating=1]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-16: ProductDTO[id=16, description=Durable Cotton Table, rating=5]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-17: ProductDTO[id=17, description=Practical Wool Bottle, rating=4]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-18: ProductDTO[id=18, description=Gorgeous Steel Table, rating=1]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-19: ProductDTO[id=19, description=Lightweight Rubber Hat, rating=5]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-20: ProductDTO[id=20, description=Lightweight Wooden Lamp, rating=5]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-21: ProductDTO[id=21, description=Durable Paper Bag, rating=4]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-22: ProductDTO[id=22, description=Mediocre Aluminum Bench, rating=3]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-23: ProductDTO[id=23, description=Awesome Bronze Coat, rating=4]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-24: ProductDTO[id=24, description=Ergonomic Steel Hat, rating=3]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-25: ProductDTO[id=25, description=Heavy Duty Linen Pants, rating=4]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-26: ProductDTO[id=26, description=Synergistic Wooden Gloves, rating=3]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-27: ProductDTO[id=27, description=Incredible Granite Pants, rating=5]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-28: ProductDTO[id=28, description=Mediocre Iron Shirt, rating=2]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-29: ProductDTO[id=29, description=Intelligent Cotton Keyboard, rating=2]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-30: ProductDTO[id=30, description=Rustic Iron Car, rating=2]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-31: ProductDTO[id=31, description=Ergonomic Paper Bottle, rating=5]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-32: ProductDTO[id=32, description=Intelligent Leather Watch, rating=3]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-33: ProductDTO[id=33, description=Mediocre Marble Watch, rating=1]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-34: ProductDTO[id=34, description=Gorgeous Silk Coat, rating=3]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-35: ProductDTO[id=35, description=Heavy Duty Granite Plate, rating=4]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-36: ProductDTO[id=36, description=Synergistic Wool Watch, rating=4]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-37: ProductDTO[id=37, description=Ergonomic Steel Shirt, rating=5]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-38: ProductDTO[id=38, description=Synergistic Rubber Table, rating=5]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-39: ProductDTO[id=39, description=Small Granite Shoes, rating=2]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-40: ProductDTO[id=40, description=Rustic Bronze Computer, rating=4]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-41: ProductDTO[id=41, description=Sleek Paper Watch, rating=1]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-42: ProductDTO[id=42, description=Intelligent Marble Plate, rating=3]
08:42:01.956 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-43: ProductDTO[id=43, description=Fantastic Concrete Bag, rating=5]
08:42:01.963 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-44: ProductDTO[id=44, description=Intelligent Aluminum Pants, rating=5]
08:42:01.963 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-45: ProductDTO[id=45, description=Synergistic Granite Computer, rating=1]
08:42:01.963 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-46: ProductDTO[id=46, description=Durable Wooden Coat, rating=1]
08:42:01.963 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-47: ProductDTO[id=47, description=Lightweight Concrete Wallet, rating=4]
08:42:01.963 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-48: ProductDTO[id=48, description=Incredible Steel Table, rating=4]
08:42:01.963 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-49: ProductDTO[id=49, description=Ergonomic Leather Hat, rating=1]
08:42:01.963 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec06AllOf -- PRODUCT-50: ProductDTO[id=50, description=Aerodynamic Copper Computer, rating=3]
         */
    }

}
