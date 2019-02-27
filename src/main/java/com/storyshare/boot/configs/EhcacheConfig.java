package com.storyshare.boot.configs;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.stereotype.Component;

import javax.cache.CacheManager;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.Duration;
import javax.cache.expiry.TouchedExpiryPolicy;

import static java.util.concurrent.TimeUnit.MINUTES;

@Component
public class EhcacheConfig implements JCacheManagerCustomizer {
    @Override
    public void customize(CacheManager cacheManager) {
        cacheManager.createCache("userName", new MutableConfiguration<>()
                .setExpiryPolicyFactory(TouchedExpiryPolicy.factoryOf(new Duration(MINUTES, 10)))
                .setStoreByValue(false));

        cacheManager.createCache("userAvatar", new MutableConfiguration<>()
                .setExpiryPolicyFactory(TouchedExpiryPolicy.factoryOf(new Duration(MINUTES, 5)))
                .setStoreByValue(false));
    }
}
