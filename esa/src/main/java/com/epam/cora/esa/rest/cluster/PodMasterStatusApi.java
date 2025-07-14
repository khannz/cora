package com.epam.cora.esa.rest.cluster;

import com.epam.cora.entity.cluster.MasterPodInfo;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PodMasterStatusApi {

    /**
     * Get a master pod name.
     */
    @GET("/")
    Call<MasterPodInfo> getMasterName();
}
