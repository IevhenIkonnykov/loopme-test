import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AppsComponent} from './apps.component';
import {NO_ERRORS_SCHEMA} from "@angular/core";
import {WebClientService} from "../shared/web-client.service";
import {Observable} from "rxjs/internal/Observable";
import {of} from "rxjs/internal/observable/of";
import {App} from "../shared/app";

class WebClientServiceMock {
  get(): Observable<App[]> {
    const apps: App[] = [];
    let app = new App();
    app.name = 'test';
    apps.push(app);

    return of(apps);
  }
}

describe('AppsComponent', () => {
  let component: AppsComponent;
  let fixture: ComponentFixture<AppsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AppsComponent],
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
    fixture = TestBed.createComponent(AppsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should contain one app', () => {
    expect(component.apps.length).toBe(1);
  });
});
