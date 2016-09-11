import {Injectable} from "@angular/core";
import {Http, Headers} from "@angular/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs/Observable";
import "rxjs/add/observable/throw";
import "rxjs/add/operator/catch";
import "rxjs/add/operator/map";

@Injectable()
export class LoginService {

    constructor(private http: Http, private router: Router){}

    sendCredentials(credentials) {
        let url = "http://localhost:8080/api/login/user";
        let header = new Headers({'Content-Type': 'application/json'});
        let body = JSON.stringify(credentials);
        return this.http.post(url, body, {headers: header}).catch(this.parseLoginError);
    }

    logout() {
        sessionStorage.clear();
        this.router.navigate(["/login"]);
    }

    private parseLoginError(error: any) {
        console.log("LoginService.parseError(error) called");
        let errMsg = "";
        if (error.status == 400) {
            errMsg = "A username and password must both be provided";
        } else if (error.status == 401){
            errMsg = "The password you entered is incorrect for this username";
        } else if (error.status == 404){
            errMsg = "The username you entered could not be found";
        } else {
            errMsg = "An unknown error occurred, try logging in again"
        }
        return Observable.throw(errMsg);
    }
}