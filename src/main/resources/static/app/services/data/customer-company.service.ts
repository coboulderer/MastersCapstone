import {Injectable} from "@angular/core";
import {Http, Headers} from "@angular/http";
import {CustomerCompany} from "../../model/customer-company";
import {ResponseParseService} from "../util/response-parse.service";

@Injectable()
export class CustomerCompanyService {

    private companyUrl: string = "http://localhost:8080/api/secure/customer/company";

    constructor(private http: Http, private responseParseService: ResponseParseService){}

    getAllCustomerCompanies() {
        console.log("CustomerCompanyService.getAllCustomerCompanies");
        let header = this.getHeaders();
        return this.http.get(this.companyUrl, {headers: header}).
            map(this.responseParseService.parseData).
            catch(this.responseParseService.parseError);
    }

    createNewCustomerCompany(customerCompany: CustomerCompany) {
        console.log("CustomerCompanyService.createNewCustomerCompany()");
        let body = JSON.stringify(customerCompany);
        let header = this.getHeaders();
        return this.http.post(this.companyUrl, body, {headers: header}).
        map(this.responseParseService.parseData).
        catch(this.responseParseService.parseError);
    }

    deleteCustomerCompany(customerCompanyId: number) {
        console.log("CustomerCompanyService.deleteCustomerCompany()");
        let url = this.companyUrl + "/" + customerCompanyId;
        let header = this.getHeaders();
        return this.http.delete(url, {headers: header}).
        map(this.responseParseService.parseDelete).
        catch(this.responseParseService.parseError);
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
