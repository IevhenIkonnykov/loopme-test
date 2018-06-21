import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import {Ng2Webstorage, SessionStorageService} from 'ngx-webstorage';

import { AppComponent } from './app.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatButtonModule, MatCardModule, MatDialogModule, MatToolbarModule} from "@angular/material";
import {APP_BASE_HREF, CommonModule} from "@angular/common";
import {AppRoutingModule} from "./app.routes";
import { LoginComponent } from './login/login.component';
import {MatInputModule} from "@angular/material/input";
import {ReactiveFormsModule} from "@angular/forms";
import {WebClientService} from "./shared/web-client.service";
import {HttpClientModule} from "@angular/common/http";
import {InformationDialogComponent} from "./shared/information_dialog/information-dialog.component";
import {RBACAllowDirective} from "./shared/directives/rbac-allow.directive";
import {AuthService} from "./shared/auth.service";
import { HomeComponent } from './home/home.component';
import {AppsComponent} from "./apps/apps.component";
import {LogoutComponent} from "./logout.component";
import {Router} from "@angular/router";
import {AuthorizationGuard} from "./shared/auth.guard";
import {UserRole} from "./shared/userRole";
import { PublishersComponent } from './publishers/publishers.component';
import { OperatorsComponent } from './operators/operators.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    InformationDialogComponent,
    RBACAllowDirective,
    HomeComponent,
    AppsComponent,
    LogoutComponent,
    PublishersComponent,
    OperatorsComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    MatToolbarModule,
    MatInputModule,
    MatButtonModule,
    HttpClientModule,
    MatDialogModule,
    Ng2Webstorage,
    MatCardModule
  ],
  entryComponents: [
    InformationDialogComponent
  ],
  providers: [
    {provide: APP_BASE_HREF, useValue: '/'},
    WebClientService,
    AuthService,
    SessionStorageService,
    {
      provide: 'loggedUsersGuard',
      useFactory: (authService: AuthService, router: Router) =>
        new AuthorizationGuard([UserRole.PUBLISHER, UserRole.ADOPS, UserRole.ADMIN], authService, router),
      deps: [
        AuthService,
        Router
      ]
    },
    {
      provide: 'publishersAndOperatorsGuard',
      useFactory: (authService: AuthService, router: Router) =>
        new AuthorizationGuard([UserRole.PUBLISHER, UserRole.ADOPS], authService, router),
      deps: [
        AuthService,
        Router
      ]
    },
    {
      provide: 'guestUsersGuard',
      useFactory: (authService: AuthService, router: Router) =>
        new AuthorizationGuard([UserRole.GUEST], authService, router),
      deps: [
        AuthService,
        Router
      ]
    },
    {
      provide: 'adminsAndOperatorsGuard',
      useFactory: (authService: AuthService, router: Router) =>
        new AuthorizationGuard([UserRole.ADMIN, UserRole.ADOPS], authService, router),
      deps: [
        AuthService,
        Router
      ]
    },
    {
      provide: 'adminsGuard',
      useFactory: (authService: AuthService, router: Router) =>
        new AuthorizationGuard([UserRole.ADMIN], authService, router),
      deps: [
        AuthService,
        Router
      ]
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
