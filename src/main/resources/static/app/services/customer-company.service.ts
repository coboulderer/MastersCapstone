import {Injectable} from "@angular/core";
import {Http, Headers, Response} from "@angular/http";
import {Observable} from "rxjs/Rx";
import {CustomerCompany} from "../model/customer-company";

@Injectable()
export class CustomerCompanyService {

    constructor(private http: Http){}

    getAllCustomerCompanies() {
        console.log("CustomerCompanyService.getAllCustomerCompanies");
        let url = "http://localhost:8080/api/secure/customer/company";
        let header = this.getHeaders();
        return this.http.get(url, {headers: header}).
            map(this.parseData).
            catch(this.parseError);
    }

    createNewCustomerCompany(customerCompany: CustomerCompany) {
        console.log("CustomerCompanyService.createNewCustomerCompany()");
        let url = "http://localhost:8080/api/secure/customer/company";
        let body = JSON.stringify(customerCompany);
        let header = this.getHeaders();
        return this.http.post(url, body, {headers: header}).
        map(this.parseData).
        catch(this.parseError);
    }

    deleteCustomerCompany(customerCompanyId: number) {
        console.log("CustomerCompanyService.deleteCustomerCompany()");
        let url = "http://localhost:8080/api/secure/customer/company/" + customerCompanyId;
        let header = this.getHeaders();
        return this.http.delete(url, {headers: header}).
        map(this.parseDelete).
        catch(this.parseError);
    }

    private parseData(res: Response) {
        console.log("CustomerCompanyService.parseData(res) called");
        let body = res.json();
        console.log("Returned JSON body" + JSON.stringify(body));
        return body || {};
    }

    private parseDelete(response: Response) {
        console.log("CustomerCompanyService.parseDelete(response) called");
        let resString = JSON.parse(JSON.stringify(response))._body;
        return resString || {};
    }

    private parseError(error: any) {
        console.log("CustomerCompanyService.parseError(error) called");
        let errMsg = "";
        if (error.status == 400) {
            errMsg = "A Bad Request was made - try your action again";
        } else if (error.status == 404) {
            errMsg = "The resource(s) you requested could not be found on the server";
        } else if (error.status = 500) {
            errMsg = "A Server Error occurred, try your request again";
        } else {
            errMsg = "An unknown error occurred processing your request";
        }
        return Observable.throw(errMsg);
    }

    private getAuthToken() {
        return sessionStorage.getItem("authToken");
    }

    private getHeaders() {
        let header = new Headers();
        header.append("Content-Type", "application/json");
        header.append("auth-token", this.getAuthToken());
        return header;
    }
}
