import { AppComponent } from './app.component';
import { HeaderComponentComponent } from './components/header-component/header-component';
import { LoginPageComponent } from './components/login-page-component/login-page.component';
import { ResultsComponent } from './components/results-component/results.component';
import { UsersComponent } from './components/users-component/users.component';
import { BrowserModule } from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import { NgModule } from '@angular/core';
import {routing} from './app.routing';
import { HttpClientModule } from '@angular/common/http';
import { AuthService } from './services/auth.service';
import { UserService } from './services/user.service';
import { ResultService } from './services/result.service';
import { NewUserComponent } from './components/new-user-component/new-user.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponentComponent,
    LoginPageComponent,
    ResultsComponent,
    UsersComponent,
    NewUserComponent
  ],
  imports: [
    BrowserModule,
    routing,
    HttpClientModule,
    FormsModule
  ],
  providers: [AuthService, UserService, ResultService],
  bootstrap: [AppComponent]
})
export class AppModule { }
