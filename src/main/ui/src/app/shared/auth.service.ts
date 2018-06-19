import {Injectable} from "@angular/core";
import {User} from "./user";
import {filter, map} from "rxjs/operators";
import {Observable} from "rxjs/internal/Observable";
import {BehaviorSubject} from "rxjs/internal/BehaviorSubject";

const ANONYMOUS_USER: User = {
  name: undefined,
  email: undefined,
  authorities: []
};

@Injectable()
export class AuthService {

  user = new BehaviorSubject<User>(ANONYMOUS_USER);

  user$ = this.user.asObservable().pipe(filter(user => !!user));

  isLoggedIn$: Observable<boolean>;

  isLoggedOut$: Observable<boolean>;

  constructor() {
    this.isLoggedIn$ = this.user$.pipe(map(user => !!user.email));
    this.isLoggedOut$ = this.isLoggedIn$.pipe(map(isLoggedIn => !isLoggedIn));
  }

  setUser(user: User) {
    this.user.next(user);
  }

  logout() {
    this.user.next(ANONYMOUS_USER);
  }
}
