package com.cheikh.lazywaimai.module.library;

import android.content.Context;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import com.waimai.context.AppCookie;
import com.cheikh.lazywaimai.module.qualifiers.ApplicationContext;
import com.cheikh.lazywaimai.module.qualifiers.CacheDirectory;
import com.cheikh.lazywaimai.module.qualifiers.ShareDirectory;
import com.waimai.network.RestApiClient;
import com.waimai.util.Constants;

@Module(
    library = true,
    includes = ContextProvider.class
)
public class NetworkProvider {

    @Provides @Singleton
    public RestApiClient provideRestApiClient(@CacheDirectory File cacheLocation, @ApplicationContext Context context) {
        RestApiClient restApiClient = new RestApiClient(context, cacheLocation);
        if (AppCookie.isLoggin()) {
            restApiClient.setToken(AppCookie.getAccessToken());
        }
        return restApiClient;
    }

    @Provides @Singleton @CacheDirectory
    public File provideHttpCacheLocation(@ApplicationContext Context context) {
        return context.getCacheDir();
    }

    @Provides @Singleton @ShareDirectory
    public File provideShareLocation(@ApplicationContext Context context) {
        return new File(context.getFilesDir(), Constants.Persistence.SHARE_FILE);
    }
}
