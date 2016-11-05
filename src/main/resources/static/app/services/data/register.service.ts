import {Injectable} from "@angular/core";
import {Http, Headers} from "@angular/http";
import {Observable} from "rxjs/Observable";

import {User} from "../../model/user";
import {ResponseParseService} from "../util/response-parse.service";

@Injectable()
export class RegisterService {

    constructor(private http: Http, private responseParseService: ResponseParseService) {
    }

    registerUser(user: User): Observable<User> {
        console.log("RegisterService.registerUser(user) called");
        let url = "http://localhost:8080/api/register/user";
        let body = JSON.stringify(user);
        let header = new Headers({'Content-Type': 'application/json'});
        return this.http.post(url, body, {headers: header}).
            map(this.responseParseService.parseData).
            catch(this.responseParseService.parseError);
    }
}