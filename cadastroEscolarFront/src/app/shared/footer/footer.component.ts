import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit{
  mostrarMenu : boolean;

  ngOnInit(): void {
    this.mostrarMenu = JSON.parse(localStorage.getItem("mostrarMenu")!);
  }
}
