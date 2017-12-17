import { Component, OnInit } from '@angular/core';
import autoBind from 'auto-bind';
import User from'../../models/user';
import { UserService } from "../../services/user.service";
import { ResultService } from "../../services/result.service";
import { Router } from '@angular/router';
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-results',
  templateUrl: './results.component.html',
  styleUrls: ['./results.component.css']
})
export class ResultsComponent implements OnInit {

  user:User;
  oefening1:boolean = true;
  oefening2:boolean = true;
  oefening3:boolean = true;
  oefening4:boolean = true;
  oefening5:boolean = true;

  constructor(private authService : AuthService, private userService : UserService, private resultService : ResultService, private router : Router) {
    autoBind(this);
  }

  async ngOnInit() {
    this.user = this.userService.user;

  }

}
