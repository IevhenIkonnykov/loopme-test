import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {OperatorsComponent} from './operators.component';
import {NO_ERRORS_SCHEMA} from "@angular/core";
import {WebClientService} from "../shared/web-client.service";
import {Observable} from "rxjs/internal/Observable";
import {of} from "rxjs/internal/observable/of";
import {User} from "../shared/user";
import {UserRole} from "../shared/userRole";

class WebClientServiceMock {
  get(): Observable<User[]> {
    const users: User[] = [];
    let user = new User();
    user.name = 'test';
    user.email = 'test@test';
    user.authorities = [UserRole.ADOPS];
    users.push(user);

    return of(users);
  }
}

describe('OperatorsComponent', () => {
  let component: OperatorsComponent;
  let fixture: ComponentFixture<OperatorsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [OperatorsComponent],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [
        {
          provide: WebClientService,
          useClass: WebClientServiceMock
        }
      ]
    });
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OperatorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should contain one operator', () => {
    expect(component.operators.length).toBe(1);
  });
});
