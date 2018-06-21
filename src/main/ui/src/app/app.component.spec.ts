import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {AppComponent} from './app.component';
import {NO_ERRORS_SCHEMA} from "@angular/core";
import {RBACAllowDirective} from "./shared/directives/rbac-allow.directive";
import {User} from "./shared/user";
import {UserRole} from "./shared/userRole";
import {of} from "rxjs/internal/observable/of";
import {AuthService} from "./shared/auth.service";
import {SessionStorageService} from "ngx-webstorage";
import {BehaviorSubject} from "rxjs/internal/BehaviorSubject";
import {By} from "@angular/platform-browser";

class AuthServiceMock {
  user = new BehaviorSubject<User>(new User());

  user$ = this.user.asObservable();
  isLoggedIn$ = of(true);

  setUser(user: User) {
    this.user.next(user);
  }
}

class SessionStorageServiceMock {
  retrieve(): User {
    let user = new User();
    user.name = 'test';
    user.email = 'test@test';
    user.authorities = [UserRole.PUBLISHER];

    return user;
  }
}

describe('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent,
        RBACAllowDirective
      ],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [
        {
          provide: AuthService,
          useClass: AuthServiceMock
        },
        {
          provide: SessionStorageService,
          useClass: SessionStorageServiceMock
        }
      ]
    });
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the app', async(() => {
    expect(component).toBeTruthy();
  }));
  it('should show a name of a user in the title', async(() => {
    expect(fixture.debugElement.nativeElement.querySelector('span').textContent).toContain('test');
  }));
  it('should show a logout button', async(() => {
    expect(fixture.debugElement.query(By.css('.logout__button'))).toBeTruthy();
  }));
});
