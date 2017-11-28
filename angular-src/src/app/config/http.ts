import { HttpHeaders } from "@angular/common/http";

export default class Config{
	static host:string = "http://localhost:3000";

	static headers:HttpHeaders = new HttpHeaders().set('content-type', 'application/json').set("Authorization", localStorage.getItem("authToken"));

	static refreshHeaders(){
		this.headers = new HttpHeaders().set('content-type', 'application/json').set("Authorization", localStorage.getItem("authToken"));
	}
}