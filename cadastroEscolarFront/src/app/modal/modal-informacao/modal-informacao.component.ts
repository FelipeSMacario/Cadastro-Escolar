import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-modal-informacao',
  templateUrl: './modal-informacao.component.html',
  styleUrls: ['./modal-informacao.component.css']
})
export class ModalInformacaoComponent {

  message : string;
  action : string

  constructor(private _snackBar: MatSnackBar) {}

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action);
  }

}
