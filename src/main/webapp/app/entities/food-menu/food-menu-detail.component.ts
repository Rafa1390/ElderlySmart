import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFoodMenu } from 'app/shared/model/food-menu.model';

@Component({
  selector: 'jhi-food-menu-detail',
  templateUrl: './food-menu-detail.component.html'
})
export class FoodMenuDetailComponent implements OnInit {
  foodMenu: IFoodMenu;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ foodMenu }) => {
      this.foodMenu = foodMenu;
    });
  }

  previousState() {
    window.history.back();
  }
}
