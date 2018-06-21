import {Component, OnInit} from '@angular/core';
import {WebClientService} from "../shared/web-client.service";
import {User} from "../shared/user";

@Component({
  selector: 'lm-publishers',
  templateUrl: './publishers.component.html'
})
export class PublishersComponent implements OnInit {

  publishers: User[];

  constructor(private webClientService: WebClientService) {
  }

  ngOnInit() {
    this.getPublishers();
  }

  getPublishers() {
    this.webClientService.get<User[]>('/api/publisher').subscribe(
      (publishers: User[]) => {
        this.publishers = publishers;
      }
    );
  }
}
