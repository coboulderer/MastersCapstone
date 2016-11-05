import {Injectable} from "@angular/core";
import {Headers} from "@angular/http";

@Injectable()
export class HeaderService {

    getStandardHeaders() {
        let header = new Headers();
        header.append("Content-Type", "application/json");
        header.append("auth-token", this.getAuthToken());
        return header;
    }

    private getAuthToken() {
        return sessionStorage.getItem("authToken");
    }
}