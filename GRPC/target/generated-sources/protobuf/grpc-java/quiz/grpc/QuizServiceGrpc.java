package quiz.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.65.1)",
    comments = "Source: quiz.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class QuizServiceGrpc {

  private QuizServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "quiz.QuizService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<quiz.grpc.HelloRequest,
      quiz.grpc.HelloResponse> getHelloMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Hello",
      requestType = quiz.grpc.HelloRequest.class,
      responseType = quiz.grpc.HelloResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<quiz.grpc.HelloRequest,
      quiz.grpc.HelloResponse> getHelloMethod() {
    io.grpc.MethodDescriptor<quiz.grpc.HelloRequest, quiz.grpc.HelloResponse> getHelloMethod;
    if ((getHelloMethod = QuizServiceGrpc.getHelloMethod) == null) {
      synchronized (QuizServiceGrpc.class) {
        if ((getHelloMethod = QuizServiceGrpc.getHelloMethod) == null) {
          QuizServiceGrpc.getHelloMethod = getHelloMethod =
              io.grpc.MethodDescriptor.<quiz.grpc.HelloRequest, quiz.grpc.HelloResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Hello"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  quiz.grpc.HelloRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  quiz.grpc.HelloResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QuizServiceMethodDescriptorSupplier("Hello"))
              .build();
        }
      }
    }
    return getHelloMethod;
  }

  private static volatile io.grpc.MethodDescriptor<quiz.grpc.QuitRequest,
      quiz.grpc.QuitResponse> getQuitMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Quit",
      requestType = quiz.grpc.QuitRequest.class,
      responseType = quiz.grpc.QuitResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<quiz.grpc.QuitRequest,
      quiz.grpc.QuitResponse> getQuitMethod() {
    io.grpc.MethodDescriptor<quiz.grpc.QuitRequest, quiz.grpc.QuitResponse> getQuitMethod;
    if ((getQuitMethod = QuizServiceGrpc.getQuitMethod) == null) {
      synchronized (QuizServiceGrpc.class) {
        if ((getQuitMethod = QuizServiceGrpc.getQuitMethod) == null) {
          QuizServiceGrpc.getQuitMethod = getQuitMethod =
              io.grpc.MethodDescriptor.<quiz.grpc.QuitRequest, quiz.grpc.QuitResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Quit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  quiz.grpc.QuitRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  quiz.grpc.QuitResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QuizServiceMethodDescriptorSupplier("Quit"))
              .build();
        }
      }
    }
    return getQuitMethod;
  }

  private static volatile io.grpc.MethodDescriptor<quiz.grpc.ListRoomsRequest,
      quiz.grpc.ListRoomsResponse> getListRoomsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListRooms",
      requestType = quiz.grpc.ListRoomsRequest.class,
      responseType = quiz.grpc.ListRoomsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<quiz.grpc.ListRoomsRequest,
      quiz.grpc.ListRoomsResponse> getListRoomsMethod() {
    io.grpc.MethodDescriptor<quiz.grpc.ListRoomsRequest, quiz.grpc.ListRoomsResponse> getListRoomsMethod;
    if ((getListRoomsMethod = QuizServiceGrpc.getListRoomsMethod) == null) {
      synchronized (QuizServiceGrpc.class) {
        if ((getListRoomsMethod = QuizServiceGrpc.getListRoomsMethod) == null) {
          QuizServiceGrpc.getListRoomsMethod = getListRoomsMethod =
              io.grpc.MethodDescriptor.<quiz.grpc.ListRoomsRequest, quiz.grpc.ListRoomsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListRooms"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  quiz.grpc.ListRoomsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  quiz.grpc.ListRoomsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QuizServiceMethodDescriptorSupplier("ListRooms"))
              .build();
        }
      }
    }
    return getListRoomsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<quiz.grpc.CreateRoomRequest,
      quiz.grpc.RoomActionResponse> getCreateRoomMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateRoom",
      requestType = quiz.grpc.CreateRoomRequest.class,
      responseType = quiz.grpc.RoomActionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<quiz.grpc.CreateRoomRequest,
      quiz.grpc.RoomActionResponse> getCreateRoomMethod() {
    io.grpc.MethodDescriptor<quiz.grpc.CreateRoomRequest, quiz.grpc.RoomActionResponse> getCreateRoomMethod;
    if ((getCreateRoomMethod = QuizServiceGrpc.getCreateRoomMethod) == null) {
      synchronized (QuizServiceGrpc.class) {
        if ((getCreateRoomMethod = QuizServiceGrpc.getCreateRoomMethod) == null) {
          QuizServiceGrpc.getCreateRoomMethod = getCreateRoomMethod =
              io.grpc.MethodDescriptor.<quiz.grpc.CreateRoomRequest, quiz.grpc.RoomActionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateRoom"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  quiz.grpc.CreateRoomRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  quiz.grpc.RoomActionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QuizServiceMethodDescriptorSupplier("CreateRoom"))
              .build();
        }
      }
    }
    return getCreateRoomMethod;
  }

  private static volatile io.grpc.MethodDescriptor<quiz.grpc.JoinRoomRequest,
      quiz.grpc.RoomActionResponse> getJoinRoomMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "JoinRoom",
      requestType = quiz.grpc.JoinRoomRequest.class,
      responseType = quiz.grpc.RoomActionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<quiz.grpc.JoinRoomRequest,
      quiz.grpc.RoomActionResponse> getJoinRoomMethod() {
    io.grpc.MethodDescriptor<quiz.grpc.JoinRoomRequest, quiz.grpc.RoomActionResponse> getJoinRoomMethod;
    if ((getJoinRoomMethod = QuizServiceGrpc.getJoinRoomMethod) == null) {
      synchronized (QuizServiceGrpc.class) {
        if ((getJoinRoomMethod = QuizServiceGrpc.getJoinRoomMethod) == null) {
          QuizServiceGrpc.getJoinRoomMethod = getJoinRoomMethod =
              io.grpc.MethodDescriptor.<quiz.grpc.JoinRoomRequest, quiz.grpc.RoomActionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "JoinRoom"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  quiz.grpc.JoinRoomRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  quiz.grpc.RoomActionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QuizServiceMethodDescriptorSupplier("JoinRoom"))
              .build();
        }
      }
    }
    return getJoinRoomMethod;
  }

  private static volatile io.grpc.MethodDescriptor<quiz.grpc.LeaveRoomRequest,
      quiz.grpc.RoomActionResponse> getLeaveRoomMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "LeaveRoom",
      requestType = quiz.grpc.LeaveRoomRequest.class,
      responseType = quiz.grpc.RoomActionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<quiz.grpc.LeaveRoomRequest,
      quiz.grpc.RoomActionResponse> getLeaveRoomMethod() {
    io.grpc.MethodDescriptor<quiz.grpc.LeaveRoomRequest, quiz.grpc.RoomActionResponse> getLeaveRoomMethod;
    if ((getLeaveRoomMethod = QuizServiceGrpc.getLeaveRoomMethod) == null) {
      synchronized (QuizServiceGrpc.class) {
        if ((getLeaveRoomMethod = QuizServiceGrpc.getLeaveRoomMethod) == null) {
          QuizServiceGrpc.getLeaveRoomMethod = getLeaveRoomMethod =
              io.grpc.MethodDescriptor.<quiz.grpc.LeaveRoomRequest, quiz.grpc.RoomActionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "LeaveRoom"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  quiz.grpc.LeaveRoomRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  quiz.grpc.RoomActionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QuizServiceMethodDescriptorSupplier("LeaveRoom"))
              .build();
        }
      }
    }
    return getLeaveRoomMethod;
  }

  private static volatile io.grpc.MethodDescriptor<quiz.grpc.ReadyRequest,
      quiz.grpc.ReadyResponse> getReadyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Ready",
      requestType = quiz.grpc.ReadyRequest.class,
      responseType = quiz.grpc.ReadyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<quiz.grpc.ReadyRequest,
      quiz.grpc.ReadyResponse> getReadyMethod() {
    io.grpc.MethodDescriptor<quiz.grpc.ReadyRequest, quiz.grpc.ReadyResponse> getReadyMethod;
    if ((getReadyMethod = QuizServiceGrpc.getReadyMethod) == null) {
      synchronized (QuizServiceGrpc.class) {
        if ((getReadyMethod = QuizServiceGrpc.getReadyMethod) == null) {
          QuizServiceGrpc.getReadyMethod = getReadyMethod =
              io.grpc.MethodDescriptor.<quiz.grpc.ReadyRequest, quiz.grpc.ReadyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Ready"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  quiz.grpc.ReadyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  quiz.grpc.ReadyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QuizServiceMethodDescriptorSupplier("Ready"))
              .build();
        }
      }
    }
    return getReadyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<quiz.grpc.SubmitAnswerRequest,
      quiz.grpc.SubmitAnswerResponse> getSubmitAnswerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SubmitAnswer",
      requestType = quiz.grpc.SubmitAnswerRequest.class,
      responseType = quiz.grpc.SubmitAnswerResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<quiz.grpc.SubmitAnswerRequest,
      quiz.grpc.SubmitAnswerResponse> getSubmitAnswerMethod() {
    io.grpc.MethodDescriptor<quiz.grpc.SubmitAnswerRequest, quiz.grpc.SubmitAnswerResponse> getSubmitAnswerMethod;
    if ((getSubmitAnswerMethod = QuizServiceGrpc.getSubmitAnswerMethod) == null) {
      synchronized (QuizServiceGrpc.class) {
        if ((getSubmitAnswerMethod = QuizServiceGrpc.getSubmitAnswerMethod) == null) {
          QuizServiceGrpc.getSubmitAnswerMethod = getSubmitAnswerMethod =
              io.grpc.MethodDescriptor.<quiz.grpc.SubmitAnswerRequest, quiz.grpc.SubmitAnswerResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SubmitAnswer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  quiz.grpc.SubmitAnswerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  quiz.grpc.SubmitAnswerResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QuizServiceMethodDescriptorSupplier("SubmitAnswer"))
              .build();
        }
      }
    }
    return getSubmitAnswerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<quiz.grpc.SubscribeRequest,
      quiz.grpc.RoomEvent> getStreamRoomEventsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StreamRoomEvents",
      requestType = quiz.grpc.SubscribeRequest.class,
      responseType = quiz.grpc.RoomEvent.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<quiz.grpc.SubscribeRequest,
      quiz.grpc.RoomEvent> getStreamRoomEventsMethod() {
    io.grpc.MethodDescriptor<quiz.grpc.SubscribeRequest, quiz.grpc.RoomEvent> getStreamRoomEventsMethod;
    if ((getStreamRoomEventsMethod = QuizServiceGrpc.getStreamRoomEventsMethod) == null) {
      synchronized (QuizServiceGrpc.class) {
        if ((getStreamRoomEventsMethod = QuizServiceGrpc.getStreamRoomEventsMethod) == null) {
          QuizServiceGrpc.getStreamRoomEventsMethod = getStreamRoomEventsMethod =
              io.grpc.MethodDescriptor.<quiz.grpc.SubscribeRequest, quiz.grpc.RoomEvent>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StreamRoomEvents"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  quiz.grpc.SubscribeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  quiz.grpc.RoomEvent.getDefaultInstance()))
              .setSchemaDescriptor(new QuizServiceMethodDescriptorSupplier("StreamRoomEvents"))
              .build();
        }
      }
    }
    return getStreamRoomEventsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<quiz.grpc.SubscribeRequest,
      quiz.grpc.GameEvent> getStreamGameEventsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StreamGameEvents",
      requestType = quiz.grpc.SubscribeRequest.class,
      responseType = quiz.grpc.GameEvent.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<quiz.grpc.SubscribeRequest,
      quiz.grpc.GameEvent> getStreamGameEventsMethod() {
    io.grpc.MethodDescriptor<quiz.grpc.SubscribeRequest, quiz.grpc.GameEvent> getStreamGameEventsMethod;
    if ((getStreamGameEventsMethod = QuizServiceGrpc.getStreamGameEventsMethod) == null) {
      synchronized (QuizServiceGrpc.class) {
        if ((getStreamGameEventsMethod = QuizServiceGrpc.getStreamGameEventsMethod) == null) {
          QuizServiceGrpc.getStreamGameEventsMethod = getStreamGameEventsMethod =
              io.grpc.MethodDescriptor.<quiz.grpc.SubscribeRequest, quiz.grpc.GameEvent>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StreamGameEvents"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  quiz.grpc.SubscribeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  quiz.grpc.GameEvent.getDefaultInstance()))
              .setSchemaDescriptor(new QuizServiceMethodDescriptorSupplier("StreamGameEvents"))
              .build();
        }
      }
    }
    return getStreamGameEventsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static QuizServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QuizServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QuizServiceStub>() {
        @java.lang.Override
        public QuizServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QuizServiceStub(channel, callOptions);
        }
      };
    return QuizServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static QuizServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QuizServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QuizServiceBlockingStub>() {
        @java.lang.Override
        public QuizServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QuizServiceBlockingStub(channel, callOptions);
        }
      };
    return QuizServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static QuizServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QuizServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QuizServiceFutureStub>() {
        @java.lang.Override
        public QuizServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QuizServiceFutureStub(channel, callOptions);
        }
      };
    return QuizServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void hello(quiz.grpc.HelloRequest request,
        io.grpc.stub.StreamObserver<quiz.grpc.HelloResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHelloMethod(), responseObserver);
    }

    /**
     */
    default void quit(quiz.grpc.QuitRequest request,
        io.grpc.stub.StreamObserver<quiz.grpc.QuitResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQuitMethod(), responseObserver);
    }

    /**
     */
    default void listRooms(quiz.grpc.ListRoomsRequest request,
        io.grpc.stub.StreamObserver<quiz.grpc.ListRoomsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListRoomsMethod(), responseObserver);
    }

    /**
     */
    default void createRoom(quiz.grpc.CreateRoomRequest request,
        io.grpc.stub.StreamObserver<quiz.grpc.RoomActionResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateRoomMethod(), responseObserver);
    }

    /**
     */
    default void joinRoom(quiz.grpc.JoinRoomRequest request,
        io.grpc.stub.StreamObserver<quiz.grpc.RoomActionResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getJoinRoomMethod(), responseObserver);
    }

    /**
     */
    default void leaveRoom(quiz.grpc.LeaveRoomRequest request,
        io.grpc.stub.StreamObserver<quiz.grpc.RoomActionResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLeaveRoomMethod(), responseObserver);
    }

    /**
     */
    default void ready(quiz.grpc.ReadyRequest request,
        io.grpc.stub.StreamObserver<quiz.grpc.ReadyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReadyMethod(), responseObserver);
    }

    /**
     */
    default void submitAnswer(quiz.grpc.SubmitAnswerRequest request,
        io.grpc.stub.StreamObserver<quiz.grpc.SubmitAnswerResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSubmitAnswerMethod(), responseObserver);
    }

    /**
     */
    default void streamRoomEvents(quiz.grpc.SubscribeRequest request,
        io.grpc.stub.StreamObserver<quiz.grpc.RoomEvent> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStreamRoomEventsMethod(), responseObserver);
    }

    /**
     */
    default void streamGameEvents(quiz.grpc.SubscribeRequest request,
        io.grpc.stub.StreamObserver<quiz.grpc.GameEvent> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStreamGameEventsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service QuizService.
   */
  public static abstract class QuizServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return QuizServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service QuizService.
   */
  public static final class QuizServiceStub
      extends io.grpc.stub.AbstractAsyncStub<QuizServiceStub> {
    private QuizServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QuizServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QuizServiceStub(channel, callOptions);
    }

    /**
     */
    public void hello(quiz.grpc.HelloRequest request,
        io.grpc.stub.StreamObserver<quiz.grpc.HelloResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHelloMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void quit(quiz.grpc.QuitRequest request,
        io.grpc.stub.StreamObserver<quiz.grpc.QuitResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQuitMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listRooms(quiz.grpc.ListRoomsRequest request,
        io.grpc.stub.StreamObserver<quiz.grpc.ListRoomsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListRoomsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createRoom(quiz.grpc.CreateRoomRequest request,
        io.grpc.stub.StreamObserver<quiz.grpc.RoomActionResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateRoomMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void joinRoom(quiz.grpc.JoinRoomRequest request,
        io.grpc.stub.StreamObserver<quiz.grpc.RoomActionResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getJoinRoomMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void leaveRoom(quiz.grpc.LeaveRoomRequest request,
        io.grpc.stub.StreamObserver<quiz.grpc.RoomActionResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLeaveRoomMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void ready(quiz.grpc.ReadyRequest request,
        io.grpc.stub.StreamObserver<quiz.grpc.ReadyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReadyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void submitAnswer(quiz.grpc.SubmitAnswerRequest request,
        io.grpc.stub.StreamObserver<quiz.grpc.SubmitAnswerResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSubmitAnswerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void streamRoomEvents(quiz.grpc.SubscribeRequest request,
        io.grpc.stub.StreamObserver<quiz.grpc.RoomEvent> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getStreamRoomEventsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void streamGameEvents(quiz.grpc.SubscribeRequest request,
        io.grpc.stub.StreamObserver<quiz.grpc.GameEvent> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getStreamGameEventsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service QuizService.
   */
  public static final class QuizServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<QuizServiceBlockingStub> {
    private QuizServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QuizServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QuizServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public quiz.grpc.HelloResponse hello(quiz.grpc.HelloRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHelloMethod(), getCallOptions(), request);
    }

    /**
     */
    public quiz.grpc.QuitResponse quit(quiz.grpc.QuitRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQuitMethod(), getCallOptions(), request);
    }

    /**
     */
    public quiz.grpc.ListRoomsResponse listRooms(quiz.grpc.ListRoomsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListRoomsMethod(), getCallOptions(), request);
    }

    /**
     */
    public quiz.grpc.RoomActionResponse createRoom(quiz.grpc.CreateRoomRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateRoomMethod(), getCallOptions(), request);
    }

    /**
     */
    public quiz.grpc.RoomActionResponse joinRoom(quiz.grpc.JoinRoomRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getJoinRoomMethod(), getCallOptions(), request);
    }

    /**
     */
    public quiz.grpc.RoomActionResponse leaveRoom(quiz.grpc.LeaveRoomRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLeaveRoomMethod(), getCallOptions(), request);
    }

    /**
     */
    public quiz.grpc.ReadyResponse ready(quiz.grpc.ReadyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReadyMethod(), getCallOptions(), request);
    }

    /**
     */
    public quiz.grpc.SubmitAnswerResponse submitAnswer(quiz.grpc.SubmitAnswerRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSubmitAnswerMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<quiz.grpc.RoomEvent> streamRoomEvents(
        quiz.grpc.SubscribeRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getStreamRoomEventsMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<quiz.grpc.GameEvent> streamGameEvents(
        quiz.grpc.SubscribeRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getStreamGameEventsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service QuizService.
   */
  public static final class QuizServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<QuizServiceFutureStub> {
    private QuizServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QuizServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QuizServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<quiz.grpc.HelloResponse> hello(
        quiz.grpc.HelloRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHelloMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<quiz.grpc.QuitResponse> quit(
        quiz.grpc.QuitRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQuitMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<quiz.grpc.ListRoomsResponse> listRooms(
        quiz.grpc.ListRoomsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListRoomsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<quiz.grpc.RoomActionResponse> createRoom(
        quiz.grpc.CreateRoomRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateRoomMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<quiz.grpc.RoomActionResponse> joinRoom(
        quiz.grpc.JoinRoomRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getJoinRoomMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<quiz.grpc.RoomActionResponse> leaveRoom(
        quiz.grpc.LeaveRoomRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLeaveRoomMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<quiz.grpc.ReadyResponse> ready(
        quiz.grpc.ReadyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReadyMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<quiz.grpc.SubmitAnswerResponse> submitAnswer(
        quiz.grpc.SubmitAnswerRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSubmitAnswerMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_HELLO = 0;
  private static final int METHODID_QUIT = 1;
  private static final int METHODID_LIST_ROOMS = 2;
  private static final int METHODID_CREATE_ROOM = 3;
  private static final int METHODID_JOIN_ROOM = 4;
  private static final int METHODID_LEAVE_ROOM = 5;
  private static final int METHODID_READY = 6;
  private static final int METHODID_SUBMIT_ANSWER = 7;
  private static final int METHODID_STREAM_ROOM_EVENTS = 8;
  private static final int METHODID_STREAM_GAME_EVENTS = 9;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_HELLO:
          serviceImpl.hello((quiz.grpc.HelloRequest) request,
              (io.grpc.stub.StreamObserver<quiz.grpc.HelloResponse>) responseObserver);
          break;
        case METHODID_QUIT:
          serviceImpl.quit((quiz.grpc.QuitRequest) request,
              (io.grpc.stub.StreamObserver<quiz.grpc.QuitResponse>) responseObserver);
          break;
        case METHODID_LIST_ROOMS:
          serviceImpl.listRooms((quiz.grpc.ListRoomsRequest) request,
              (io.grpc.stub.StreamObserver<quiz.grpc.ListRoomsResponse>) responseObserver);
          break;
        case METHODID_CREATE_ROOM:
          serviceImpl.createRoom((quiz.grpc.CreateRoomRequest) request,
              (io.grpc.stub.StreamObserver<quiz.grpc.RoomActionResponse>) responseObserver);
          break;
        case METHODID_JOIN_ROOM:
          serviceImpl.joinRoom((quiz.grpc.JoinRoomRequest) request,
              (io.grpc.stub.StreamObserver<quiz.grpc.RoomActionResponse>) responseObserver);
          break;
        case METHODID_LEAVE_ROOM:
          serviceImpl.leaveRoom((quiz.grpc.LeaveRoomRequest) request,
              (io.grpc.stub.StreamObserver<quiz.grpc.RoomActionResponse>) responseObserver);
          break;
        case METHODID_READY:
          serviceImpl.ready((quiz.grpc.ReadyRequest) request,
              (io.grpc.stub.StreamObserver<quiz.grpc.ReadyResponse>) responseObserver);
          break;
        case METHODID_SUBMIT_ANSWER:
          serviceImpl.submitAnswer((quiz.grpc.SubmitAnswerRequest) request,
              (io.grpc.stub.StreamObserver<quiz.grpc.SubmitAnswerResponse>) responseObserver);
          break;
        case METHODID_STREAM_ROOM_EVENTS:
          serviceImpl.streamRoomEvents((quiz.grpc.SubscribeRequest) request,
              (io.grpc.stub.StreamObserver<quiz.grpc.RoomEvent>) responseObserver);
          break;
        case METHODID_STREAM_GAME_EVENTS:
          serviceImpl.streamGameEvents((quiz.grpc.SubscribeRequest) request,
              (io.grpc.stub.StreamObserver<quiz.grpc.GameEvent>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getHelloMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              quiz.grpc.HelloRequest,
              quiz.grpc.HelloResponse>(
                service, METHODID_HELLO)))
        .addMethod(
          getQuitMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              quiz.grpc.QuitRequest,
              quiz.grpc.QuitResponse>(
                service, METHODID_QUIT)))
        .addMethod(
          getListRoomsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              quiz.grpc.ListRoomsRequest,
              quiz.grpc.ListRoomsResponse>(
                service, METHODID_LIST_ROOMS)))
        .addMethod(
          getCreateRoomMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              quiz.grpc.CreateRoomRequest,
              quiz.grpc.RoomActionResponse>(
                service, METHODID_CREATE_ROOM)))
        .addMethod(
          getJoinRoomMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              quiz.grpc.JoinRoomRequest,
              quiz.grpc.RoomActionResponse>(
                service, METHODID_JOIN_ROOM)))
        .addMethod(
          getLeaveRoomMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              quiz.grpc.LeaveRoomRequest,
              quiz.grpc.RoomActionResponse>(
                service, METHODID_LEAVE_ROOM)))
        .addMethod(
          getReadyMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              quiz.grpc.ReadyRequest,
              quiz.grpc.ReadyResponse>(
                service, METHODID_READY)))
        .addMethod(
          getSubmitAnswerMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              quiz.grpc.SubmitAnswerRequest,
              quiz.grpc.SubmitAnswerResponse>(
                service, METHODID_SUBMIT_ANSWER)))
        .addMethod(
          getStreamRoomEventsMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              quiz.grpc.SubscribeRequest,
              quiz.grpc.RoomEvent>(
                service, METHODID_STREAM_ROOM_EVENTS)))
        .addMethod(
          getStreamGameEventsMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              quiz.grpc.SubscribeRequest,
              quiz.grpc.GameEvent>(
                service, METHODID_STREAM_GAME_EVENTS)))
        .build();
  }

  private static abstract class QuizServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    QuizServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return quiz.grpc.QuizProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("QuizService");
    }
  }

  private static final class QuizServiceFileDescriptorSupplier
      extends QuizServiceBaseDescriptorSupplier {
    QuizServiceFileDescriptorSupplier() {}
  }

  private static final class QuizServiceMethodDescriptorSupplier
      extends QuizServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    QuizServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (QuizServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new QuizServiceFileDescriptorSupplier())
              .addMethod(getHelloMethod())
              .addMethod(getQuitMethod())
              .addMethod(getListRoomsMethod())
              .addMethod(getCreateRoomMethod())
              .addMethod(getJoinRoomMethod())
              .addMethod(getLeaveRoomMethod())
              .addMethod(getReadyMethod())
              .addMethod(getSubmitAnswerMethod())
              .addMethod(getStreamRoomEventsMethod())
              .addMethod(getStreamGameEventsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
