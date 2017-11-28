import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import autoBind from 'auto-bind';
import HttpConfig from '../config/http';
import Hall from '../models/hall';

@Injectable()
export class HallService {

  private hall:Hall;
  private halls:Array<Hall> = [];

  constructor(private http:HttpClient) {
    autoBind(this);
    async function init(){
      try{
        await this.getHallen();
      }catch(err){
        console.log(err);
      }
    }
    init();
  }

  async getHallen() {
    this.halls = [];
    let result:any = await this.http.get(HttpConfig.host + "/hall", {headers:HttpConfig.headers}).toPromise();
    console.log(result);
    for(let i in result) {
      this.halls.push(new Hall(result[i]));
    }
    return this.halls;
  }

  async getHall(hallId:string, reservationOffset = new Date()){
    console.log(HttpConfig.headers);
    let result:any = await this.http.get(HttpConfig.host + "/hall/" + hallId + "?offset=" + reservationOffset.toJSON(), {headers:HttpConfig.headers}).toPromise();
    let hall = new Hall(result);
    return hall;
  }

}
