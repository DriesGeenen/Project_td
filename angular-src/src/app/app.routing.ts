
import {RouterModule, Routes} from '@angular/router';
import {LoginPageComponent} from "./components/login-page-component/login-page.component";
//import {IndexInhoudComponent} from "./components/index-page-component/index-page.component";
import {HallenLijstComponent} from "./components/hallen-lijst-component/hallen-lijst.component";
import {PlanningRoomComponentComponent} from "./components/planning-room-component/planning-room-component";
import { AcceptReservationComponent } from './components/accept-reservation-component/accept-reservation.component';
import { ReservatieLijstComponentComponent} from "./components/reservatie-lijst-component/reservatie-lijst-component.component";

const APP_ROUTES: Routes = [
  {path: '', component: HallenLijstComponent, pathMatch:"full" },
  {path: 'signin', component: LoginPageComponent },
  {path: 'room-planning/:hallId', component: PlanningRoomComponentComponent },
  {path: 'reservations', component: ReservatieLijstComponentComponent },
];

export const routing = RouterModule.forRoot(APP_ROUTES);
