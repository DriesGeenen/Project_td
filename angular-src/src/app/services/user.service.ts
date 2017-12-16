import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import User from '../models/user';
import autoBind from 'auto-bind';
import HttpConfig from '../config/http';

@Injectable()
export class UserService {

  users:Array<User> = [];
  user:User;

  constructor(private http:HttpClient) {
    autoBind(this);
  }

  async getUsers() {
    this.users = [];
    console.log("getting users");
    let result:any = await this.http.get(HttpConfig.host + "/users", {headers:HttpConfig.headers}).toPromise();
    for(let i in result) {
      this.users.push(new User(result[i]));
    }
    return this.users;
  }

  async getUsersByLogopedist(logopedistId){
    this.users = [];
    console.log("getting users by logopedist");
    let result:any = await this.http.get(HttpConfig.host + "/logopedist/" + logopedistId + "/users", {headers:HttpConfig.headers}).toPromise();
    for(let i in result) {
      this.users.push(new User(result[i]));
    }
    return this.users;
  }

  async getUserById(userId){
    console.log("getting user");
    let result:any = await this.http.get(HttpConfig.host + "/users/" + userId, {headers:HttpConfig.headers}).toPromise();
    this.user = result;
  }

  async createUser(user){
    let result:any = await this.http.post(HttpConfig.host + "/users/register", user, {headers:HttpConfig.headers}).toPromise();
    console.log("created user");
    return user;
  }

  async deleteUser(userId){
    let result:any = await this.http.delete(HttpConfig.host + "/users/" + userId, {headers:HttpConfig.headers}).toPromise();
  }
}
