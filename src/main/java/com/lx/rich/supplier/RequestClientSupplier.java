package com.lx.rich.supplier;

import com.huobi.client.SubscriptionClient;
import com.huobi.client.SyncRequestClient;
import com.huobi.client.impl.WebSocketStreamClientImpl;
import com.lx.rich.utils.GlobalConstants;

public class RequestClientSupplier {

    public static SyncRequestClient syncRequestClient = SyncRequestClient.create(GlobalConstants.accessKey, GlobalConstants.secretKey);
    public static SubscriptionClient subscriptionClient = SubscriptionClient.create(GlobalConstants.accessKey, GlobalConstants.secretKey);


}
