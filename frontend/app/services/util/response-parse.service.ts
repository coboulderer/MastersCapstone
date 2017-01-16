import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {Response} from "@angular/http";

@Injectable()
export class ResponseParseService {

    constructor(){}

    parseData(res: Response) {
        console.log("ResponseParseService.parseData(res) called");
        let body = res.json();
        console.log("Returned JSON body" + JSON.stringify(body));
        return body || {};
    }

    parseDelete(response: Response) {
        console.log("ResponseParseService.parseDelete(response) called");
        let resString = JSON.parse(JSON.stringify(response))._body;
        return resString || {};
    }

    parseError(error: any) {
        console.log("ResponseParseService.parseError(error) called");
        let errMsg = "";
        if (error.status == 400) {
            errMsg = "A Bad Request was made - try your action again";
        } else if (error.status == 404) {
            errMsg = "The resource(s) you requested could not be found on the server";
        } else if (error.status == 409) {
            errMsg = "The Username you have picked is already being used.  Pick Another Username.";
        } else if (error.status = 500) {
            errMsg = "A Server Error occurred, try your request again";
        } else {
            errMsg = "An unknown error occurred processing your request";
        }
        return Observable.throw(errMsg);
    }
}