import {Directive, Input, OnDestroy, TemplateRef, ViewContainerRef} from '@angular/core';
import {Subscription} from 'rxjs';
import * as _ from 'lodash';
import {User} from "../user";
import {AuthService} from "../auth.service";

@Directive({
  selector: '[lmRBACAllow]'
})
export class RBACAllowDirective implements OnDestroy {
  public allowedRoles: string[];
  public user: User;
  public userSubscription: Subscription;

  constructor(private templateRef: TemplateRef<any>,
              private viewContainer: ViewContainerRef,
              private authService: AuthService) {
    this.userSubscription = authService.user$.subscribe(
      user => {
        this.user = user;
        this.showIfUserAllowed();
      }
    )
  }

  public ngOnDestroy(): void {
    this.userSubscription.unsubscribe();
  }

  @Input()
  public set epRBACAllow(allowedRoles: string[]) {
    this.allowedRoles = allowedRoles;
    this.showIfUserAllowed();
  }

  public showIfUserAllowed(): void {
    if (!this.allowedRoles || this.allowedRoles.length === 0 || !this.user) {
      this.viewContainer.clear();
      return;
    }

    const isUserAllowed = _.intersection(this.allowedRoles, this.user.authorities).length > 0;

    if (isUserAllowed) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    } else {
      this.viewContainer.clear();
    }
  }
}

