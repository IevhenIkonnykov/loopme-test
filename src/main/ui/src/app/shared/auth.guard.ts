import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthService} from './auth.service';
import * as _ from 'lodash';
import {map, tap} from 'rxjs/operators';
import {Injectable} from '@angular/core';
import {UserRole} from "./userRole";

@Injectable()
export class AuthorizationGuard implements CanActivate {
  constructor(private allowedAuthorities: UserRole[], private authService: AuthService, private router: Router) {
  }

  public canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    return this.authService.user$.pipe(
      map(user => _.intersection(this.allowedAuthorities, user.authorities).length > 0),
      tap(allowed => {
        if (!allowed) {
          this.router.navigateByUrl('/home')
            .catch(error => console.log('Somehow navigation to a root page has failed with error: ' + error.toString()));
        }
      }));
  }
}
