
import {RouterModule, Routes} from '@angular/router';
import {LoginPageComponent} from "./components/login-page-component/login-page.component";
import {UsersComponent} from "./components/users-component/users.component";
import {NewUserComponent} from "./components/new-user-component/new-user.component";
import {ResultsComponent} from "./components/results-component/results.component";

const APP_ROUTES: Routes = [
  {path: '', redirectTo: '/signin', pathMatch:'full'},
  {path: 'signin', component: LoginPageComponent },
  {path: 'users', component: UsersComponent},
  {path: 'new-user', component: NewUserComponent},
  {path: 'results', component: ResultsComponent}
];

export const routing = RouterModule.forRoot(APP_ROUTES);
