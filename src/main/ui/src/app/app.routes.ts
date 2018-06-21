/**
 * The router configuration.
 */
import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {LoginComponent} from "./login/login.component";
import {HomeComponent} from "./home/home.component";
import {AppsComponent} from "./apps/apps.component";
import {LogoutComponent} from "./logout.component";
import {PublishersComponent} from "./publishers/publishers.component";
import {OperatorsComponent} from "./operators/operators.component";

const routes: Routes = [
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: 'login', component: LoginComponent, canActivate: ['guestUsersGuard']},
  {path: 'home', component: HomeComponent, canActivate: ['loggedUsersGuard']},
  {path: 'apps', component: AppsComponent, canActivate: ['publishersAndOperatorsGuard']},
  {path: 'publishers', component: PublishersComponent, canActivate: ['adminsAndOperatorsGuard']},
  {path: 'operators', component: OperatorsComponent, canActivate: ['adminsGuard']},
  {path: 'logout', component: LogoutComponent},
  {path: '**', redirectTo: 'home'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
