import autoBind from 'auto-bind';
import DbBase from './db-base';
import User from './user';

export default class Reservation extends DbBase{
	startDate:Date;
	endDate:Date;
	userId:string;
	approved:Boolean;
	hallIds:Array<string> = [];
	reason:string;
	email:string;

	constructor(data:any){
		super(data);
		this.startDate = new Date(data.startDate);
		this.endDate = new Date(data.endDate);
		this.userId = data.user;
		this.approved = data.approved;
		this.hallIds = data.halls;
		this.reason = data.reason;
	}

	getDay():number{
		return this.startDate.getDay();
	}

	getStartHour():number{
		return this.startDate.getUTCHours();
	}

	getStartMinute():number{
		return this.startDate.getUTCMinutes();
	}

	getEndHour():number{
		let endHour =  this.endDate.getUTCHours();
		if (endHour == 0){
			return 24;
		}
		return endHour;
	}

	getEndMinute():number{
		return this.endDate.getUTCMinutes();
	}
}