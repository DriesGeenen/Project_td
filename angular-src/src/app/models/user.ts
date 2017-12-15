import autoBind from "auto-bind";
import DbBase from "./db-base";

export default class User extends DbBase{

	constructor(data:any = null){
		super(data);
		autoBind(this);
		if (data){
			this.name = data.name;
			this.email = data.email;
			this.password = data.password;
			this.role = data.role;
		}
	}
	name:string;
	email:string;
	password:string;
	role:string;
}
