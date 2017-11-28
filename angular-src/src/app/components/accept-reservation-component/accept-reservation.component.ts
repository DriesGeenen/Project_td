import { Component, OnInit } from '@angular/core';
import { ReservationService } from '../../services/reservation.service';
import { Router } from '@angular/router';
import autoBind from 'auto-bind';
import Reservation from '../../models/reservation';
import handleHttpError from '../../extensions/errorHandle';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-accept-reservation',
  templateUrl: './accept-reservation.component.html',
  styleUrls: ['./accept-reservation.component.css']
})
export class AcceptReservationComponent implements OnInit {

  constructor(private reservationService:ReservationService, private router:Router, private userService:UserService) {
    autoBind(this);
  }

  async ngOnInit() {
    try{
      await this.reservationService.getToApproveReservations()
    }
    catch(err){
      console.log(err);
    }
  }

  async approveReservation(reservation:Reservation){
    try{
      reservation.approved = true;
      await this.reservationService.adminUpdateReservation(reservation);
      await this.reservationService.getToApproveReservations();
    }catch(err){
      reservation.approved = false;
      handleHttpError(err);
    }
  }

  async removeReservation(reservation:Reservation){
    try{
      await this.reservationService.deleteReservation(reservation);
      await this.reservationService.getToApproveReservations();
    }catch(err){
      console.log(err);
      handleHttpError(err);
    }
  }

}
