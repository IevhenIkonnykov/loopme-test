import {MAT_DIALOG_DATA} from "@angular/material";
import {Component, Inject} from "@angular/core";
import {Dialog} from "../dialog";

@Component({
  selector: 'lm-information-dialog-component',
  templateUrl: './information-dialog.component.html'
})
export class InformationDialogComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: Dialog) {
  }
}
