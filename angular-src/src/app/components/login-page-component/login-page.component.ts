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
  }

  ngOnInit() {
  }

  async login(){
    const user = {
      email: this.email,
      password: this.password
    }
    try {
      await this.authService.login(user);
      this.email = "";
      this.router.navigate(["/users"]);
    }catch(err){
      if(err != null)
      console.log(err);
    }
    this.password = "";
  }
}
