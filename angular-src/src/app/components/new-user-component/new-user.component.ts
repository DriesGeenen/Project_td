import { Component, OnInit } from '@angular/core';
import autoBind from 'auto-bind';
import { UserService } from "../../services/user.service";
import { Router } from '@angular/router';
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-new-user',
  templateUrl: './new-user.component.html',
  styleUrls: ['./new-user.component.css']
})
export class NewUserComponent implements OnInit {

  name:string = "";
  email:string = "";
  password:string = "";
  role:string = "";

  constructor(private authService : AuthService, private userService : UserService, private router : Router) {
    autoBind(this);
  }

  ngOnInit() {
  }

  async createUser(){
    const user = {
      name: this.name,
      email: this.email,
      password: this.password,
      role: this.role
    }
    try {
      await this.userService.createUser(user);
      this.router.navigate(["/users"]);
    }
    catch(err){
      if(err != null)
        console.log(err);
    }
  }

}
