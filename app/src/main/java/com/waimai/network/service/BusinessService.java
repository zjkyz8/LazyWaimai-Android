package com.waimai.network.service;

import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import com.waimai.model.bean.Business;
import com.waimai.model.bean.Favorite;
import com.waimai.model.bean.ProductCategory;
import com.cheikh.lazywaimai.model.bean.ResultsPage;

public interface BusinessService {

    @GET("restaurant")
    Observable<ResultsPage<Business>> businesses(@Query("page") int page, @Query("size") int size);

    @GET("restaurant/{bid}/products")
    Observable<List<ProductCategory>> products(@Path("bid") String businessId);

    @POST("restaurant/{bid}/favorite")
    Observable<Favorite> favorite(@Path("bid") String businessId);
}
