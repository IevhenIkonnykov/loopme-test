import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {WebClientService} from "../shared/web-client.service";

@Component({
  selector: 'lm-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private webClient: WebClientService) { }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      emailFormControl: ['', [Validators.required, Validators.email]],
      passwordFormControl: ['', [Validators.required, Validators.minLength(4)]]
    });
  }

  submit(){
    const httpBasicAuth = 'Basic ' + btoa(this.loginForm.get('emailFormControl').value + ':' + this.loginForm.get('passwordFormControl').value);
    this.webClient.login(httpBasicAuth);
  }
}
