import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import User from '../models/user';
import autoBind from 'auto-bind';
import HttpConfig from '../config/http';

@Injectable()
export class UserService {

  users:Array<User> = [];

  constructor(private http:HttpClient) { 
    autoBind(this);
  }

  async getUser(userId:string){
    for (let i in this.users){
      if (this.users[i].getId() == userId){
        return this.users[i];
      }
    }
    
    let result = this.http.get(HttpConfig.host + "/user/" + userId, {headers: HttpConfig.headers});
    for (let i in this.users){
      if (this.users[i].getId() == userId){
        return this.users[i];
      }
    }

    let user = new User(result);
    this.users.push(user);
    return user;
  }
  
}
