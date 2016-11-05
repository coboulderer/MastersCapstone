import {Injectable} from "@angular/core";
import {Http, Headers} from "@angular/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs/Observable";


@Injectable()
export class LoginService {

    constructor(private http: Http, private router: Router){}

    login(credentials) {
        let url = "http://localhost:8080/api/login/user";
        let header = new Headers({'Content-Type': 'application/json'});
        let body = JSON.stringify(credentials);
        return this.http.post(url, body, {headers: header}).catch(this.parseLoginError);
    }

    logout() {
        sessionStorage.clear();
        this.router.navigate(["/login"]);
    }

    parseLoginResponse(response, credentials) {
        let authToken = JSON.parse(JSON.stringify(response))._body;
        console.log("AuthToken:: " + authToken);
        sessionStorage.setItem("authToken", authToken);
        sessionStorage.setItem("userName", credentials.username);
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