import {Injectable} from "@angular/core";

@Injectable()
export class DateService {

    toUtcDate(dateMillis: number) {
        let date = new Date(dateMillis);
        let utc = new Date(date.getUTCFullYear(), date.getUTCMonth(), date.getUTCDate(),
            date.getUTCHours(), date.getUTCMinutes(), date.getUTCSeconds());
        return utc;
    }
}