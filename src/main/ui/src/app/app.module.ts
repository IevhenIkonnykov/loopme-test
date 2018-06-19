import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatButtonModule, MatDialogModule, MatToolbarModule} from "@angular/material";
import {APP_BASE_HREF} from "@angular/common";
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

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    InformationDialogComponent,
    RBACAllowDirective,
    HomeComponent
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
    MatDialogModule
  ],
  entryComponents: [
    InformationDialogComponent
  ],
  providers: [
    {provide: APP_BASE_HREF, useValue: '/'},
    WebClientService,
    AuthService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
