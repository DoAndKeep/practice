/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package doandkeep.com.practice.network;

/**
 * 测试发起网络请求的类
 *
 * @author liuyong05
 * @since 2018-4-19
 */
public class NetworkTest {
    /**
     * 请求接口获取数据测试方法，调用此方法进行测试
     */
    public static void startRequest() {
        BaseRequest request = new NetworkRequest(DataItem.class, "http://10.100.114.127:8090/",
                new ICallback<DataItem>() {
                    @Override
                    public void onSuccess(Response<DataItem> response) {
                        DataItem data = response.getEntity();
                        // 数据请求并解析完成
                        System.out.println("test-- server name:"
                                + data.data.name + " msg:" + data.message);
                    }

                    @Override
                    public void onFail(int code, Throwable e) {
                    }
                });

        // GET请求
        NetworkHelper.getInstance().get(request);
    }

    /**
     * 构建请求需要的Request类
     */
    public static class NetworkRequest extends BaseRequest<DataItem> {

        public NetworkRequest(Class<DataItem> cls, String url, ICallback callback) {
            super(cls, url, callback);

            // 测试添加URL参数
            putUrlParams("type", "1");
        }

        @Override
        protected void initHeader() {

        }
    }

    /**
     * 数据对象
     */
    public static class DataItem {
        public int code;
        public String message;
        public String type;
        public ItemData data;
    }

    /**
     * 二级数据对象
     */
    public static class ItemData {
        public int tid;
        public String des;
        public String name;
        public String site;
    }
}
