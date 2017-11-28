import autoBind from "auto-bind";
import DbBase from "./db-base";

export default class User extends DbBase{
	private _id:string;
	getId(){
		return this._id;
	}

	constructor(data:any = null){
		super(data);
		autoBind(this);
		if (data){
			this.name = data.name;
			this.lastName = data.lastName;
			this.email = data.email;
			this.role = data.role;
		}
	}

	name:string;
	lastName:string;
	email:string;
	role:string;
}