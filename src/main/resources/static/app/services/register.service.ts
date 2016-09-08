import {Injectable} from "@angular/core";
import {Http, Headers} from "@angular/http";
import {User} from "../model/user";

@Injectable()
export class RegisterService {

    constructor(private http: Http){}

    registerUser(user: User) {
        console.log("RegisterService.registerUser(user) called");

        // let url = "http://localhost:8080/api/register/user"
        // let header = new Headers({'Content-Type': 'application/json'});
        // this.http.post(url, JSON.stringify(user), {headers: header});
    }
}