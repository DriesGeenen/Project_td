import { Component, OnInit } from '@angular/core';
import autoBind from 'auto-bind';
import { AuthService } from '../../services/auth.service';
import {ReservationService} from "../../services/reservation.service";
import { Router } from '@angular/router';

@Component({
  selector: 'app-reservatie-lijst-component',
  templateUrl: './reservatie-lijst-component.component.html',
  styleUrls: ['./reservatie-lijst-component.component.css']
})
export class ReservatieLijstComponentComponent implements OnInit {

  constructor(private authService:AuthService, private reservationService:ReservationService, private router:Router) {
    autoBind(this);
  }

  async ngOnInit() {
    let userId = this.authService.getUser().getId();
    try {
      await this.reservationService.getReservationsByUser(userId);
    }
    catch(err){
      console.log(err);
    }
  }

}
