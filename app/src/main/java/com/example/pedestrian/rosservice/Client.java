package com.example.pedestrian.rosservice;

import org.ros.exception.RemoteException;
import org.ros.exception.RosRuntimeException;
import org.ros.exception.ServiceNotFoundException;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.service.ServiceClient;
import org.ros.node.service.ServiceResponseListener;

import rosjava_custom_srv.CustomService2;
import rosjava_custom_srv.CustomService2Request;
import rosjava_custom_srv.CustomService2Response;

/**
 * Created by pedestrian-username on 17-11-25.
 */

public class Client extends AbstractNodeMain {

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("/service/client");
    }

    @Override
    public void onStart(final ConnectedNode connectedNode) {
        ServiceClient<CustomService2Request, CustomService2Response> serviceClient;
        try {
            serviceClient = connectedNode.newServiceClient("/service/add", CustomService2._TYPE);
        } catch (ServiceNotFoundException e) {
            throw new RosRuntimeException(e);
        }
        final CustomService2Request request = serviceClient.newMessage();
        request.setA(10);
        request.setB(20);
        serviceClient.call(request, new ServiceResponseListener<CustomService2Response>() {
            @Override
            public void onSuccess(CustomService2Response response) {
                connectedNode.getLog().info(String.format("%d", response.getSum()));
            }

            @Override
            public void onFailure(RemoteException e) {
                throw new RosRuntimeException(e);
            }
        });
    }
}
