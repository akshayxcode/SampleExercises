package com.example.micro_services;

import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.ModifiedExpiryPolicy;
import javax.cache.spi.CachingProvider;
import java.util.concurrent.TimeUnit;
import javax.cache.expiry.Duration;

@Configuration
@EnableCaching
public class EhCacheConfig {
    @Bean
    public CacheManager cacheManager() {
        CachingProvider provider = Caching.getCachingProvider(EhcacheCachingProvider.class.getName());
        javax.cache.CacheManager jcacheManager = provider.getCacheManager();

        MutableConfiguration<Object, Object> cacheConfig = new MutableConfiguration<>()
                .setExpiryPolicyFactory(ModifiedExpiryPolicy.factoryOf(new Duration(TimeUnit.MINUTES, 10)))
                .setStatisticsEnabled(true);

        jcacheManager.createCache("users", cacheConfig);
        jcacheManager.createCache("users_login", cacheConfig);
        jcacheManager.createCache("pets", cacheConfig);
        jcacheManager.createCache("pets_by_status", cacheConfig);
        jcacheManager.createCache("oders", cacheConfig);
        jcacheManager.createCache("order_inventory", cacheConfig);

        return new JCacheCacheManager(jcacheManager);
    }
}
