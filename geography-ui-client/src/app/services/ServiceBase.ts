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

  handleError(err: any) {
    const codeAsString = 'tbd';
    console.error(`GRPC Error ${err.code} : ${codeAsString} : ${err.message}`);
    if (err.metadata.headersMap.errorsInfo) {
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
