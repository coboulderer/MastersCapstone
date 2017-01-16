import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {CustomerCompany} from "../../model/customer-company";
import {ResponseParseService} from "../util/response-parse.service";
import {HeaderService} from "../util/header.service";

@Injectable()
export class CustomerCompanyService {

    private companyUrl: string = "http://localhost:8080/api/secure/customer/company";

    constructor(private http: Http, private responseParseService: ResponseParseService,
                private headerService: HeaderService){}

    getAllCustomerCompanies() {
        console.log("CustomerCompanyService.getAllCustomerCompanies");
        let header = this.headerService.getStandardHeaders();
        return this.http.get(this.companyUrl, {headers: header}).
            map(this.responseParseService.parseData).
            catch(this.responseParseService.parseError);
    }

    getAllCustomerCampaigns(customerCompany: CustomerCompany) {
        console.log("CustomerCompanyService.getAllCustomerCampaigns()");
        let url = this.companyUrl + "/" + customerCompany.id + "/campaigns";
        let header = this.headerService.getStandardHeaders();
        return this.http.get(url, {headers: header}).
            map(this.responseParseService.parseData).
            catch(this.responseParseService.parseError);
    }

    createNewCustomerCompany(customerCompany: CustomerCompany) {
        console.log("CustomerCompanyService.createNewCustomerCompany()");
        let body = JSON.stringify(customerCompany);
        let header = this.headerService.getStandardHeaders();
        return this.http.post(this.companyUrl, body, {headers: header}).
            map(this.responseParseService.parseData).
            catch(this.responseParseService.parseError);
    }

    deleteCustomerCompany(customerCompanyId: number) {
        console.log("CustomerCompanyService.deleteCustomerCompany()");
        let url = this.companyUrl + "/" + customerCompanyId;
        let header = this.headerService.getStandardHeaders();
        return this.http.delete(url, {headers: header}).
            map(this.responseParseService.parseDelete).
            catch(this.responseParseService.parseError);
    }
}
