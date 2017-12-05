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

  async getUsers() {
    this.users = [];
    let result:any = await this.http.get(HttpConfig.host + "/users", {headers:HttpConfig.headers}).toPromise();
    for(let i in result) {
      this.users.push(new User(result[i]));
    }
    return this.users;
  }

  async updateUser(user:User){
    let result:any = await this.http.patch(HttpConfig.host + "/user/" + user.getId(), user, {headers:HttpConfig.headers}).toPromise();
    return user;
  }

  async createUser(user:User){
    let result:any = await this.http.post(HttpConfig.host + "/user/", user, {headers:HttpConfig.headers}).toPromise();
    return user;
  }

  async deleteUser(user:User){
    let result:any = await this.http.delete(HttpConfig.host + "/user/" + user.getId(), {headers:HttpConfig.headers}).toPromise();
  }
}
