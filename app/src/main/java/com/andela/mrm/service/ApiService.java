package com.andela.mrm.service;

import android.content.Context;
import android.support.annotation.NonNull;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Operation;
import com.apollographql.apollo.api.ResponseField;
import com.apollographql.apollo.cache.normalized.CacheKey;
import com.apollographql.apollo.cache.normalized.CacheKeyResolver;
import com.apollographql.apollo.cache.normalized.NormalizedCacheFactory;
import com.apollographql.apollo.cache.normalized.sql.ApolloSqlHelper;
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory;

import java.util.Map;

import javax.annotation.Nonnull;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Apollo client provider class.
 */
public final class ApiService {

    private static final String BASE_URL = "http://converge-api.andela.com/mrm";

    /**
     * Private class constructor.
     */
    private ApiService() {
        // Prevents instantiation since this is an utility class
    }

    /**
     * Gets apollo client.
     *
     * @param context the context
     * @return a configured instance of apollo client
     */
    public static ApolloClient getApolloClient(Context context) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = getOkHttpClient(loggingInterceptor);
        ApolloSqlHelper apolloSqlHelper = ApolloSqlHelper.create(context, "mCache_db");
        NormalizedCacheFactory sqlCacheFactory = new SqlNormalizedCacheFactory(apolloSqlHelper);
        CacheKeyResolver resolver = new CacheKeyResolver() { //Create the cache key resolver
            @Nonnull
            @Override
            public CacheKey fromFieldRecordSet(@Nonnull ResponseField field,
                                               @Nonnull Map<String, Object> recordSet) {
                if (recordSet.containsKey("id")) {
                    String typeNameAndIdKey = recordSet
                            .get("__typename") + "." + recordSet.get("id");
                    return CacheKey.from(typeNameAndIdKey);
                }
                return formatCacheKey((String) recordSet.get("id"));
            }
            @Nonnull
            @Override
            public CacheKey fromFieldArguments(@Nonnull ResponseField field,
                                               @Nonnull Operation.Variables variables) {
                return formatCacheKey((String) field.resolveArgument("id", variables));
            }
            CacheKey formatCacheKey(String id) {
                return getCacheKey(id);
            }
        };
        return apolloClientBuilder(okHttpClient, sqlCacheFactory, resolver);
    }

    /**
      * Method to build the Apollo Client.
      * @param okHttpClient okHttpClient instance
      * @param sqlCacheFactory Cache factory for the data
      * @param resolver Resolver for the
      * @return new OkHttpClient instance
      */
    public static ApolloClient apolloClientBuilder(OkHttpClient okHttpClient,
                                                   NormalizedCacheFactory sqlCacheFactory,
                                                   CacheKeyResolver resolver) {
        return ApolloClient.builder()
                .serverUrl(BASE_URL)
                .normalizedCache(sqlCacheFactory, resolver)
                .okHttpClient(okHttpClient)
                .build();
    }

    /**
     * Extracted Method to get the Cache Key.
     * @param id String with an Identifier
     * @return The Created Cache Key is returned
     */
    static CacheKey getCacheKey(String id) {
        if (id == null || id.isEmpty()) {
            return CacheKey.NO_KEY;
        } else {
            return CacheKey.from(id);
        }
    }

    /**
     * Extract Method to create OkHttpClient.
     * @param loggingInterceptor loggingInterceptor logginf interceptor Instance
     * @return new OkHttpClient instance
     */
    @NonNull
    public static OkHttpClient getOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
    }
}
