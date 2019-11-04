import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPrescriptionNotification } from 'app/shared/model/prescription-notification.model';
import { AccountService } from 'app/core/auth/account.service';
import { PrescriptionNotificationService } from './prescription-notification.service';

@Component({
  selector: 'jhi-prescription-notification',
  templateUrl: './prescription-notification.component.html'
})
export class PrescriptionNotificationComponent implements OnInit, OnDestroy {
  prescriptionNotifications: IPrescriptionNotification[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected prescriptionNotificationService: PrescriptionNotificationService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.prescriptionNotificationService
      .query()
      .pipe(
        filter((res: HttpResponse<IPrescriptionNotification[]>) => res.ok),
        map((res: HttpResponse<IPrescriptionNotification[]>) => res.body)
      )
      .subscribe((res: IPrescriptionNotification[]) => {
        this.prescriptionNotifications = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPrescriptionNotifications();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPrescriptionNotification) {
    return item.id;
  }

  registerChangeInPrescriptionNotifications() {
    this.eventSubscriber = this.eventManager.subscribe('prescriptionNotificationListModification', response => this.loadAll());
  }
}
