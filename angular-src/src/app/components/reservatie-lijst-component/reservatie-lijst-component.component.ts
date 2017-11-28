import { Component, OnInit } from '@angular/core';
import autoBind from 'auto-bind';
import { AuthService } from '../../services/auth.service';
import {ReservationService} from "../../services/reservation.service";
import { Router } from '@angular/router';
import Reservation from '../../models/reservation';
import handleHttpError from '../../extensions/errorHandle';

@Component({
  selector: 'app-reservatie-lijst-component',
  templateUrl: './reservatie-lijst-component.component.html',
  styleUrls: ['./reservatie-lijst-component.component.css']
})
export class ReservatieLijstComponentComponent implements OnInit {

  reservation:Reservation = new Reservation({});

  startTime:string = "";
  endTime:string = "";

  constructor(private authService:AuthService, private reservationService:ReservationService, private router:Router) {
    autoBind(this);
  }

  async ngOnInit() {
    try {
      await this.reservationService.getReservationsByUser();
    }
    catch(err){
      console.log(err);
    }
  }

  setReservation(reservation:Reservation){
    this.reservation = reservation;
  }

  setStartTime(event){
    this.startTime = event.target.value;
  }

  setEndTime(event){
    this.endTime = event.target.value;
    console.log(this.endTime);
  }

  async onSubmit(){
    try{
      let startTime = this.startTime.split(':').map(function(a){
        return parseInt(a);
      });
      this.reservation.startDate.setUTCHours(startTime[0], startTime[1], 0);
      this.reservation.endDate = new Date(this.reservation.startDate.getTime());
      let endTime = this.endTime.split(':').map(function(a){
        return parseInt(a);
      });
      this.reservation.endDate.setUTCHours(endTime[0], endTime[1], 0);
      await this.reservationService.updateReservation(this.reservation);
      this.reservationService.getReservationsByUser();
    }catch(err){
      handleHttpError(err);
      console.log(err);
    }
  }
}
