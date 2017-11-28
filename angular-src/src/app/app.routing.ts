
import {RouterModule, Routes} from '@angular/router';
import {LoginPageComponent} from "./components/login-page-component/login-page.component";
import {HallenLijstComponent} from "./components/hallen-lijst-component/hallen-lijst.component";
import {PlanningRoomComponentComponent} from "./components/planning-room-component/planning-room-component";
import { AcceptReservationComponent } from './components/accept-reservation-component/accept-reservation.component';
import { ReservatieLijstComponentComponent} from "./components/reservatie-lijst-component/reservatie-lijst-component.component";
import { AddHallComponent } from './components/add-hall-component/add-hall.component';

const APP_ROUTES: Routes = [
  {path: '', redirectTo: '/hall-list', pathMatch:'full'},
  {path: 'hall-list', component: HallenLijstComponent},
  {path: 'signin', component: LoginPageComponent },
  {path: 'room-planning/:hallId', component: PlanningRoomComponentComponent },
  {path: 'reservations', component: ReservatieLijstComponentComponent },
  {path: 'edit-hall/:hallId', component: AddHallComponent},
  {path: 'pending-reservations', component: AcceptReservationComponent}
];

export const routing = RouterModule.forRoot(APP_ROUTES);
