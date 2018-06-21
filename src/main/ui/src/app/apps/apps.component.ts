import {Component, OnInit} from '@angular/core';
import {WebClientService} from "../shared/web-client.service";
import {App} from "../shared/app";

@Component({
  selector: 'lm-apps',
  templateUrl: './apps.component.html'
})
export class AppsComponent implements OnInit {

  apps: App[];

  constructor(private webClientService: WebClientService) {
  }

  ngOnInit() {
    this.getApps();
  }

  getApps(){
    this.webClientService.get<App[]>('/api/app').subscribe(
      (apps: App[]) => {
        this.apps = apps;
      }
    );
  }
}
