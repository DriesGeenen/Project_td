import { Component, OnInit } from '@angular/core';
import autoBind from 'auto-bind';
import { UserService } from "../../services/user.service";
import { ResultService } from "../../services/result.service";
import { Router } from '@angular/router';
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {



  constructor(private authService : AuthService, private userService : UserService, private resultService : ResultService, private router : Router) {
    autoBind(this);
  }

  async ngOnInit() {
    console.log("init users");
    try {
      await this.authService.fetchUser();
      if(this.authService.getUser().role == 'admin'){
        await this.userService.getUsers();
      }
      if(this.authService.getUser().role == 'logopedist'){
        await this.userService.getUsersByLogopedist(this.authService.getUser().getId());
      }
    }
    catch(err){
      console.log(err);
    }
  }

  async getResults(userId){
    try{

      await this.userService.getUserById(userId);
      await this.resultService.getResultsForUser(userId);
      this.router.navigate(["/results"]);
    }
    catch(err){
      console.log(err);
    }
  }

  async deleteUser(userId){
    console.log("delete user " + userId);
    try{
      this.userService.deleteUser(userId);
      try {
        await this.userService.getUsers();
      }
      catch(err){
        console.log(err);
      }
    }
    catch(err){
      console.log(err);
    }
  }
}
