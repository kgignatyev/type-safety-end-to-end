import * as grpc from 'grpc-web';

import * as google_protobuf_wrappers_pb from 'google-protobuf/google/protobuf/wrappers_pb';
import {AutzService} from './autz.service';


export class ServiceBase {

  constructor(public authzSvc: AutzService) {
  }

  makeHandler(onSuccess: (s: any) => any, onError?: (e: any) => void): (err: any, success: any) => void {
    return (err: any, success: any) => {
      if (err) {
        this.handleError(err);
        if (onError) {
          onError(err);
        }
      } else {
        onSuccess(success);
      }
    };
  }

  grpcCodeDescription(code:number|null): String {
    if( 0 == code) {
      return "OK"
    } else if( 1== code){
      return "CANCELLED"
    }else if (2==code){
      return "UNKNOWN"
    }else if (3==code){ return  "INVALID_ARGUMENT"}
    else if (4==code){ return  "DEADLINE_EXCEEDED"}
    else if (5==code){ return  "NOT_FOUND"}
    else if (6==code){ return  "ALREADY_EXISTS"}
    else if (7==code){ return  "PERMISSION_DENIED"}
    else if (8==code){ return  "RESOURCE_EXHAUSTED"}
    else if (9==code){ return  "FAILED_PRECONDITION"}
    else if (10==code){ return  "ABORTED"}
    else if (11==code){ return  "OUT_OF_RANGE"}
    else if (12==code){ return  "UNIMPLEMENTED"}
    else if (13==code){ return  "INTERNAL"}
    else if (14==code){ return  "UNAVAILABLE"}
    else if (15==code){ return  "DATA_LOSS"}
    else if (16==code){ return  "UNAUTHENTICATED"}
    return "No description for code: " + code;
  }

  handleError(err: any) {
    const codeAsString = this.grpcCodeDescription( err.code);
    console.error(`GRPC Error ${err.code} : ${codeAsString} : ${err.message}`);
    if (err.metadata.headersMap?.errorsInfo) {
      for (const errInfo of err.metadata.headersMap.errorsInfo) {
        console.error(`Backend Error Info: ${errInfo}`);
      }
    }
  }


  metadata(): grpc.Metadata {
    return this.authzSvc.grpcMetadata();
  }

  stringValueOf( text: string ): google_protobuf_wrappers_pb.StringValue {
    const sr: google_protobuf_wrappers_pb.StringValue = new google_protobuf_wrappers_pb.StringValue();
    sr.setValue( text );
    return sr;
  }

}
