package com.welltech;



import java.util.List;

/**
 * Created by myMac on 17/6/8.
 */
public class ZookeeperTest {

    /*public static void main(String[] args) throws InterruptedException {

        ZkClient zkClient = new ZkClient("139.196.28.48:2181",5000);
        System.out.println("ZK 成功建立连接！");
        String path = "/root";
        System.out.println(zkClient.getChildren(path));

        // 注册子节点变更监听（此时path节点并不存在，但可以进行监听注册）
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println("路径" + parentPath +"下面的子节点变更。子节点为：" + currentChilds );
            }
        });

        Thread.sleep(100000);
        System.out.println(zkClient.getChildren(path));

        // 注册子节点变更监听（此时path节点并不存在，但可以进行监听注册）
        *//*zkClient.subscribeChildChanges(path, new IZkChildListener() {
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println("路径" + parentPath +"下面的子节点变更。子节点为：" + currentChilds );
            }
        });*//*
    }
*/
}
