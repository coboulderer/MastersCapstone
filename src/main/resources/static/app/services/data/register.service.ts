import {Injectable} from "@angular/core";
import {Http, Headers, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";
import "rxjs/add/observable/throw";
import "rxjs/add/operator/catch";
import "rxjs/add/operator/map";
import {User} from "../../model/user";

@Injectable()
export class RegisterService {

    constructor(private http: Http) {
    }

    registerUser(user: User): Observable<User> {
        console.log("RegisterService.registerUser(user) called");
        let url = "http://localhost:8080/api/register/user";
        let body = JSON.stringify(user);
        let header = new Headers({'Content-Type': 'application/json'});
        return this.http.post(url, body, {headers: header}).
            map(this.parseData).
            catch(this.parseError);
    }

    private parseData(res: Response) {
        console.log("RegisterService.parseData(res) called");
        let body = res.json();
        return body || {};
    }

    private parseError(error: any) {
        console.log("RegisterService.parseError(error) called");
        let errMsg = "";
        if (error.status == 409) {
            errMsg = "The Username you have picked is already being used.  Pick Another Username.";
        } else {
            errMsg = "An unexpected error occurred. Try to register again";
        }
        return Observable.throw(errMsg);
    }
}