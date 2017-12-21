import {Component, OnInit} from '@angular/core';
import autoBind from 'auto-bind';
import User from'../../models/user';
import Result from'../../models/result';
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
  results:Array<Result> = [];
  totalCorrect:number = 0;
  totalWrong:number = 0;
  totalCorrectByDay:Array<number> = [];
  totalWrongByDay:Array<number> = [];

  constructor(private authService : AuthService, private userService : UserService, private resultService : ResultService, private router : Router) {
    autoBind(this);
  }

  async ngOnInit() {
    await this.userService.getUserById(localStorage.getItem("userId"));
    await this.resultService.getResultsForUser(localStorage.getItem("userId"));
    this.user = this.userService.user;
    this.results = this.resultService.results;
    }

  addToTotal(amountCorrect, amountWrong) {
    this.totalCorrect += amountCorrect;
    this.totalWrong += amountWrong;
  }

  getTotalCorrect(){
    this.totalCorrectByDay.push(this.totalCorrect);
    this.totalCorrect = 0;
    return this.totalCorrectByDay[this.totalCorrectByDay.length - 1];
  }

  getTotalWrong(){
    this.totalWrongByDay.push(this.totalWrong);
    this.totalWrong = 0;
    return this.totalWrongByDay[this.totalWrongByDay.length - 1];
  }
}
