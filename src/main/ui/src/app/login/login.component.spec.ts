import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {ReactiveFormsModule} from '@angular/forms';
import {LoginComponent} from './login.component';
import {NO_ERRORS_SCHEMA} from "@angular/core";
import {WebClientService} from "../shared/web-client.service";
import {By} from "@angular/platform-browser";

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  const webClientSpy = jasmine.createSpyObj('WebClientService', ['login']);

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [LoginComponent],
      schemas: [NO_ERRORS_SCHEMA],
      imports: [
        ReactiveFormsModule
      ],
      providers: [
        {
          provide: WebClientService,
          useValue: webClientSpy
        }
      ]
    });
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('login form is validated', () => {
    let email = component.loginForm.get('emailFormControl');
    let password = component.loginForm.get('passwordFormControl');
    const button = fixture.debugElement.query(By.css('button')).nativeElement as HTMLButtonElement;
    fixture.detectChanges();
    expect(component.loginForm.invalid).toBeTruthy();
    expect(button.disabled).toBeTruthy();

    email.setValue('a.com');
    password.setValue('pas');
    fixture.detectChanges();
    expect(component.loginForm.invalid).toBeTruthy();
    expect(button.disabled).toBeTruthy();

    password.setValue('pass');
    fixture.detectChanges();
    expect(component.loginForm.invalid).toBeTruthy();
    expect(button.disabled).toBeTruthy();

    email.setValue('a@a.com');
    fixture.detectChanges();
    expect(component.loginForm.valid).toBeTruthy();
    expect(button.disabled).toBeFalsy();
  });

  it('should call a web client service with appropriate data', () => {
    const webClientService = fixture.debugElement.injector.get(WebClientService);
    const spy = webClientService.login as jasmine.Spy;

    component.loginForm.setValue({'emailFormControl': 'username', 'passwordFormControl': 'password'});
    component.submit();

    expect(spy).toHaveBeenCalledTimes(1);
    expect(spy).toHaveBeenCalledWith('Basic dXNlcm5hbWU6cGFzc3dvcmQ=');
  });
});
