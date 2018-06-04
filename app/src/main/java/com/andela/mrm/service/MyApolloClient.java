package com.andela.mrm.service;

import android.content.Context;

import com.andela.mrm.room_setup.data.SetupPage;
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
 * The type My apollo client.
 */
public final class MyApolloClient {

    /**
     * The constant INSTANCE.
     */
    public static final MyApolloClient INSTANCE = new MyApolloClient();
    private static final String BASE_URL =
            "https://api.graph.cool/simple/v1/cjgdrz83r1t4k0173p13insaj";
    private ApolloClient mApolloClient;
    private SetupPage mCurrentSetupPage = SetupPage.COUNTRY;

    private int mSelectedCountryIndex;
    private int mSelectedBuildingIndex;
    private int mSelectedFloorIndex;


    /**
     * Private constructor for this class.
     */
    private MyApolloClient() {

    }

    /**
     * Gets my apollo client.
     *
     * @param context the context
     * @return the my apollo client
     */
    public ApolloClient getMyApolloClient(Context context) {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        ApolloSqlHelper apolloSqlHelper = ApolloSqlHelper.create(context, "mCache_db");

        //Create NormalizedCacheFactory for disk caching
        NormalizedCacheFactory sqlCacheFactory = new SqlNormalizedCacheFactory(apolloSqlHelper);

        //Create the cache key resolver
        CacheKeyResolver resolver = new CacheKeyResolver() {
            @Nonnull
            @Override
            public CacheKey fromFieldRecordSet(@Nonnull ResponseField field,
                                               @Nonnull Map<String, Object> recordSet) {
                return formatCacheKey((String) recordSet.get("id"));
            }

            @Nonnull
            @Override
            public CacheKey fromFieldArguments(@Nonnull ResponseField field,
                                               @Nonnull Operation.Variables variables) {
                return formatCacheKey((String) field.resolveArgument("id", variables));
            }

            private CacheKey formatCacheKey(String id) {
                if (id == null || id.isEmpty()) {
                    return CacheKey.NO_KEY;
                } else {
                    return CacheKey.from(id);
                }
            }
        };

        mApolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .normalizedCache(sqlCacheFactory, resolver)
                .okHttpClient(okHttpClient)
                .build();

        return mApolloClient;

    }


    /**
     * Gets current setup page.
     *
     * @return the current setup page
     */
    public SetupPage getCurrentSetupPage() {
        return mCurrentSetupPage;
    }

    /**
     * Sets current setup page.
     *
     * @param currentSetupPage the current setup page
     */
    public void setCurrentSetupPage(SetupPage currentSetupPage) {
        mCurrentSetupPage = currentSetupPage;
    }


    /**
     * Gets selected country index.
     *
     * @return the selected country index
     */
    public int getSelectedCountryIndex() {
        return mSelectedCountryIndex;
    }

    /**
     * Sets selected country index.
     *
     * @param selectedCountryIndex the selected country index
     */
    public void setSelectedCountryIndex(int selectedCountryIndex) {
        mSelectedCountryIndex = selectedCountryIndex;
    }

    /**
     * Gets selected building index.
     *
     * @return the selected building index
     */
    public int getSelectedBuildingIndex() {
        return mSelectedBuildingIndex;
    }

    /**
     * Sets selected building index.
     *
     * @param selectedBuildingIndex the selected building index
     */
    public void setSelectedBuildingIndex(int selectedBuildingIndex) {
        mSelectedBuildingIndex = selectedBuildingIndex;
    }

    /**
     * Gets selected floor index.
     *
     * @return the selected floor index
     */
    public int getSelectedFloorIndex() {
        return mSelectedFloorIndex;
    }

    /**
     * Sets selected floor index.
     *
     * @param selectedFloorIndex the selected floor index
     */
    public void setSelectedFloorIndex(int selectedFloorIndex) {
        mSelectedFloorIndex = selectedFloorIndex;
    }
}
