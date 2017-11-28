import { Component, OnInit } from '@angular/core';
import autoBind from 'auto-bind';
import { AuthService } from '../../services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  email:string = "";
  password:string = "";

  constructor(private authService:AuthService, private router:Router) {
    autoBind(this);
    console.log(router);
  }

  ngOnInit() {
  }

  async login(){
    try {
      console.log(await this.authService.login(this.email, this.password));
      this.email = "";
      this.router.navigate(["/"]);
    }catch(err){
      console.log(err);
    }
    this.password = "";
  }
}
