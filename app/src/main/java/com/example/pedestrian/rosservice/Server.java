package com.example.pedestrian.rosservice;

import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.service.ServiceResponseBuilder;

import rosjava_custom_srv.CustomService2;
import rosjava_custom_srv.CustomService2Request;
import rosjava_custom_srv.CustomService2Response;

/**
 * Created by pedestrian-username on 17-11-25.
 */

public class Server extends AbstractNodeMain {

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("/service/server");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        connectedNode.newServiceServer("/service/add", CustomService2._TYPE,
                new ServiceResponseBuilder<CustomService2Request, CustomService2Response>() {
                    @Override
                    public void build(CustomService2Request request, CustomService2Response response) {
                        response.setSum(request.getA() + request.getB());
                    }
                });
    }
}
