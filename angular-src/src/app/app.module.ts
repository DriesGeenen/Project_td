import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { HeaderComponentComponent } from './components/header-component/header-component';
import {routing} from './app.routing';
import { AuthService } from './services/auth.service';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import {FormsModule} from '@angular/forms';
//import { IndexInhoudComponent } from './components/index-page/index-page.component';
import { LoginPageComponent } from './components/login-page-component/login-page.component';
import { HallenLijstComponent } from './components/hallen-lijst-component/hallen-lijst.component';
import { HallService} from "./services/hall.service";
import { PlanningRoomComponentComponent} from "./components/planning-room-component/planning-room-component";

import { TranslateModule, TranslateLoader} from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { AcceptReservationComponent } from './components/accept-reservation-component/accept-reservation.component';
import { ReservationService } from './services/reservation.service';
import { ReservatieLijstComponentComponent } from './components/reservatie-lijst-component/reservatie-lijst-component.component';
import { UserService } from './services/user.service';
import { AddHallComponent } from './components/add-hall-component/add-hall.component';
import { ResultsComponent } from './components/results-component/results.component';
import { UsersComponent } from './components/users-component/users.component';


export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponentComponent,
    LoginPageComponent,
    PlanningRoomComponentComponent,
    HallenLijstComponent,
    AcceptReservationComponent,
    ReservatieLijstComponentComponent,
    AddHallComponent,
    ResultsComponent,
    UsersComponent
  ],
  imports: [
    BrowserModule,
    routing,
    HttpClientModule,
    FormsModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    })
  ],
  providers: [AuthService, HallService, ReservationService, UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
