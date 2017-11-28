import { Component, OnInit } from '@angular/core';
import { ReservationService } from '../../services/reservation.service';
import { Router } from '@angular/router';
import autoBind from 'auto-bind';

@Component({
  selector: 'app-accept-reservation',
  templateUrl: './accept-reservation.component.html',
  styleUrls: ['./accept-reservation.component.css']
})
export class AcceptReservationComponent implements OnInit {

  constructor(private reservationService:ReservationService, private router:Router) {
      autoBind(this);
   }

  async ngOnInit() {
    try{
      await this.reservationService.getReservations()
    }
    catch(err){
      console.log(err);
    }
  }

}
