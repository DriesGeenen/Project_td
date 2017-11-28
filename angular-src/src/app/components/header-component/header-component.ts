import { Component, OnInit, Input } from '@angular/core';
import { AuthService } from '../../services/auth.service';

import {TranslateService} from '@ngx-translate/core';

import autoBind from 'auto-bind';
import { HallService } from '../../services/hall.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header-component',
  templateUrl: './header-component.html',
  styleUrls: ['./header-component.css']
})
export class HeaderComponentComponent implements OnInit {
  isLoggedIn: boolean;
  selectedLang: string = "";

  constructor(private authService: AuthService, private translateService: TranslateService, private router:Router){
    autoBind(this);
    this.isLoggedIn = this.authService.isLoggedIn();
    this.authService.onLogin.subscribe(function(){
      this.isLoggedIn = true;
    }.bind(this));
    this.authService.onLogout.subscribe(function(){
      this.isLoggedIn = false;
    }.bind(this));
    this.translateService.onLangChange.subscribe(function(){
      this.selectedLang = this.translateService.currentLang;
    }.bind(this));
    this.selectedLang = this.translateService.currentLang;
  }

  ngOnInit() {
  }

  logout(){
    console.log("logout");
    this.authService.logout();
    console.log("Loged out");
    this.router.navigate(["/"]);
  }

  changeLanguage(language:string){
    console.log("Setting language to " + language);
    localStorage.setItem("lang", language);
    this.translateService.use(language);
  }
}





