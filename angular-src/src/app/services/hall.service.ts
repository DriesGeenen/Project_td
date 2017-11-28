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
    for(let i in result) {
      this.halls.push(new Hall(result[i]));
    }
    return this.halls;
  }

  async getHall(hallId:string, reservationOffset = new Date()){
    let result:any = await this.http.get(HttpConfig.host + "/hall/" + hallId + "?offset=" + reservationOffset.toJSON(), {headers:HttpConfig.headers}).toPromise();
    let hall = new Hall(result);
    return hall;
  }

  async updateHall(hall:Hall){
    let result:any = await this.http.patch(HttpConfig.host + "/hall/" + hall.getId(), hall, {headers:HttpConfig.headers}).toPromise();
    return hall;
  }

  async createHall(hall:Hall){
    let result:any = await this.http.post(HttpConfig.host + "/hall/", hall, {headers:HttpConfig.headers}).toPromise();
    return hall;
  }

  async deleteHall(hall:Hall){
    let result:any = await this.http.delete(HttpConfig.host + "/hall/" + hall.getId(), {headers:HttpConfig.headers}).toPromise();
  }

}
