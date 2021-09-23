import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

const API = "/api";
const REL = "productionOrders"; 

@Component({
  selector: 'app-production-order-list',
  template: `
  <ul>
    <li *ngFor="let order of productionOrders">{{order.name}} ({{order.state}})</li>
  </ul>
  `,
  styleUrls: ['./production-order-list.component.css']
})
export class ProductionOrderListComponent implements OnInit {

  root: any;
  productionOrders: any;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get(API).subscribe(
      response => {
        this.root = response;
        this.reload();
      },
      error => alert(error)
    );
  }

  private reload(): void {
    if (this.root) {
      this.http.get<any>(this.root._links[REL].href).subscribe(
        response => this.productionOrders = response._embedded[REL],
        error => alert(error)
      )
    }
  }

}
