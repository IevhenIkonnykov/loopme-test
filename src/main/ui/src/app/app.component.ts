import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from "./shared/auth.service";
import {Observable} from "rxjs/internal/Observable";
import {User} from "./shared/user";
import {Subscription} from "rxjs/internal/Subscription";
import {SessionStorageService} from 'ngx-webstorage';

@Component({
  selector: 'lm-root',
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit, OnDestroy{

  isLoggedIn$: Observable<boolean>;

  user: User;

  private userSubscription: Subscription;

  constructor(private authService: AuthService,
              private sessionStorageService: SessionStorageService){
    this.userSubscription = this.authService.user$.subscribe(user => this.user = user);
  }

  ngOnInit(): void {
    this.authService.setUser(this.sessionStorageService.retrieve('currentUser'));
    this.isLoggedIn$ = this.authService.isLoggedIn$;
  }

  ngOnDestroy(): void{
    this.userSubscription.unsubscribe();
  }
}
