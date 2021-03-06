import {Campaign} from "./campaign"

export class User {
    public userId: number;
    public firstName: string;
    public lastName: string;
    public emailAddress: string;
    public userName: string;
    public password: string;
    public campaigns: Campaign[];
}