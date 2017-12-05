import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import Result from '../models/result';
import autoBind from 'auto-bind';
import HttpConfig from '../config/http';

@Injectable()
export class ResultService {

  private result:Result;
  private results:Array<Result> = [];

  constructor(private http:HttpClient) {
    autoBind(this);
    async function init(){
      try{
        await this.getResults();
      }catch(err){
        console.log(err);
      }
    }
    init();
  }

  async getResultsForUser(userId:string) {
    this.results = [];
    let result:any = await this.http.get(HttpConfig.host + "/user/" + userId + "/results/", {headers:HttpConfig.headers}).toPromise();
    for(let i in result) {
      this.results.push(new Result(result[i]));
    }
    return this.results;
  }

}
