import {Component} from "@angular/core";
import {WebClientService} from "./shared/web-client.service";

@Component({
  selector: 'lm-logout-component',
  template: ''
})
export class LogoutComponent {
  constructor(private webClientService: WebClientService) {
    this.logout();
  }

  public logout() {
    this.webClientService.logout();
  }
}
