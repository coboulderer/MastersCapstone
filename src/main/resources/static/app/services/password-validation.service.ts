import {Injectable} from "@angular/core";
import {Passwords} from "../model/passwords";

@Injectable()
export class PasswordValidationService {

    checkMatching(passwords: Passwords) {

        let matching = true;
        if (passwords.password != null &&
            passwords.passwordConf != null &&
            passwords.password.length > 5 &&
            passwords.passwordConf.length > 5) {

            matching = passwords.password === passwords.passwordConf;
        }
        return matching;
    }

    checkComplexity(passwords: Passwords) {
        // TODO Check Password Complexity using Regex - LOW Priority for now
    }
}