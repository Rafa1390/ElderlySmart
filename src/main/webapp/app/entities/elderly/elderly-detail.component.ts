import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IElderly } from 'app/shared/model/elderly.model';

@Component({
  selector: 'jhi-elderly-detail',
  templateUrl: './elderly-detail.component.html'
})
export class ElderlyDetailComponent implements OnInit {
  elderly: IElderly;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ elderly }) => {
      this.elderly = elderly;
    });
  }

  previousState() {
    window.history.back();
  }
}
