import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecreationalActivity } from 'app/shared/model/recreational-activity.model';

@Component({
  selector: 'jhi-recreational-activity-detail',
  templateUrl: './recreational-activity-detail.component.html'
})
export class RecreationalActivityDetailComponent implements OnInit {
  recreationalActivity: IRecreationalActivity;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ recreationalActivity }) => {
      this.recreationalActivity = recreationalActivity;
    });
  }

  previousState() {
    window.history.back();
  }
}
