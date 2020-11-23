package sh.spartan.crud.phalanx.utils;

import org.springframework.http.*;
import org.springframework.stereotype.Component;

@Component
public class Util {

    private boolean isEmpty(String text) {
        return text == null || text.trim().equals("");
    }

    /**
     *
     * Method that receives a Throwable object and prepares a 500 error response with the details about the Exception thrown.
     *
     * @param throwable the Throwable object to be read
     * @return ResponseEntity object with the details about the Exception.
     */
    public ResponseEntity<Response<?>> notifyError(Throwable throwable) {
        return this.getResponse(500, null, throwable.getMessage(), new Response<String>());
    }

    /**
     *
     * Method that creates a proper ResponseEntity object for REST response.
     *
     * @param code HTTP status code in int format
     * @param payload the content to be sent back to the consumer
     * @param message some extra information sent back to the consumer
     * @param response the Response object instance specialized and created by the calling method. If null then a generic Response object will be instantiated.
     * @return ResponseEntity object specialized based on the response parameter sent
     */
    public ResponseEntity<Response<?>> getResponse(int code, Object payload, String message, Response<?> response) {
        Response<?> resp;

        if(response != null) {
            resp = response;
        } else {
            resp = new Response<>();
        }

        String error = "ERROR";

        switch (code) {
            case 400:
                resp.setCode(400);
                resp.setRecordset(null);
                resp.setMsg(!isEmpty(message) ? message : "Bad Syntax");
                resp.setType(error);
                return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
            case 401:
                resp.setCode(401);
                resp.setRecordset(null);
                resp.setMsg(!isEmpty(message) ? message : "Missing Auth");
                resp.setType(error);

                return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
            case 403:
                resp.setCode(403);
                resp.setRecordset(payload);
                resp.setMsg(!isEmpty(message) ? message : "Forbidden");
                resp.setType(error);
                return new ResponseEntity<>(resp, HttpStatus.FORBIDDEN);
            case 404:
                resp.setCode(404);
                resp.setRecordset(null);
                resp.setMsg(!isEmpty(message) ? message : "Not found");
                resp.setType(error);
                return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
            case 200:
                resp.setCode(200);
                resp.setMsg(!isEmpty(message) ? message : "OK");
                resp.setType("SUCCESS");
                resp.setRecordset(payload);
                return new ResponseEntity<>(resp, HttpStatus.OK);

            case 204:
                resp.setCode(204);
                resp.setMsg(!isEmpty(message) ? message : "No Content");
                resp.setType("SUCCESS");
                resp.setRecordset(payload);
                return new ResponseEntity<>(resp, HttpStatus.NO_CONTENT);

            case 503:
                resp.setCode(503);
                resp.setMsg(!isEmpty(message) ? message : "Service Unavailable");
                resp.setType(error);
                resp.setRecordset(payload);
                return new ResponseEntity<>(resp, HttpStatus.SERVICE_UNAVAILABLE);

            default:
                resp.setCode(500);
                resp.setRecordset(null);
                resp.setMsg(!isEmpty(message) ? message : "Server Error");
                resp.setType(error);
                return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }
}