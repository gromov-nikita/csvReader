package com.gromov.csvReader.service.grpc;

import com.gromov.notification_grpc.EventRequest;
import com.gromov.notification_grpc.EventResponse;
import com.gromov.notification_grpc.NotificationServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class NotificationServiceImpl extends NotificationServiceGrpc.NotificationServiceImplBase {
    @Override
    public void notifyEvent(EventRequest request, StreamObserver<EventResponse> responseObserver) {
        System.out.println("Received event: " + request.getEventId() + ", message: " + request.getMessage());
        EventResponse response = EventResponse.newBuilder()
                .setSuccess(true)
                .setDetails("Event processed successfully")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
