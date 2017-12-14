import { Injectable,EventEmitter } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import autoBind from 'auto-bind';
import HttpConfig from '../config/http';
import User from '../models/user';

@Injectable()
export class AuthService {

  onLogin:EventEmitter<User> = new EventEmitter();
  onLogout:EventEmitter<any> = new EventEmitter();
  private user:User;

  constructor(private http:HttpClient) {
    autoBind(this);
    if (this.isLoggedIn()){
      console.log("fetching user");
      this.fetchUser();
    }
  }

  getUser(){
    return this.user;
  }

  getAuthToken(): string {
    return localStorage.getItem("authToken");
  }

  private setAuthToken(token:string){
    localStorage.setItem("authToken", token);
    if (!token)
      localStorage.clear();
  }

  isLoggedIn():boolean{
    return this.getAuthToken() != null;
  }


  async login(user_login)
  {
    let result:any = await this.http.post(HttpConfig.host + "/users/authenticate", user_login, {headers:HttpConfig.headers}).toPromise();
    this.user = new User(result);
    this.setAuthToken(result._id);
    HttpConfig.refreshHeaders();
    this.onLogin.emit(this.user);
    return this.user;
  }

  logout(){
    this.setAuthToken(null);
    HttpConfig.refreshHeaders();
    this.onLogout.emit();
  }

  async fetchUser(){
    try{
      let result:any = await this.http.get(HttpConfig.host + "/users/profile", {headers:HttpConfig.headers}).toPromise();
      this.user = new User(result);
    }catch(err){
    }
  }
}
