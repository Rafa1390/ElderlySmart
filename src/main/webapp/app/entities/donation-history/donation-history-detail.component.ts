import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDonationHistory } from 'app/shared/model/donation-history.model';

@Component({
  selector: 'jhi-donation-history-detail',
  templateUrl: './donation-history-detail.component.html'
})
export class DonationHistoryDetailComponent implements OnInit {
  donationHistory: IDonationHistory;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ donationHistory }) => {
      this.donationHistory = donationHistory;
    });
  }

  previousState() {
    window.history.back();
  }
}
