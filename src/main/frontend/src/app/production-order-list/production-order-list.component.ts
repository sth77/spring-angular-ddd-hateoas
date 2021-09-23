import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ProductionOrderResource } from '../model';

const API = "/api";
const REL = "productionOrders"; 

@Component({
  selector: 'app-production-order-list',
  template: `
  <ul>
    <li *ngFor="let order of productionOrders">{{order.name}} ({{order.state}})
      <button *ngIf="can('submit', order)" (click)="do('submit', order)">submit</button>
      <button *ngIf="can('accept', order)" (click)="do('accept', order)">accept</button>
    </li>
  </ul>
  `,
  styleUrls: ['./production-order-list.component.css']
})
export class ProductionOrderListComponent implements OnInit {

  root: any;
  productionOrders?: ProductionOrderResource[];

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

  can(action: string, order: any): boolean {
    return !!order._links[action];
  }

  do(action: string, order: ProductionOrderResource): void {
    const url = order._links[action].href;
    this.http.post(url, {}).subscribe(
      _ => this.reload(), 
      error => alert(error));
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
