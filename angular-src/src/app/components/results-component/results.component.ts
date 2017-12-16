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

  constructor(private authService : AuthService, private userService : UserService, private resultService : ResultService, private router : Router) {
    autoBind(this);
  }

  async ngOnInit() {
    this.user = this.userService.user;
  }

}
