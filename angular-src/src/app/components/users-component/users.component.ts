import { Component, OnInit } from '@angular/core';
import autoBind from 'auto-bind';
import { UserService } from "../../services/user.service";
import { Router } from '@angular/router';
import User from '../../models/user';
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  constructor(private authService : AuthService, private userService : UserService, private router : Router) {
    autoBind(this);
  }

  async ngOnInit() {
    console.log("init users");
    try {
      await this.userService.getUsers();
    }
    catch(err){
      console.log(err);
    }
  }

}
