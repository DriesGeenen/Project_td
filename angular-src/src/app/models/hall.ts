import autoBind from 'auto-bind';
import Reservation from './reservation';
import DbBase from './db-base';

export default class Hall extends DbBase{

  constructor(data:any ={}){
    super(data);
    autoBind(this);
    this.hallType = data.hallType;
    this.description = data.description;
    console.log(data.calendar);
    for (let i in data.calendar){
      this.calendar.push(new Reservation(data.calendar[i]));
    }
    
    this.coRentableHalls = data.coRentableHalls;
    this.userPrice = data.userPrice;
    this.businessPrice = data.businessPrice;
    this.isDeleted = data.isDeleted;
  }

  hallType:string;
  description:string;
  calendar:Array<Reservation> = [];
  coRentableHalls:string;
  userPrice:number;
  businessPrice:number;
  isDeleted:boolean;
}
