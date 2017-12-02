import { Injectable } from "@angular/core";
import Reservation from "../models/reservation";
import { HttpClient } from "@angular/common/http";
import autoBind from 'auto-bind';
import HttpConfig from '../config/http';
import { AuthService } from "./auth.service";
import { UserService } from "./user.service";


@Injectable()
export class ReservationService{
    private reservation:Reservation;
    private reservations:Array<Reservation> = [];

    constructor(private http:HttpClient, private authService:AuthService, private userService:UserService){
        autoBind(this);
        this.authService.onLogout.subscribe(function(){
            this.reservations = [];
        }.bind(this));
    }

    async getReservations(){
        this.reservations = [];
        let result:any = await this.http.get(HttpConfig.host + "/reservation", {headers:HttpConfig.headers}).toPromise();
        for(let i in result){
            this.reservations.push(new Reservation(result[i]));
        }
        return this.reservations;
    }

    async getToApproveReservations(){
        this.reservations = [];
        let result:any = await this.http.get(HttpConfig.host + "/reservation/approved", {headers:HttpConfig.headers}).toPromise();
        for(let i in result){
            this.reservations.push(new Reservation(result[i]));
        }
        return this.reservations;
    }

    async getReservationsByUser(){
      this.reservations = [];
      let result:any = await this.http.get(HttpConfig.host + "/reservation/user", {headers:HttpConfig.headers}).toPromise();
      for(let i in result){
        let reservation = new Reservation(result[i]);
        this.reservations.push(reservation);
      }
      return this.reservations;
  }

    async createReservation(reservation:Reservation){
        let result = await this.http.post(HttpConfig.host + "/reservation", reservation, {headers:HttpConfig.headers}).toPromise();
    }

    async getReservation(reservationId:string){
        let result:any = await this.http.post(HttpConfig.host + "/reservation/" + reservationId, {headers:HttpConfig.headers}).toPromise();
        let reservation = new Reservation(result);
        return reservation;
    }

    async acceptReservation(reservationId:string){
        let result:any = await this.http.patch(HttpConfig.host + '/reservation/' + reservationId, { headers:HttpConfig.headers}).toPromise();
        let reservation = new Reservation(result);
        return reservation;
    }

    async updateReservation(reservation:Reservation){
        let result:any = await this.http.put(HttpConfig.host + '/reservation/' + reservation.getId(), reservation ,{ headers:HttpConfig.headers}).toPromise();
        return reservation;
    }

    async adminUpdateReservation(reservation:Reservation){
        let result:any = await this.http.patch(HttpConfig.host + '/reservation/' + reservation.getId(), reservation ,{ headers:HttpConfig.headers}).toPromise();
        return reservation;
    }

    async deleteReservation(reservation:Reservation){
        let result:any = await this.http.delete(HttpConfig.host + '/reservation/' + reservation.getId() ,{ headers:HttpConfig.headers}).toPromise();
    }
}