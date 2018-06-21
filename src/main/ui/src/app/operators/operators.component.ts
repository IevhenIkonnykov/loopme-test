import {Component, OnInit} from '@angular/core';
import {WebClientService} from "../shared/web-client.service";
import {User} from "../shared/user";

@Component({
  selector: 'lm-operators',
  templateUrl: './operators.component.html'
})
export class OperatorsComponent implements OnInit {

  operators: User[];

  constructor(private webClientService: WebClientService) {
  }

  ngOnInit() {
    this.getOperators();
  }

  getOperators() {
    this.webClientService.get<User[]>('/api/operator').subscribe(
      (operators: User[]) => {
        this.operators = operators;
      }
    );
  }
}
