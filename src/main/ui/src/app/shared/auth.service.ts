import {Injectable} from "@angular/core";
import {User} from "./user";
import {filter, map} from "rxjs/operators";
import {Observable} from "rxjs/internal/Observable";
import {BehaviorSubject} from "rxjs/internal/BehaviorSubject";
import {SessionStorageService} from 'ngx-webstorage';
import {Utils} from "./Utils";
import {Router} from "@angular/router";
import {UserRole} from "./userRole";

const ANONYMOUS_USER: User = {
  id: undefined,
  name: 'Guest',
  email: undefined,
  authorities: [UserRole.GUEST]
};

@Injectable()
export class AuthService {

  user = new BehaviorSubject<User>(ANONYMOUS_USER);

  user$ = this.user.asObservable().pipe(filter(user => !!user));

  isLoggedIn$: Observable<boolean>;

  isLoggedOut$: Observable<boolean>;

  constructor(private sessionStorageService: SessionStorageService,
              private router: Router) {
    this.isLoggedIn$ = this.user$.pipe(map(user => !!user.email));
    this.isLoggedOut$ = this.isLoggedIn$.pipe(map(isLoggedIn => !isLoggedIn));
  }

  setUser(user: User) {
    this.user.next(Utils.isUndefinedOrNull(user) ? ANONYMOUS_USER : user);
    this.sessionStorageService.store('currentUser', user);
  }

  logout() {
    this.setUser(ANONYMOUS_USER);
    this.sessionStorageService.clear();
  }
}
