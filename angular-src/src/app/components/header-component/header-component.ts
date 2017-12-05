import { Component, OnInit, Input } from '@angular/core';
import { AuthService } from '../../services/auth.service';

import autoBind from 'auto-bind';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header-component',
  templateUrl: './header-component.html',
  styleUrls: ['./header-component.css']
})
export class HeaderComponentComponent implements OnInit {
  isLoggedIn: boolean;

  constructor(private authService: AuthService, private router:Router){
    autoBind(this);
    this.isLoggedIn = this.authService.isLoggedIn();
    this.authService.onLogin.subscribe(function(){
      this.isLoggedIn = true;
    }.bind(this));
    this.authService.onLogout.subscribe(function(){
      this.isLoggedIn = false;
    }.bind(this));
  }

  ngOnInit() {
  }

  logout(){
    console.log("logout");
    this.authService.logout();
    console.log("Loged out");
    this.router.navigate(["/"]);
  }
}
