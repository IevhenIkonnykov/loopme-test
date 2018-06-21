import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PublishersComponent } from './publishers.component';
import {NO_ERRORS_SCHEMA} from "@angular/core";
import {WebClientService} from "../shared/web-client.service";
import {Observable} from "rxjs/internal/Observable";
import {User} from "../shared/user";
import {UserRole} from "../shared/userRole";
import {of} from "rxjs/internal/observable/of";

class WebClientServiceMock {
  get(): Observable<User[]> {
    const users: User[] = [];
    let user = new User();
    user.name = 'test';
    user.email = 'test@test';
    user.authorities = [UserRole.PUBLISHER];
    users.push(user);

    return of(users);
  }
}

describe('PublishersComponent', () => {
  let component: PublishersComponent;
  let fixture: ComponentFixture<PublishersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PublishersComponent ],
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
    fixture = TestBed.createComponent(PublishersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should contain one publisher', () => {
    expect(component.publishers.length).toBe(1);
  });
});
