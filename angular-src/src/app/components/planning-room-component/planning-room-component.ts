import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HallService } from '../../services/hall.service';
import Hall from '../../models/hall';
import Reservation from '../../models/reservation';
import autoBind from 'auto-bind';
import { ReservationService } from '../../services/reservation.service';
import { AuthService } from '../../services/auth.service';

class PlannedCell{
  reservation:Reservation = null;
  day:number;
  hour:number;
  quarter:number;
  date:Date;
  
  getClass():string{
    if (!this.reservation)
      return "free";
    if (this.reservation.getId() == null)
        return "not-added";
    let result = "reserved-item ";
    if(this.reservation.approved)
      result += "approved";
    else
      result += "pending"
    return result;
  }

  getReason():string{
    if(!this.reservation)
      return "";
    else
      return this.reservation.reason;
  }

  canBeReserved():boolean{
    if (!this.reservation)
      return true;
    else 
      return !this.reservation.approved;
  }
}

@Component({
  selector: 'app-planning-room-component',
  templateUrl: './planning-room-component.html',
  styleUrls: ['./planning-room-component.css']
})
export class PlanningRoomComponentComponent implements OnInit, OnDestroy {
  hall:Hall = new Hall();
  private sub:any;
  days:Array<Array<Array<PlannedCell>>> = [];
  date:Date = new Date();
  beginReservation: Date = null;
  endReservation: Date = null;
  reservation:Reservation = new Reservation({reason: "", email:""});
  startTime:string = "";
  endTime:string = "";

  getDayDate(weekDay:number):string{
    return new Date(new Date().setDate((this.date.getDate() - this.date.getDay()) + weekDay)).toLocaleDateString('en-GB');
  }

  constructor(private route:ActivatedRoute, private hallService:HallService, private reservationService:ReservationService, private authService:AuthService) {
    autoBind(this);
    this.resetCal();
  }

  resetCal(){
    this.beginReservation = null;
    this.endReservation = null;
    this.days = [];
    for (let d = 0; d < 7; d++){
      let day = [];
      for (let h = 0; h < 24; h++){
        let hour = [];
        for (let m = 0; m < 4; m++){
          let minute = new PlannedCell();
          minute.day = d;
          minute.hour = h;
          minute.quarter = m;
          let date = new Date(this.date.getTime());
          date.setDate(date.getDate() - date.getDay() + d + 1);
          date.setUTCHours(h, m * 15, 0);
          minute.date = date;
          hour.push(minute);
        }
        day.push(hour);
      }
      this.days.push(day);
    }
  }

  async getHall(hallId:string = this.hall.getId()){
    try{
      this.hall = await this.hallService.getHall(hallId, this.date);
      console.log(this.hall);
      this.resetCal();
      for (let i in this.hall.calendar){
        this.markOnCalendar(this.hall.calendar[i]);
      }
    }catch(err){
      console.log(err);
    }
  }

  
  ngOnInit() {
    this.sub = this.route.params.subscribe(function(params){this.getHall(params["hallId"]);}.bind(this))
  }

  markOnCalendar(reservation:Reservation){
    let day = this.days[reservation.getDay()];
    let beginHour = reservation.getStartHour();
    let endHour = reservation.getEndHour();
    console.log(reservation.startDate.toJSON());
    for(let h = beginHour; h <= endHour; h++){
      let hour = day[h];
      if (h == beginHour){
        let beginMinute = Math.floor(reservation.getStartMinute() / 15);
        for (let m = beginMinute; m < 4; m++){
          hour[m].reservation = reservation;
        }
      }else if (h == endHour){
        let endMinute = Math.ceil(reservation.getEndMinute() / 15);
        for (let m = 0; m < endMinute; m++){
          hour[m].reservation = reservation;
        }
      }
      else{
        for (let m = 0; m < 4; m++){
          hour[m].reservation = reservation;
        }
      }
    }
  }

  ngOnDestroy(){
    this.sub.unsubscribe();
  }

  subWeek(){
    this.date.setDate(this.date.getDate() - 7);
    this.getHall();
  }

  addWeek(){
    this.date.setDate(this.date.getDate() + 7);
    this.getHall();
  }

  tableCellClicked(cell:PlannedCell){
    this.beginReservation = cell.date;
    this.reservation.startDate = cell.date;
    this.reservation.endDate = cell.date;
  }

  async onSubmit(){
    try{
      console.log(this.startTime);
      let startTime = this.startTime.split(':').map(function(a){
        return parseInt(a);
      });
      console.log(startTime);
      this.reservation.startDate.setUTCHours(startTime[0], startTime[1], 0);
      this.reservation.endDate = new Date(this.reservation.startDate.getTime());
      let endTime = this.endTime.split(':').map(function(a){
        return parseInt(a);
      });
      console.log(this.reservation.endDate);
      this.reservation.endDate.setUTCHours(endTime[0], endTime[1], 0);
      console.log(this.reservation);
      this.reservation.hallIds = [this.hall.getId()];
      await this.reservationService.createReservation(this.reservation);
      this.getHall();
    }catch(err){
      if (err.error)
        if(err.error.err)
          alert(err.error.err);
        else
          alert(err.error.message);
      else
        alert(err.message);
      console.log(err);
    }
  }

  setStartTime(event){
    console.log(event.target.value)
    this.startTime = event.target.value;
    console.log(this.startTime);
  }

  setEndTime(event){
    this.endTime = event.target.value;
  }

}
