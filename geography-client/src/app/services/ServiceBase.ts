import {grpc} from "@improbable-eng/grpc-web";
import {ServiceError} from "@kgi/geograply-interface/geography_pb_service";
import * as google_protobuf_wrappers_pb from 'google-protobuf/google/protobuf/wrappers_pb';
export class ServiceBase {

  makeHandler(onSuccess: (s: any) => any, onError?: (e: ServiceError) => void): (err: any, success: any) => void {
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

  handleError(err: ServiceError) {
    const codeAsString = grpc.Code[err.code]
    console.error(`GRPC Error ${err.code} : ${codeAsString} : ${err.message}`);
    if (err.metadata.headersMap.errorsInfo) {
      for (const errInfo of err.metadata.headersMap.errorsInfo) {
        console.error(`Backend Error Info: ${errInfo}`);
      }
    }
  }

  stringValueOf( text: string ): google_protobuf_wrappers_pb.StringValue {
    const sr: google_protobuf_wrappers_pb.StringValue = new google_protobuf_wrappers_pb.StringValue();
    sr.setValue( text );
    return sr;
  }
}
