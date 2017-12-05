import autoBind from 'auto-bind';
import DbBase from './db-base';

export default class Result extends DbBase{
  private _id:string;
  getId(){
    return this._id;
  }

  constructor(data:any = null){
    super(data);
    autoBind(this);
    if (data){
      this.userId = data.userId;
      this.exerciseNr = data.exerciseNr;
      this.word = data.word;
      this.amountCorrect = data.amountCorrect;
      this.amountWrong = data.amountWrong;
      this.date = new Date(data.date);
    }
  }

  userId:number;
  exerciseNr:number;
  word:string;
  amountCorrect:number;
  amountWrong:number;
  date:Date;
}
